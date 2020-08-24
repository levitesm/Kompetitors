import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListCompetanciesService from '../list-competancies/list-competancies.service';
import { IListCompetancies } from '@/shared/model/list-competancies.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechCompetancies, TechCompetancies } from '@/shared/model/tech-competancies.model';
import TechCompetanciesService from './tech-competancies.service';

const validations: any = {
  techCompetancies: {}
};

@Component({
  validations
})
export default class TechCompetanciesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techCompetanciesService') private techCompetanciesService: () => TechCompetanciesService;
  public techCompetancies: ITechCompetancies = new TechCompetancies();

  @Inject('listCompetanciesService') private listCompetanciesService: () => ListCompetanciesService;

  public listCompetancies: IListCompetancies[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techCompetanciesId) {
        vm.retrieveTechCompetancies(to.params.techCompetanciesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techCompetancies.id) {
      this.techCompetanciesService()
        .update(this.techCompetancies)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techCompetancies.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techCompetanciesService()
        .create(this.techCompetancies)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techCompetancies.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechCompetancies(techCompetanciesId): void {
    this.techCompetanciesService()
      .find(techCompetanciesId)
      .then(res => {
        this.techCompetancies = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listCompetanciesService()
      .retrieve()
      .then(res => {
        this.listCompetancies = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
