import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechInfo } from '@/shared/model/tech-info.model';
import TechInfoService from './tech-info.service';

@Component
export default class TechInfoDetails extends Vue {
  @Inject('techInfoService') private techInfoService: () => TechInfoService;
  public techInfo: ITechInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techInfoId) {
        vm.retrieveTechInfo(to.params.techInfoId);
      }
    });
  }

  public retrieveTechInfo(techInfoId) {
    this.techInfoService()
      .find(techInfoId)
      .then(res => {
        this.techInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
