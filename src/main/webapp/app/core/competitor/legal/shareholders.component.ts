import { Component, Inject, Mixins } from 'vue-property-decorator';
import ShareHoldersService from '@/entities/share-holders/share-holders.service';
import CapitalService from '@/entities/capital/capital.service';
import { IShareHolders, ShareHolders } from '@/shared/model/share-holders.model';
import { Updatehistory } from '@/shared/model/updatehistory.model';
import UpdatehistoryService from '@/entities/updatehistory/updatehistory.service';
import { Capital } from '@/shared/model/capital.model';
import { mapGetters } from 'vuex';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';
import { IAnnualAccount } from '@/shared/model/annual-account.model';
import FinanceFormulaMixin from '@/shared/mixins/finance-formula-mixin';
import AccessMixin from '@/account/AccessMixin';

@Component({
  name: 'shareholders',
  computed: {
    ...mapGetters({
      annualAccounts: 'annualAccounts',
      competitorIsListed: 'competitorIsListed',
      competitorIsIndependent: 'competitorIsIndependent',
      competitorHasPrivateCapital: 'competitorHasPrivateCapital'
    })
  }
})
export default class Shareholders extends Mixins(FinanceFormulaMixin, AccessMixin) {
  @Inject('shareHoldersService') private shareholdersService: () => ShareHoldersService;
  @Inject('capitalService') private capitalService: () => CapitalService;
  @Inject('updatehistoryService') private updatehistoryService: () => UpdatehistoryService;
  @Inject('annualAccountService') private annualAccountService: () => AnnualAccountService;

  public shareholders: IShareHolders[] = [];
  public capital = null;
  public numPP = 0;
  public numPM = 0;
  public isListed = false;
  public isPrivateEquity = false;
  public isIndependent = false;
  public updateDate = this.$root.$t('never');
  public user = '';
  public annualAccounts!: IAnnualAccount[];
  public prevYear = new Date().getFullYear() - 1;
  public newSS = new ShareHolders();
  public newSsPE = new ShareHolders();
  public showAddSS = false;
  public showAddSsPE = false;
  public errorSS = '';
  public errorPE = '';
  public partsSS = '0';
  public partsPE = '0';
  public percSS = '0';
  public percPE = '0';
  public PEflag = false;
  public specFlag = false;

  async mounted() {
    this.$root.$on('change_in_shares', this.refill);
    this.user = this.$store.getters.account.login;
    await this.refill();
  }

  public async refill(): Promise<void> {
    this.updateDate = await this.getUpdateDate();
    this.capital = await this.capitalService().retrieveBySiren(this.$attrs.siren);
    if (this.capital.length > 0) {
      this.capital = this.capital[0];
      this.isListed = this.capital.listed;
      // this.isIndependent = this.capital.independentC;
      this.isPrivateEquity = this.capital.privateCapital;
    } else {
      this.capital = new Capital();
      this.capital.competitorSiren = this.$attrs.siren;
      this.capital.montant = 0;
      this.capital.pourcentageDetentionPP = 0;
      this.capital.pourcentageDetentionPM = 0;
      this.capital.nbrParts = 0;

      if (!(this.annualAccounts.length > 0 && this.annualAccounts[0].siren === this.$attrs.siren)) {
        this.annualAccountService()
          .getAllBySiren(this.$attrs.siren)
          .then(response => {
            this.$store.commit('setAnnualAccounts', response);
          });
      }
      this.capital.montant = this.shareCapital(this.prevYear);
      this.capital = await this.capitalService().create(this.capital);
    }

    this.shareholders = await this.shareholdersService().retrieveBySiren(this.$attrs.siren);
    this.shareholders.sort((a, b) => {
      return b.pourcentageDetention - a.pourcentageDetention;
    });
    this.numPP = 0;
    this.numPM = 0;
    this.capital.pourcentageDetentionPP = 0;
    this.capital.pourcentageDetentionPM = 0;
    this.capital.nbrParts = 0;
    this.isIndependent = false;
    this.PEflag = false;
    for (let i = 0; i < this.shareholders.length; i++) {
      this.capital.nbrParts += this.shareholders[i].nbrParts;
      if (!this.shareholders[i].old && this.shareholders[i].typePersonne === 'Personne Physique') {
        this.numPP++;
        this.capital.pourcentageDetentionPP += this.shareholders[i].pourcentageDetention;
        if (this.shareholders[i].pourcentageDetention > 50) {
          this.isIndependent = true;
        }
      }
      if (!this.shareholders[i].old && this.shareholders[i].typePersonne === 'Personne Morale') {
        this.numPM++;
        this.capital.pourcentageDetentionPM += this.shareholders[i].pourcentageDetention;
        if (this.shareholders[i].prenom === '@PRIVATE') {
          this.PEflag = true;
        }
      }
    }
    if (this.PEflag) {
      this.capital.privateCapital = true;
    }
    this.capital.independentC = this.isIndependent;
    await this.capitalService().update(this.capital);
    this.$store.commit('setCompetitorIsListed', this.capital.listed);
    this.$store.commit('setCompetitorIsIndependent', this.capital.independentC);
    this.$store.commit('setCompetitorHasPrivateCapital', this.capital.privateCapital);
  }

