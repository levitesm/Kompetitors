import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISocieteMain } from '@/shared/model/societe-main.model';
import SocieteMainService from './societe-main.service';

@Component
export default class SocieteMainDetails extends Vue {
  @Inject('societeMainService') private societeMainService: () => SocieteMainService;
  public societeMain: ISocieteMain = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.societeMainId) {
        vm.retrieveSocieteMain(to.params.societeMainId);
      }
    });
  }

  public retrieveSocieteMain(societeMainId) {
    this.societeMainService()
      .find(societeMainId)
      .then(res => {
        this.societeMain = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
