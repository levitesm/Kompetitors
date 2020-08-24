import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmployeeRole } from '@/shared/model/employee-role.model';
import EmployeeRoleService from './employee-role.service';

@Component
export default class EmployeeRoleDetails extends Vue {
  @Inject('employeeRoleService') private employeeRoleService: () => EmployeeRoleService;
  public employeeRole: IEmployeeRole = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeRoleId) {
        vm.retrieveEmployeeRole(to.params.employeeRoleId);
      }
    });
  }

  public retrieveEmployeeRole(employeeRoleId) {
    this.employeeRoleService()
      .find(employeeRoleId)
      .then(res => {
        this.employeeRole = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
