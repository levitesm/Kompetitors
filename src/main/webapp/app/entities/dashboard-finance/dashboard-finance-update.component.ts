import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IDashboardFinance, DashboardFinance } from '@/shared/model/dashboard-finance.model';
import DashboardFinanceService from './dashboard-finance.service';

const validations: any = {
  dashboardFinance: {
    grossSales: {},
    grossSalesPerEmployee: {},
    ebit: {},
    netResult: {},
    workforce: {},
    year: {},
    grossSalesPerConsultant: {},
    averagePay: {},
    netResultPercent: {}
  }
};

@Component({
  validations
})
export default class DashboardFinanceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dashboardFinanceService') private dashboardFinanceService: () => DashboardFinanceService;
  public dashboardFinance: IDashboardFinance = new DashboardFinance();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dashboardFinanceId) {
        vm.retrieveDashboardFinance(to.params.dashboardFinanceId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.dashboardFinance.id) {
      this.dashboardFinanceService()
        .update(this.dashboardFinance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.dashboardFinance.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.dashboardFinanceService()
        .create(this.dashboardFinance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.dashboardFinance.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDashboardFinance(dashboardFinanceId): void {
    this.dashboardFinanceService()
      .find(dashboardFinanceId)
      .then(res => {
        this.dashboardFinance = res;
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
