import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IFinance, Finance } from '@/shared/model/finance.model';
import FinanceService from './finance.service';

const validations: any = {
  finance: {
    margin: {},
    ebitda: {},
    occupationRate: {},
    revenue: {},
    year: {
      required,
      numeric
    }
  }
};

@Component({
  validations
})
export default class FinanceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('financeService') private financeService: () => FinanceService;
  public finance: IFinance = new Finance();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.financeId) {
        vm.retrieveFinance(to.params.financeId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.finance.id) {
      this.financeService()
        .update(this.finance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.finance.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.financeService()
        .create(this.finance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.finance.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFinance(financeId): void {
    this.financeService()
      .find(financeId)
      .then(res => {
        this.finance = res;
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
