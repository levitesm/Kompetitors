import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnnualAccount } from '@/shared/model/annual-account.model';
import AnnualAccountService from './annual-account.service';

@Component
export default class AnnualAccountDetails extends Vue {
  @Inject('annualAccountService') private annualAccountService: () => AnnualAccountService;
  public annualAccount: IAnnualAccount = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.annualAccountId) {
        vm.retrieveAnnualAccount(to.params.annualAccountId);
      }
    });
  }

  public retrieveAnnualAccount(annualAccountId) {
    this.annualAccountService()
      .find(annualAccountId)
      .then(res => {
        this.annualAccount = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
