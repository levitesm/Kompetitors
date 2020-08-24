import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListServices } from '@/shared/model/list-services.model';
import ListServicesService from './list-services.service';

@Component
export default class ListServicesDetails extends Vue {
  @Inject('listServicesService') private listServicesService: () => ListServicesService;
  public listServices: IListServices = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listServicesId) {
        vm.retrieveListServices(to.params.listServicesId);
      }
    });
  }

  public retrieveListServices(listServicesId) {
    this.listServicesService()
      .find(listServicesId)
      .then(res => {
        this.listServices = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
