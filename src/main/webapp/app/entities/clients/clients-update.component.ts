import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ClientsProjectsService from '../clients-projects/clients-projects.service';
import { IClientsProjects } from '@/shared/model/clients-projects.model';

import ListIndustriesService from '../list-industries/list-industries.service';
import { IListIndustries } from '@/shared/model/list-industries.model';

import OfficesService from '../offices/offices.service';
import { IOffices } from '@/shared/model/offices.model';

import AlertService from '@/shared/alert/alert.service';
import { IClients, Clients } from '@/shared/model/clients.model';
import ClientsService from './clients.service';

const validations: any = {
  clients: {
    officeName: {},
    name: {
      required
    },
    since: {},
    isIppon: {},
    updateDate: {}
  }
};

@Component({
  validations
})
export default class ClientsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('clientsService') private clientsService: () => ClientsService;
  public clients: IClients = new Clients();

  @Inject('clientsProjectsService') private clientsProjectsService: () => ClientsProjectsService;

  public clientsProjects: IClientsProjects[] = [];

  @Inject('listIndustriesService') private listIndustriesService: () => ListIndustriesService;

  public listIndustries: IListIndustries[] = [];

  @Inject('officesService') private officesService: () => OfficesService;

  public offices: IOffices[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientsId) {
        vm.retrieveClients(to.params.clientsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.clients.id) {
      this.clientsService()
        .update(this.clients)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.clients.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.clientsService()
        .create(this.clients)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.clients.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveClients(clientsId): void {
    this.clientsService()
      .find(clientsId)
      .then(res => {
        this.clients = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clientsProjectsService()
      .retrieve()
      .then(res => {
        this.clientsProjects = res.data;
      });
    this.listIndustriesService()
      .retrieve()
      .then(res => {
        this.listIndustries = res.data;
      });
    this.officesService()
      .retrieve()
      .then(res => {
        this.offices = res.data;
      });
  }
}
