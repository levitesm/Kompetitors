import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmployeesTypology } from '@/shared/model/employees-typology.model';
import EmployeesTypologyService from './employees-typology.service';

@Component
export default class EmployeesTypologyDetails extends Vue {
  @Inject('employeesTypologyService') private employeesTypologyService: () => EmployeesTypologyService;
  public employeesTypology: IEmployeesTypology = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeesTypologyId) {
        vm.retrieveEmployeesTypology(to.params.employeesTypologyId);
      }
    });
  }

  public retrieveEmployeesTypology(employeesTypologyId) {
    this.employeesTypologyService()
      .find(employeesTypologyId)
      .then(res => {
        this.employeesTypology = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
