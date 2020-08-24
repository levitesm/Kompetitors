import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListPracticesService from '../list-practices/list-practices.service';
import { IListPractices } from '@/shared/model/list-practices.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechPractices, TechPractices } from '@/shared/model/tech-practices.model';
import TechPracticesService from './tech-practices.service';

const validations: any = {
  techPractices: {}
};

@Component({
  validations
})
export default class TechPracticesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techPracticesService') private techPracticesService: () => TechPracticesService;
  public techPractices: ITechPractices = new TechPractices();

  @Inject('listPracticesService') private listPracticesService: () => ListPracticesService;

  public listPractices: IListPractices[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techPracticesId) {
        vm.retrieveTechPractices(to.params.techPracticesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techPractices.id) {
      this.techPracticesService()
        .update(this.techPractices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techPractices.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techPracticesService()
        .create(this.techPractices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techPractices.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechPractices(techPracticesId): void {
    this.techPracticesService()
      .find(techPracticesId)
      .then(res => {
        this.techPractices = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listPracticesService()
      .retrieve()
      .then(res => {
        this.listPractices = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
