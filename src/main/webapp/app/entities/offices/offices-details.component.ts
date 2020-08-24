import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOffices } from '@/shared/model/offices.model';
import OfficesService from './offices.service';

@Component
export default class OfficesDetails extends Vue {
  @Inject('officesService') private officesService: () => OfficesService;
  public offices: IOffices = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officesId) {
        vm.retrieveOffices(to.params.officesId);
      }
    });
  }

  public retrieveOffices(officesId) {
    this.officesService()
      .find(officesId)
      .then(res => {
        this.offices = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
