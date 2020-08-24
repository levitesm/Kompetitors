import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitiveRates, CompetitiveRates } from '@/shared/model/competitive-rates.model';
import CompetitiveRatesService from './competitive-rates.service';

const validations: any = {
  competitiveRates: {
    totalRate: {},
    techRate: {},
    financeRate: {},
    clientsRate: {},
    hrRate: {}
  }
};

@Component({
  validations
})
export default class CompetitiveRatesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;
  public competitiveRates: ICompetitiveRates = new CompetitiveRates();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitiveRatesId) {
        vm.retrieveCompetitiveRates(to.params.competitiveRatesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {}

  public retrieveCompetitiveRates(competitiveRatesId): void {
    this.competitiveRatesService()
      .find(competitiveRatesId)
      .then(res => {
        this.competitiveRates = res;
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
  }
}
