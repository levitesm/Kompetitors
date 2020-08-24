import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmployeeSalaries } from '@/shared/model/employee-salaries.model';
import EmployeeSalariesService from './employee-salaries.service';

@Component
export default class EmployeeSalariesDetails extends Vue {
  @Inject('employeeSalariesService') private employeeSalariesService: () => EmployeeSalariesService;
  public employeeSalaries: IEmployeeSalaries = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeSalariesId) {
        vm.retrieveEmployeeSalaries(to.params.employeeSalariesId);
      }
    });
  }

  public retrieveEmployeeSalaries(employeeSalariesId) {
    this.employeeSalariesService()
      .find(employeeSalariesId)
      .then(res => {
        this.employeeSalaries = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
