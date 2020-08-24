import { Component, Vue, Inject } from 'vue-property-decorator';

import { IInfogreffe } from '@/shared/model/infogreffe.model';
import InfogreffeService from './infogreffe.service';

@Component
export default class InfogreffeDetails extends Vue {
  @Inject('infogreffeService') private infogreffeService: () => InfogreffeService;
  public infogreffe: IInfogreffe = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.infogreffeId) {
        vm.retrieveInfogreffe(to.params.infogreffeId);
      }
    });
  }

  public retrieveInfogreffe(infogreffeId) {
    this.infogreffeService()
      .find(infogreffeId)
      .then(res => {
        this.infogreffe = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
