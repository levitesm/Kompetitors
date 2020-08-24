import { Component, Inject, Vue } from 'vue-property-decorator';
import RepresentativesService from '@/entities/representatives/representatives.service';
import { Updatehistory } from '@/shared/model/updatehistory.model';
import UpdatehistoryService from '@/entities/updatehistory/updatehistory.service';

@Component({ name: 'representatives' })
export default class Representatives extends Vue {
  @Inject('representativesService') private representativesService: () => RepresentativesService;
  @Inject('updatehistoryService') private updatehistoryService: () => UpdatehistoryService;

  public representatives = [];
  public numPP = 0;
  public numPM = 0;
  public sirenSet = '';
  public updateDate = this.$root.$t('never');
  public user = '';

  async mounted() {
    if (this.$attrs.mountFlag) {
      this.$root.$on('change_in_reps', this.refill);
    }
    this.user = this.$store.getters.account.login;
    await this.refill();
  }

  public async refill(): Promise<void> {
    this.numPP = 0;
    this.numPM = 0;
    this.updateDate = this.$root.$t('never');
    this.updateDate = await this.getUpdateDate();
    this.representatives = await this.representativesService().retrieveBySiren(this.$attrs.siren);
    for (let i = 0; i < this.representatives.length; i++) {
      if (!this.representatives[i].old && this.representatives[i].type === 'PP') {
        this.numPP++;
      }
      if (!this.representatives[i].old && this.representatives[i].type === 'PM') {
        this.numPM++;
      }
    }
  }

  public async updateReps(): Promise<void> {
    if ('snomis' !== this.user && 'mlevites' !== this.user) {
      // TODO TEMPORARY for mlevites
      alert(this.$root.$t('legal-tab.representatives.restricted-update'));
      return;
    }
    this.$root.$emit('loading_start');
    const urh = new Updatehistory();
    urh.type = 'REPS';
    urh.siren = this.$attrs.siren;
    urh.date = new Date();
    this.updatehistoryService().create(urh);
    await this.representativesService().updateBySiren(this.$attrs.siren);
    await this.refill();
    this.$root.$emit('loading_stop');
    this.$root.$emit('update_flags');
  }

  public showEditReps(): void {
    localStorage.setItem('siren', this.$attrs.siren);
    this.$root.$emit('bv::show::modal', 'edit-reps-page');
  }

  public addSiren(siren: string): void {
    if (this.sirenSet.includes(siren + ';')) {
      this.sirenSet = this.sirenSet.replace(siren + ';', '');
    } else {
      this.sirenSet += siren + ';';
    }
  }

  public async getUpdateDate(): Promise<string> {
    let datesList = await this.updatehistoryService().retrieve();

    datesList = datesList.data
      .filter(a => {
        return a.type === 'REPS' && a.siren === this.$attrs.siren;
      })
      .sort((a, b) => {
        return b.date - a.date;
      });
    if (!datesList || !datesList[0] || datesList.size === 0) {
      return this.$root.$t('never') as string;
    }

    return datesList[0].date.toString();
  }
}
