import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILegal } from '@/shared/model/legal.model';
import LegalService from './legal.service';

@Component
export default class LegalDetails extends Vue {
  @Inject('legalService') private legalService: () => LegalService;
  public legal: ILegal = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.legalId) {
        vm.retrieveLegal(to.params.legalId);
      }
    });
  }

  public retrieveLegal(legalId) {
    this.legalService()
      .find(legalId)
      .then(res => {
        this.legal = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
