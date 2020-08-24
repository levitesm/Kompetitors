import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IEmployeeSalaries, EmployeeSalaries } from '@/shared/model/employee-salaries.model';
import EmployeeSalariesService from './employee-salaries.service';

const validations: any = {
  employeeSalaries: {
    seniority: {},
    salary: {},
    updateDate: {},
    updatedBy: {}
  }
};

@Component({
  validations
})
export default class EmployeeSalariesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeeSalariesService') private employeeSalariesService: () => EmployeeSalariesService;
  public employeeSalaries: IEmployeeSalaries = new EmployeeSalaries();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeSalariesId) {
        vm.retrieveEmployeeSalaries(to.params.employeeSalariesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.employeeSalaries.id) {
      this.employeeSalariesService()
        .update(this.employeeSalaries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeeSalaries.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.employeeSalariesService()
        .create(this.employeeSalaries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeeSalaries.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEmployeeSalaries(employeeSalariesId): void {
    this.employeeSalariesService()
      .find(employeeSalariesId)
      .then(res => {
        this.employeeSalaries = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
