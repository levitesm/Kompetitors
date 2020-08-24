import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICapital } from '@/shared/model/capital.model';
import CapitalService from './capital.service';

@Component
export default class CapitalDetails extends Vue {
  @Inject('capitalService') private capitalService: () => CapitalService;
  public capital: ICapital = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.capitalId) {
        vm.retrieveCapital(to.params.capitalId);
      }
    });
  }

  public retrieveCapital(capitalId) {
    this.capitalService()
      .find(capitalId)
      .then(res => {
        this.capital = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
