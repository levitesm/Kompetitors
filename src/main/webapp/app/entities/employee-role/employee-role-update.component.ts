import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IEmployeeRole, EmployeeRole } from '@/shared/model/employee-role.model';
import EmployeeRoleService from './employee-role.service';

const validations: any = {
  employeeRole: {
    name: {
      required
    }
  }
};

@Component({
  validations
})
export default class EmployeeRoleUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeeRoleService') private employeeRoleService: () => EmployeeRoleService;
  public employeeRole: IEmployeeRole = new EmployeeRole();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeRoleId) {
        vm.retrieveEmployeeRole(to.params.employeeRoleId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.employeeRole.id) {
      this.employeeRoleService()
        .update(this.employeeRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeeRole.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.employeeRoleService()
        .create(this.employeeRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeeRole.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEmployeeRole(employeeRoleId): void {
    this.employeeRoleService()
      .find(employeeRoleId)
      .then(res => {
        this.employeeRole = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
