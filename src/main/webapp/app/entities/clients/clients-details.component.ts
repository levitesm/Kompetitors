import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClients } from '@/shared/model/clients.model';
import ClientsService from './clients.service';

@Component
export default class ClientsDetails extends Vue {
  @Inject('clientsService') private clientsService: () => ClientsService;
  public clients: IClients = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientsId) {
        vm.retrieveClients(to.params.clientsId);
      }
    });
  }

  public retrieveClients(clientsId) {
    this.clientsService()
      .find(clientsId)
      .then(res => {
        this.clients = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
