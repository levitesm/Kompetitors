import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import ListIndustriesService from '../list-industries/list-industries.service';
import { IListIndustries } from '@/shared/model/list-industries.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitorIndustry, CompetitorIndustry } from '@/shared/model/competitor-industry.model';
import CompetitorIndustryService from './competitor-industry.service';

const validations: any = {
  competitorIndustry: {
    competitorId: {
      required
    },
    industryId: {
      required
    }
  }
};

@Component({
  validations
})
export default class CompetitorIndustryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitorIndustryService') private competitorIndustryService: () => CompetitorIndustryService;
  public competitorIndustry: ICompetitorIndustry = new CompetitorIndustry();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];

  @Inject('listIndustriesService') private listIndustriesService: () => ListIndustriesService;

  public listIndustries: IListIndustries[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitorIndustryId) {
        vm.retrieveCompetitorIndustry(to.params.competitorIndustryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitorIndustry.id) {
      this.competitorIndustryService()
        .update(this.competitorIndustry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.competitorIndustry.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitorIndustryService()
        .create(this.competitorIndustry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.competitorIndustry.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitorIndustry(competitorIndustryId): void {
    this.competitorIndustryService()
      .find(competitorIndustryId)
      .then(res => {
        this.competitorIndustry = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
    this.listIndustriesService()
      .retrieve()
      .then(res => {
        this.listIndustries = res.data;
      });
  }
}
