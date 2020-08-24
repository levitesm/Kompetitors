import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHrInfo } from '@/shared/model/hr-info.model';
import HrInfoService from './hr-info.service';

@Component
export default class HrInfoDetails extends Vue {
  @Inject('hrInfoService') private hrInfoService: () => HrInfoService;
  public hrInfo: IHrInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hrInfoId) {
        vm.retrieveHrInfo(to.params.hrInfoId);
      }
    });
  }

  public retrieveHrInfo(hrInfoId) {
    this.hrInfoService()
      .find(hrInfoId)
      .then(res => {
        this.hrInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
