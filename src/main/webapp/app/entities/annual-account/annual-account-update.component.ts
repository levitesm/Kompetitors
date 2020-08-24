import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IAnnualAccount, AnnualAccount } from '@/shared/model/annual-account.model';
import AnnualAccountService from './annual-account.service';

const validations: any = {
  annualAccount: {
    siren: {
      required
    },
    year: {
      required,
      numeric
    },
    code: {
      required
    },
    value: {}
  }
};

@Component({
  validations
})
export default class AnnualAccountUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('annualAccountService') private annualAccountService: () => AnnualAccountService;
  public annualAccount: IAnnualAccount = new AnnualAccount();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.annualAccountId) {
        vm.retrieveAnnualAccount(to.params.annualAccountId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.annualAccount.id) {
      this.annualAccountService()
        .update(this.annualAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.annualAccount.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.annualAccountService()
        .create(this.annualAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.annualAccount.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAnnualAccount(annualAccountId): void {
    this.annualAccountService()
      .find(annualAccountId)
      .then(res => {
        this.annualAccount = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
