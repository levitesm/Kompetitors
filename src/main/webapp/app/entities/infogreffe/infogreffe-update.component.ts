import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IInfogreffe, Infogreffe } from '@/shared/model/infogreffe.model';
import InfogreffeService from './infogreffe.service';

const validations: any = {
  infogreffe: {
    departement: {},
    ville: {},
    numDept: {},
    codeGreffe: {},
    dateImmatriculation: {},
    ca1: {},
    siren: {},
    ca2: {},
    formeJuridique: {},
    resultat3: {},
    resultat2: {},
    resultat1: {},
    ficheidentite: {},
    duree1: {},
    dateDePublication: {},
    statut: {},
    nic: {},
    codeApe: {},
    adresse: {},
    trancheCaMillesime3: {},
    denomination: {},
    duree2: {},
    effectif1: {},
    effectif3: {},
    effectif2: {},
    ca3: {},
    trancheCaMillesime1: {},
    duree3: {},
    trancheCaMillesime2: {},
    codePostal: {},
    dateDeClotureExercice1: {},
    dateDeClotureExercice3: {},
    dateDeClotureExercice2: {},
    libelleApe: {},
    greffe: {},
    millesime3: {},
    millesime2: {},
    millesime1: {},
    region: {}
  }
};

@Component({
  validations
})
export default class InfogreffeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('infogreffeService') private infogreffeService: () => InfogreffeService;
  public infogreffe: IInfogreffe = new Infogreffe();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.infogreffeId) {
        vm.retrieveInfogreffe(to.params.infogreffeId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.infogreffe.id) {
      this.infogreffeService()
        .update(this.infogreffe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.infogreffe.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.infogreffeService()
        .create(this.infogreffe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.infogreffe.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveInfogreffe(infogreffeId): void {
    this.infogreffeService()
      .find(infogreffeId)
      .then(res => {
        this.infogreffe = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
