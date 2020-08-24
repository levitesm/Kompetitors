import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClientsProjects } from '@/shared/model/clients-projects.model';
import ClientsProjectsService from './clients-projects.service';

@Component
export default class ClientsProjectsDetails extends Vue {
  @Inject('clientsProjectsService') private clientsProjectsService: () => ClientsProjectsService;
  public clientsProjects: IClientsProjects = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientsProjectsId) {
        vm.retrieveClientsProjects(to.params.clientsProjectsId);
      }
    });
  }

  public retrieveClientsProjects(clientsProjectsId) {
    this.clientsProjectsService()
      .find(clientsProjectsId)
      .then(res => {
        this.clientsProjects = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
