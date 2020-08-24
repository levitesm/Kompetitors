import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ClientsService from '../clients/clients.service';
import { IClients } from '@/shared/model/clients.model';

import TechCompetanciesService from '../tech-competancies/tech-competancies.service';
import { ITechCompetancies } from '@/shared/model/tech-competancies.model';

import TechPartnersService from '../tech-partners/tech-partners.service';
import { ITechPartners } from '@/shared/model/tech-partners.model';

import TechProjectsService from '../tech-projects/tech-projects.service';
import { ITechProjects } from '@/shared/model/tech-projects.model';

import TechServicesService from '../tech-services/tech-services.service';
import { ITechServices } from '@/shared/model/tech-services.model';

import TechToolsService from '../tech-tools/tech-tools.service';
import { ITechTools } from '@/shared/model/tech-tools.model';

import ListCitiesService from '../list-cities/list-cities.service';
import { IListCities } from '@/shared/model/list-cities.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IOffices, Offices } from '@/shared/model/offices.model';
import OfficesService from './offices.service';

const validations: any = {
  offices: {
    name: {},
    address: {},
    phone: {},
    post: {},
    cityAsText: {},
    numberEmployees: {},
    numberConsultants: {},
    numberTechnicals: {},
    numberHR: {},
    numberClients: {},
    established: {},
    isMainOffice: {},
    latitude: {},
    longitude: {}
  }
};

@Component({
  validations
})
export default class OfficesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officesService') private officesService: () => OfficesService;
  public offices: IOffices = new Offices();

  @Inject('clientsService') private clientsService: () => ClientsService;

  public clients: IClients[] = [];

  @Inject('techCompetanciesService') private techCompetanciesService: () => TechCompetanciesService;

  public techCompetancies: ITechCompetancies[] = [];

  @Inject('techPartnersService') private techPartnersService: () => TechPartnersService;

  public techPartners: ITechPartners[] = [];

  @Inject('techProjectsService') private techProjectsService: () => TechProjectsService;

  public techProjects: ITechProjects[] = [];

  @Inject('techServicesService') private techServicesService: () => TechServicesService;

  public techServices: ITechServices[] = [];

  @Inject('techToolsService') private techToolsService: () => TechToolsService;

  public techTools: ITechTools[] = [];

  @Inject('listCitiesService') private listCitiesService: () => ListCitiesService;

  public listCities: IListCities[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.officesId) {
        vm.retrieveOffices(to.params.officesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.offices.id) {
      this.officesService()
        .update(this.offices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.offices.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.officesService()
        .create(this.offices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.offices.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOffices(officesId): void {
    this.officesService()
      .find(officesId)
      .then(res => {
        this.offices = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clientsService()
      .retrieve()
      .then(res => {
        this.clients = res.data;
      });
    this.techCompetanciesService()
      .retrieve()
      .then(res => {
        this.techCompetancies = res.data;
      });
    this.techPartnersService()
      .retrieve()
      .then(res => {
        this.techPartners = res.data;
      });
    this.techProjectsService()
      .retrieve()
      .then(res => {
        this.techProjects = res.data;
      });
    this.techServicesService()
      .retrieve()
      .then(res => {
        this.techServices = res.data;
      });
    this.techToolsService()
      .retrieve()
      .then(res => {
        this.techTools = res.data;
      });
    this.listCitiesService()
      .retrieve()
      .then(res => {
        this.listCities = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
