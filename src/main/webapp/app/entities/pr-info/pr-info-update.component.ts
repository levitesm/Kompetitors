import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IPrInfo, PrInfo } from '@/shared/model/pr-info.model';
import PrInfoService from './pr-info.service';

const validations: any = {
  prInfo: {
    date: {},
    marketingWorkforce: {},
    marketingBudget: {},
    experienceFeedback: {},
    linkedInSubscribers: {},
    linkedInEngageRate: {},
    linkedInPostWeek: {},
    linkedInPostDay: {},
    twitterFollowers: {},
    twitterPostWeek: {},
    twitterPostDay: {},
    instagramFollowers: {}
  }
};

@Component({
  validations
})
export default class PrInfoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('prInfoService') private prInfoService: () => PrInfoService;
  public prInfo: IPrInfo = new PrInfo();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.prInfoId) {
        vm.retrievePrInfo(to.params.prInfoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.prInfo.id) {
      this.prInfoService()
        .update(this.prInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.prInfo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.prInfoService()
        .create(this.prInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.prInfo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePrInfo(prInfoId): void {
    this.prInfoService()
      .find(prInfoId)
      .then(res => {
        this.prInfo = res;
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
