import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICapital, Capital } from '@/shared/model/capital.model';
import CapitalService from './capital.service';

const validations: any = {
  capital: {
    competitorSiren: {},
    montant: {},
    devise: {},
    nbrParts: {},
    pourcentageDetentionPP: {},
    pourcentageDetentionPM: {},
    listed: {},
    privateCapital: {},
    independentC: {},
    independentE: {},
    old: {}
  }
};

@Component({
  validations
})
export default class CapitalUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('capitalService') private capitalService: () => CapitalService;
  public capital: ICapital = new Capital();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.capitalId) {
        vm.retrieveCapital(to.params.capitalId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.capital.id) {
      this.capitalService()
        .update(this.capital)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.capital.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.capitalService()
        .create(this.capital)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.capital.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCapital(capitalId): void {
    this.capitalService()
      .find(capitalId)
      .then(res => {
        this.capital = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
