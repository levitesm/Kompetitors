import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUpdatehistory } from '@/shared/model/updatehistory.model';
import UpdatehistoryService from './updatehistory.service';

@Component
export default class UpdatehistoryDetails extends Vue {
  @Inject('updatehistoryService') private updatehistoryService: () => UpdatehistoryService;
  public updatehistory: IUpdatehistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.updatehistoryId) {
        vm.retrieveUpdatehistory(to.params.updatehistoryId);
      }
    });
  }

  public retrieveUpdatehistory(updatehistoryId) {
    this.updatehistoryService()
      .find(updatehistoryId)
      .then(res => {
        this.updatehistory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
