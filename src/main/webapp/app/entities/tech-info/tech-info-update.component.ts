import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechInfo, TechInfo } from '@/shared/model/tech-info.model';
import TechInfoService from './tech-info.service';

const validations: any = {
  techInfo: {
    techSpecialistsNumber: {}
  }
};

@Component({
  validations
})
export default class TechInfoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techInfoService') private techInfoService: () => TechInfoService;
  public techInfo: ITechInfo = new TechInfo();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techInfoId) {
        vm.retrieveTechInfo(to.params.techInfoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techInfo.id) {
      this.techInfoService()
        .update(this.techInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techInfo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techInfoService()
        .create(this.techInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techInfo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechInfo(techInfoId): void {
    this.techInfoService()
      .find(techInfoId)
      .then(res => {
        this.techInfo = res;
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
