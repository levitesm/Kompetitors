import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmployeeType } from '@/shared/model/employee-type.model';
import EmployeeTypeService from './employee-type.service';

@Component
export default class EmployeeTypeDetails extends Vue {
  @Inject('employeeTypeService') private employeeTypeService: () => EmployeeTypeService;
  public employeeType: IEmployeeType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeTypeId) {
        vm.retrieveEmployeeType(to.params.employeeTypeId);
      }
    });
  }

  public retrieveEmployeeType(employeeTypeId) {
    this.employeeTypeService()
      .find(employeeTypeId)
      .then(res => {
        this.employeeType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
