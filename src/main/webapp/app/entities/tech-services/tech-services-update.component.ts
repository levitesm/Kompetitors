import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListServicesService from '../list-services/list-services.service';
import { IListServices } from '@/shared/model/list-services.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechServices, TechServices } from '@/shared/model/tech-services.model';
import TechServicesService from './tech-services.service';

const validations: any = {
  techServices: {}
};

@Component({
  validations
})
export default class TechServicesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techServicesService') private techServicesService: () => TechServicesService;
  public techServices: ITechServices = new TechServices();

  @Inject('listServicesService') private listServicesService: () => ListServicesService;

  public listServices: IListServices[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techServicesId) {
        vm.retrieveTechServices(to.params.techServicesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techServices.id) {
      this.techServicesService()
        .update(this.techServices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techServices.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techServicesService()
        .create(this.techServices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techServices.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechServices(techServicesId): void {
    this.techServicesService()
      .find(techServicesId)
      .then(res => {
        this.techServices = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listServicesService()
      .retrieve()
      .then(res => {
        this.listServices = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
