import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitors } from '@/shared/model/competitors.model';
import CompetitorsService from './competitors.service';

@Component
export default class CompetitorsDetails extends Vue {
  @Inject('competitorsService') private competitorsService: () => CompetitorsService;
  public competitors: ICompetitors = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitorsId) {
        vm.retrieveCompetitors(to.params.competitorsId);
      }
    });
  }

  public retrieveCompetitors(competitorsId) {
    this.competitorsService()
      .find(competitorsId)
      .then(res => {
        this.competitors = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
