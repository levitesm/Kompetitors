import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechCompetancies } from '@/shared/model/tech-competancies.model';
import TechCompetanciesService from './tech-competancies.service';

@Component
export default class TechCompetanciesDetails extends Vue {
  @Inject('techCompetanciesService') private techCompetanciesService: () => TechCompetanciesService;
  public techCompetancies: ITechCompetancies = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techCompetanciesId) {
        vm.retrieveTechCompetancies(to.params.techCompetanciesId);
      }
    });
  }

  public retrieveTechCompetancies(techCompetanciesId) {
    this.techCompetanciesService()
      .find(techCompetanciesId)
      .then(res => {
        this.techCompetancies = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
