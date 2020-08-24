import { Component, Vue, Inject } from 'vue-property-decorator';

import { IExternalUrls } from '@/shared/model/external-urls.model';
import ExternalUrlsService from './external-urls.service';

@Component
export default class ExternalUrlsDetails extends Vue {
  @Inject('externalUrlsService') private externalUrlsService: () => ExternalUrlsService;
  public externalUrls: IExternalUrls = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.externalUrlsId) {
        vm.retrieveExternalUrls(to.params.externalUrlsId);
      }
    });
  }

  public retrieveExternalUrls(externalUrlsId) {
    this.externalUrlsService()
      .find(externalUrlsId)
      .then(res => {
        this.externalUrls = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