  public async updateShares(): Promise<void> {
    if ('snomis' !== this.user) {
      alert(this.$root.$t('legal-tab.shareholders.restricted-update'));
      return;
    }
    this.$root.$emit('loading_start');
    const urh = new Updatehistory();
    urh.type = 'SHARES';
    urh.siren = this.$attrs.siren;
    urh.date = new Date();
    this.updatehistoryService().create(urh);
    await this.shareholdersService().updateBySiren(this.$attrs.siren);
    await this.refill();
    this.$root.$emit('loading_stop');
    this.$root.$emit('update_flags');
  }

  public async changeBox(): Promise<void> {
    this.capital.listed = !this.isListed;
    await this.capitalService().update(this.capital); // Update Check Boxes
    this.$store.commit('setCompetitorIsListed', this.capital.listed);
  }
  public async changeBoxPE(): Promise<void> {
    this.capital.privateCapital = !this.isPrivateEquity;
    await this.capitalService().update(this.capital); // Update Check Boxes
    this.$store.commit('setCompetitorHasPrivateCapital', this.capital.privateCapital);
  }

  public getBarWidth(perc: number): string {
    return 'width: ' + perc + '%';
  }

  public Nothing(): void {}

  private getMoney(field: string): string {
    let res = '';
    let inn = '' + field;
    while (inn.length > 3) {
      res = inn.substring(inn.length - 3, inn.length) + ' ' + res;
      inn = inn.substring(0, inn.length - 3);
    }

    res = inn + ' ' + res + ' \u20ac';
    return res;
  }

  public async getUpdateDate(): Promise<string> {
    let datesList = await this.updatehistoryService().retrieve();

    datesList = datesList.data
      .filter(a => {
        return a.type === 'SHARES' && a.siren === this.$attrs.siren;
      })
      .sort((a, b) => {
        return b.date - a.date;
      });
    if (!datesList || !datesList[0] || datesList.size === 0) {
      return this.$root.$t('never') as string;
    }

    return datesList[0].date.toString();
  }

  public async deleteShareholder(ss: ShareHolders): Promise<void> {
    await this.shareholdersService().delete(ss.id);
    this.refill();
  }

  public showAddss(): void {
    this.showAddSS = true;
    this.specFlag = false;
    this.newSS = new ShareHolders();
    this.newSS.typePersonne = 'Personne Morale';
    this.newSsPE.nomPatronymique = '';
    this.newSsPE.civilite = '';
    this.newSS.denomination = '';
    this.newSS.codeApe = '';
    this.newSS.nbrParts = 0;
    this.newSS.pourcentageDetention = 0;
    this.newSS.competitorSiren = this.$attrs.siren;
    this.partsSS = '0';
    this.percSS = '0';
  }

