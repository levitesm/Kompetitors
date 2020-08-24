import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListClientsProjectTypesService from '../list-clients-project-types/list-clients-project-types.service';
import { IListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';

import ClientsService from '../clients/clients.service';
import { IClients } from '@/shared/model/clients.model';

import AlertService from '@/shared/alert/alert.service';
import { IClientsProjects, ClientsProjects } from '@/shared/model/clients-projects.model';
import ClientsProjectsService from './clients-projects.service';

const validations: any = {
  clientsProjects: {
    status: {}
  }
};

@Component({
  validations
})
export default class ClientsProjectsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('clientsProjectsService') private clientsProjectsService: () => ClientsProjectsService;
  public clientsProjects: IClientsProjects = new ClientsProjects();

  @Inject('listClientsProjectTypesService') private listClientsProjectTypesService: () => ListClientsProjectTypesService;

  public listClientsProjectTypes: IListClientsProjectTypes[] = [];

  @Inject('clientsService') private clientsService: () => ClientsService;

  public clients: IClients[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientsProjectsId) {
        vm.retrieveClientsProjects(to.params.clientsProjectsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.clientsProjects.id) {
      this.clientsProjectsService()
        .update(this.clientsProjects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.clientsProjects.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.clientsProjectsService()
        .create(this.clientsProjects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.clientsProjects.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveClientsProjects(clientsProjectsId): void {
    this.clientsProjectsService()
      .find(clientsProjectsId)
      .then(res => {
        this.clientsProjects = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listClientsProjectTypesService()
      .retrieve()
      .then(res => {
        this.listClientsProjectTypes = res.data;
      });
    this.clientsService()
      .retrieve()
      .then(res => {
        this.clients = res.data;
      });
  }
}
