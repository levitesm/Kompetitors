import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAccessKey } from '@/shared/model/access-key.model';
import AccessKeyService from './access-key.service';

@Component
export default class AccessKeyDetails extends Vue {
  @Inject('accessKeyService') private accessKeyService: () => AccessKeyService;
  public accessKey: IAccessKey = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.accessKeyId) {
        vm.retrieveAccessKey(to.params.accessKeyId);
      }
    });
  }

  public retrieveAccessKey(accessKeyId) {
    this.accessKeyService()
      .find(accessKeyId)
      .then(res => {
        this.accessKey = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
