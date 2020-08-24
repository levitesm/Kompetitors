import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechPractices } from '@/shared/model/tech-practices.model';
import TechPracticesService from './tech-practices.service';

@Component
export default class TechPracticesDetails extends Vue {
  @Inject('techPracticesService') private techPracticesService: () => TechPracticesService;
  public techPractices: ITechPractices = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techPracticesId) {
        vm.retrieveTechPractices(to.params.techPracticesId);
      }
    });
  }

  public retrieveTechPractices(techPracticesId) {
    this.techPracticesService()
      .find(techPracticesId)
      .then(res => {
        this.techPractices = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
