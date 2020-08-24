import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitorIndustry } from '@/shared/model/competitor-industry.model';
import CompetitorIndustryService from './competitor-industry.service';

@Component
export default class CompetitorIndustryDetails extends Vue {
  @Inject('competitorIndustryService') private competitorIndustryService: () => CompetitorIndustryService;
  public competitorIndustry: ICompetitorIndustry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitorIndustryId) {
        vm.retrieveCompetitorIndustry(to.params.competitorIndustryId);
      }
    });
  }

  public retrieveCompetitorIndustry(competitorIndustryId) {
    this.competitorIndustryService()
      .find(competitorIndustryId)
      .then(res => {
        this.competitorIndustry = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
