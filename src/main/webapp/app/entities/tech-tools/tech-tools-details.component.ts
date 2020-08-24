import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechTools } from '@/shared/model/tech-tools.model';
import TechToolsService from './tech-tools.service';

@Component
export default class TechToolsDetails extends Vue {
  @Inject('techToolsService') private techToolsService: () => TechToolsService;
  public techTools: ITechTools = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techToolsId) {
        vm.retrieveTechTools(to.params.techToolsId);
      }
    });
  }

  public retrieveTechTools(techToolsId) {
    this.techToolsService()
      .find(techToolsId)
      .then(res => {
        this.techTools = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
