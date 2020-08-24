import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDashboardFinance } from '@/shared/model/dashboard-finance.model';
import DashboardFinanceService from './dashboard-finance.service';

@Component
export default class DashboardFinanceDetails extends Vue {
  @Inject('dashboardFinanceService') private dashboardFinanceService: () => DashboardFinanceService;
  public dashboardFinance: IDashboardFinance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dashboardFinanceId) {
        vm.retrieveDashboardFinance(to.params.dashboardFinanceId);
      }
    });
  }

  public retrieveDashboardFinance(dashboardFinanceId) {
    this.dashboardFinanceService()
      .find(dashboardFinanceId)
      .then(res => {
        this.dashboardFinance = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
