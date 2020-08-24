import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import EmployeeRoleService from '../employee-role/employee-role.service';
import { IEmployeeRole } from '@/shared/model/employee-role.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IEmployeePricing, EmployeePricing } from '@/shared/model/employee-pricing.model';
import EmployeePricingService from './employee-pricing.service';

const validations: any = {
  employeePricing: {
    level: {},
    price: {},
    currency: {},
    paymentType: {},
    modified: {}
  }
};

@Component({
  validations
})
export default class EmployeePricingUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeePricingService') private employeePricingService: () => EmployeePricingService;
  public employeePricing: IEmployeePricing = new EmployeePricing();

  @Inject('employeeRoleService') private employeeRoleService: () => EmployeeRoleService;

  public employeeRoles: IEmployeeRole[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeePricingId) {
        vm.retrieveEmployeePricing(to.params.employeePricingId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.employeePricing.id) {
      this.employeePricingService()
        .update(this.employeePricing)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeePricing.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.employeePricingService()
        .create(this.employeePricing)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.employeePricing.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.employeePricing[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.employeePricing[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.employeePricing[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.employeePricing[field] = null;
    }
  }

  public retrieveEmployeePricing(employeePricingId): void {
    this.employeePricingService()
      .find(employeePricingId)
      .then(res => {
        res.modified = new Date(res.modified);
        this.employeePricing = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.employeeRoleService()
      .retrieve()
      .then(res => {
        this.employeeRoles = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
