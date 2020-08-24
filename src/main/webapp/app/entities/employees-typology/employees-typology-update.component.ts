import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import EmployeeTypeService from '../employee-type/employee-type.service';
import { IEmployeeType } from '@/shared/model/employee-type.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IEmployeesTypology, EmployeesTypology } from '@/shared/model/employees-typology.model';
import EmployeesTypologyService from './employees-typology.service';

const validations: any = {
  employeesTypology: {
    value: {},
    year: {}
  }
};

@Component({
  validations
})
export default class EmployeesTypologyUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeesTypologyService') private employeesTypologyService: () => EmployeesTypologyService;
  public employeesTypology: IEmployeesTypology = new EmployeesTypology();

  @Inject('employeeTypeService') private employeeTypeService: () => EmployeeTypeService;

  public employeeTypes: IEmployeeType[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeesTypologyId) {
        vm.retrieveEmployeesTypology(to.params.employeesTypologyId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.employeesTypology.id) {
      this.employeesTypologyService()
        .update(this.employeesTypology)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeesTypology.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.employeesTypologyService()
        .create(this.employeesTypology)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeesTypology.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEmployeesTypology(employeesTypologyId): void {
    this.employeesTypologyService()
      .find(employeesTypologyId)
      .then(res => {
        this.employeesTypology = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.employeeTypeService()
      .retrieve()
      .then(res => {
        this.employeeTypes = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
