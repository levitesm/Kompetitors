import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitiveRates } from '@/shared/model/competitive-rates.model';
import CompetitiveRatesService from './competitive-rates.service';

@Component
export default class CompetitiveRatesDetails extends Vue {
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;
  public competitiveRates: ICompetitiveRates = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitiveRatesId) {
        vm.retrieveCompetitiveRates(to.params.competitiveRatesId);
      }
    });
  }

  public retrieveCompetitiveRates(competitiveRatesId) {
    this.competitiveRatesService()
      .find(competitiveRatesId)
      .then(res => {
        this.competitiveRates = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
