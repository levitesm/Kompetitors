import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IHrInfo, HrInfo } from '@/shared/model/hr-info.model';
import HrInfoService from './hr-info.service';

const validations: any = {
  hrInfo: {
    interviewsNumber: {},
    recrutmentTime: {},
    reviewedCvPercent: {},
    hrDetails: {},
    vacanciesUrl: {},
    hrSpecialistsNumber: {},
    glassdoorRate: {},
    viadeoRate: {},
    glassdoorUrl: {},
    viadeoUrl: {},
    cooptationPremiumAmount: {},
    juniorSalary: {},
    averageSalary: {},
    signingIncentives: {},
    averageContractDuration: {}
  }
};

@Component({
  validations
})
export default class HrInfoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('hrInfoService') private hrInfoService: () => HrInfoService;
  public hrInfo: IHrInfo = new HrInfo();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hrInfoId) {
        vm.retrieveHrInfo(to.params.hrInfoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.hrInfo.id) {
      this.hrInfoService()
        .update(this.hrInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.hrInfo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.hrInfoService()
        .create(this.hrInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.hrInfo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveHrInfo(hrInfoId): void {
    this.hrInfoService()
      .find(hrInfoId)
      .then(res => {
        this.hrInfo = res;
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
