import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IWorkforce, Workforce } from '@/shared/model/workforce.model';
import WorkforceService from './workforce.service';

const validations: any = {
  workforce: {
    employeeNumber: {},
    year: {},
    competitor: {
      required
    }
  }
};

@Component({
  validations
})
export default class WorkforceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('workforceService') private workforceService: () => WorkforceService;
  public workforce: IWorkforce = new Workforce();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workforceId) {
        vm.retrieveWorkforce(to.params.workforceId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.workforce.id) {
      this.workforceService()
        .update(this.workforce)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.workforce.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.workforceService()
        .create(this.workforce)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.workforce.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveWorkforce(workforceId): void {
    this.workforceService()
      .find(workforceId)
      .then(res => {
        this.workforce = res;
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
