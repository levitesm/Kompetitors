import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmployeePricing } from '@/shared/model/employee-pricing.model';
import EmployeePricingService from './employee-pricing.service';

@Component
export default class EmployeePricingDetails extends Vue {
  @Inject('employeePricingService') private employeePricingService: () => EmployeePricingService;
  public employeePricing: IEmployeePricing = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeePricingId) {
        vm.retrieveEmployeePricing(to.params.employeePricingId);
      }
    });
  }

  public retrieveEmployeePricing(employeePricingId) {
    this.employeePricingService()
      .find(employeePricingId)
      .then(res => {
        this.employeePricing = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
