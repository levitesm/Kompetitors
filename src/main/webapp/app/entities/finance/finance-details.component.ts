import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFinance } from '@/shared/model/finance.model';
import FinanceService from './finance.service';

@Component
export default class FinanceDetails extends Vue {
  @Inject('financeService') private financeService: () => FinanceService;
  public finance: IFinance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.financeId) {
        vm.retrieveFinance(to.params.financeId);
      }
    });
  }

  public retrieveFinance(financeId) {
    this.financeService()
      .find(financeId)
      .then(res => {
        this.finance = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
