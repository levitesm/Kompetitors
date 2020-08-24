import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRepresentatives, Representatives } from '@/shared/model/representatives.model';
import RepresentativesService from './representatives.service';

const validations: any = {
  representatives: {
    competitorSiren: {},
    qualite: {},
    type: {},
    nom: {},
    prenom: {},
    nomUsage: {},
    dateNaissance: {},
    denominationPM: {},
    sirenPM: {},
    linkedInUrl: {},
    old: {}
  }
};

@Component({
  validations
})
export default class RepresentativesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('representativesService') private representativesService: () => RepresentativesService;
  public representatives: IRepresentatives = new Representatives();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.representativesId) {
        vm.retrieveRepresentatives(to.params.representativesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.representatives.id) {
      this.representativesService()
        .update(this.representatives)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.representatives.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.representativesService()
        .create(this.representatives)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.representatives.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRepresentatives(representativesId): void {
    this.representativesService()
      .find(representativesId)
      .then(res => {
        this.representatives = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
