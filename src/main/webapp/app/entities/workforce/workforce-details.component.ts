import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWorkforce } from '@/shared/model/workforce.model';
import WorkforceService from './workforce.service';

@Component
export default class WorkforceDetails extends Vue {
  @Inject('workforceService') private workforceService: () => WorkforceService;
  public workforce: IWorkforce = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workforceId) {
        vm.retrieveWorkforce(to.params.workforceId);
      }
    });
  }

  public retrieveWorkforce(workforceId) {
    this.workforceService()
      .find(workforceId)
      .then(res => {
        this.workforce = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