  public showAddsspe(): void {
    this.showAddSsPE = true;
    this.newSsPE = new ShareHolders();
    this.newSsPE.typePersonne = 'Personne Morale';
    this.newSsPE.prenom = '@PRIVATE';
    this.newSsPE.nomPatronymique = '';
    this.newSsPE.civilite = '';
    this.newSsPE.denomination = '';
    this.newSsPE.codeApe = '';
    this.newSsPE.nbrParts = 0;
    this.newSsPE.pourcentageDetention = 0;
    this.newSsPE.competitorSiren = this.$attrs.siren;
    this.partsPE = '0';
    this.percPE = '0';
  }

  public editShareholderSS(ss: ShareHolders): void {
    this.showAddSS = true;
    this.specFlag = true;
    this.newSS = ss;
    this.partsSS = ss.nbrParts.toString();
    this.percSS = ss.pourcentageDetention.toString();
  }

  public editShareholderPE(ss: ShareHolders): void {
    this.showAddSsPE = true;
    this.newSsPE = ss;
    this.partsPE = ss.nbrParts.toString();
    this.percPE = ss.pourcentageDetention.toString();
  }

  public hideAddSsPE(): void {
    this.showAddSsPE = false;
    this.errorPE = '';
  }

  public hideAddSS(): void {
    this.showAddSS = false;
    this.errorSS = '';
  }

  public async saveSsPE(): Promise<void> {
    if (this.newSsPE.denomination === '') {
      this.errorPE = 'Please enter Denomination!';
      return;
    }
    if (!/^\d+$/.test(this.partsPE)) {
      this.errorPE = 'Number Parts must be a positive integer!';
      return;
    }
    try {
      const n = parseInt(this.partsPE, 10);
      if (n < 0) {
        this.errorPE = 'Number Parts must be a positive integer!';
        return;
      }
      this.newSsPE.nbrParts = n;
    } catch {
      this.errorPE = 'Number Parts must be a positive integer!';
      return;
    }
    if (!/^\d+$/.test(this.percPE)) {
      this.errorPE = 'Pourcentage Detention must be between 0 and 100';
      return;
    }
    try {
      const n = parseFloat(this.percPE);
      if (n <= 0 || n > 100) {
        this.errorPE = 'Pourcentage Detention must be between 0 and 100';
        return;
      }
      this.newSsPE.pourcentageDetention = n;
    } catch {
      this.errorPE = 'Pourcentage Detention must be between 0 and 100';
      return;
    }
    this.errorPE = '';
    this.hideAddSsPE();
    if (this.newSsPE.id) {
      await this.shareholdersService().update(this.newSsPE);
    } else {
      await this.shareholdersService().create(this.newSsPE);
    }
    this.refill();
  }

  public async saveSS(): Promise<void> {
    if (this.newSS.denomination === '') {
      this.errorSS = 'Please enter Name/Denomination!';
      return;
    }
    if (!/^\d+$/.test(this.partsSS)) {
      this.errorSS = 'Number Parts must be a positive integer!';
      return;
    }
    try {
      const n = parseInt(this.partsSS, 10);
      if (n < 0) {
        this.errorSS = 'Number Parts must be a positive integer!';
        return;
      }
      this.newSS.nbrParts = n;
    } catch {
      this.errorSS = 'Number Parts must be a positive integer!';
      return;
    }
    if (!/^\d+$/.test(this.percSS)) {
      this.errorSS = 'Pourcentage Detention must be between 0 and 100';
      return;
    }
    try {
      const n = parseFloat(this.percSS);
      if (n <= 0 || n > 100) {
        this.errorSS = 'Pourcentage Detention must be between 0 and 100';
        return;
      }
      this.newSS.pourcentageDetention = n;
    } catch {
      this.errorSS = 'Pourcentage Detention must be between 0 and 100';
      return;
    }
    this.errorSS = '';
    if (this.newSS.typePersonne !== 'Personne Morale') {
      this.newSS.dateNaissance = this.newSS.codeApe;
      this.newSS.codeApe = null;
      this.newSS.prenom = this.newSS.denomination;
      this.newSS.denomination = null;
    }
    this.hideAddSS();
    if (this.newSS.id) {
      await this.shareholdersService().update(this.newSS);
    } else {
      await this.shareholdersService().create(this.newSS);
    }
    this.refill();
  }
}
