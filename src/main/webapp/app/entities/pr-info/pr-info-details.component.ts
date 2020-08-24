import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrInfo } from '@/shared/model/pr-info.model';
import PrInfoService from './pr-info.service';

@Component
export default class PrInfoDetails extends Vue {
  @Inject('prInfoService') private prInfoService: () => PrInfoService;
  public prInfo: IPrInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.prInfoId) {
        vm.retrievePrInfo(to.params.prInfoId);
      }
    });
  }

  public retrievePrInfo(prInfoId) {
    this.prInfoService()
      .find(prInfoId)
      .then(res => {
        this.prInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
