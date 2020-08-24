import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnnualAccountStatistics } from '@/shared/model/annual-account-statistics.model';
import AnnualAccountStatisticsService from './annual-account-statistics.service';

@Component
export default class AnnualAccountStatisticsDetails extends Vue {
  @Inject('annualAccountStatisticsService') private annualAccountStatisticsService: () => AnnualAccountStatisticsService;
  public annualAccountStatistics: IAnnualAccountStatistics = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.annualAccountStatisticsId) {
        vm.retrieveAnnualAccountStatistics(to.params.annualAccountStatisticsId);
      }
    });
  }

  public retrieveAnnualAccountStatistics(annualAccountStatisticsId) {
    this.annualAccountStatisticsService()
      .find(annualAccountStatisticsId)
      .then(res => {
        this.annualAccountStatistics = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
