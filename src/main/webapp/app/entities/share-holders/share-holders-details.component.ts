import { Component, Vue, Inject } from 'vue-property-decorator';

import { IShareHolders } from '@/shared/model/share-holders.model';
import ShareHoldersService from './share-holders.service';

@Component
export default class ShareHoldersDetails extends Vue {
  @Inject('shareHoldersService') private shareHoldersService: () => ShareHoldersService;
  public shareHolders: IShareHolders = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.shareHoldersId) {
        vm.retrieveShareHolders(to.params.shareHoldersId);
      }
    });
  }

  public retrieveShareHolders(shareHoldersId) {
    this.shareHoldersService()
      .find(shareHoldersId)
      .then(res => {
        this.shareHolders = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
