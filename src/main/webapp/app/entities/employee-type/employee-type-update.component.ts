import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IEmployeeType, EmployeeType } from '@/shared/model/employee-type.model';
import EmployeeTypeService from './employee-type.service';

const validations: any = {
  employeeType: {
    name: {
      required
    }
  }
};

@Component({
  validations
})
export default class EmployeeTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeeTypeService') private employeeTypeService: () => EmployeeTypeService;
  public employeeType: IEmployeeType = new EmployeeType();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeTypeId) {
        vm.retrieveEmployeeType(to.params.employeeTypeId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.employeeType.id) {
      this.employeeTypeService()
        .update(this.employeeType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeeType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.employeeTypeService()
        .create(this.employeeType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeeType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEmployeeType(employeeTypeId): void {
    this.employeeTypeService()
      .find(employeeTypeId)
      .then(res => {
        this.employeeType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
