import { Inject, Mixins } from 'vue-property-decorator';
import EmployeePricingService from '@/entities/employee-pricing/employee-pricing.service';
import { EmployeePricing } from '@/shared/model/employee-pricing.model';
import Component from 'vue-class-component';
import EditPricingModal from '@/core/competitor/clients/edit-pricing.vue';
import { EmployeeRole } from '@/shared/model/employee-role.model';
import EmployeeRoleService from '@/entities/employee-role/employee-role.service';
import AddEmployeeRoleModal from '@/core/competitor/clients/add-employee-role.vue';
import AccessMixin from '@/account/AccessMixin';

@Component({
  components: {
    'edit-pricing-modal': EditPricingModal,
    'add-employee-role-modal': AddEmployeeRoleModal
  }
})
export default class ClientsPricingComponent extends Mixins(AccessMixin) {
  @Inject('employeePricingService') private employeePricingService: () => EmployeePricingService;
  @Inject('employeeRoleService') private employeeRoleService: () => EmployeeRoleService;

  public pricings: EmployeePricing[] = [];
  public roles: EmployeeRole[] = [];
  public selectedPricing: EmployeePricing = new EmployeePricing();

  public paymentTypes = [{ value: 'PER_DAY', text: 'Per day' }, { value: 'PER_HOUR', text: 'Per hour' }];

  roleById(id: number): EmployeeRole {
    return this.roles.find(role => role.id === id) || new EmployeeRole();
  }

  async mounted() {
    this.pricings = await this.employeePricingService().findByCompetitorId(this.$route.params.id);
    this.roles = await this.employeeRoleService().retrieve();
  }

  formatLocalDate(value: Date): string {
    const date = new Date(value);
    const result = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}  ${date.toLocaleTimeString()}`;
    return result || '';
  }

  formatPaymentType(value: string): string {
    const result = this.paymentTypes.find(type => type.value === value);
    return result ? result.text : '';
  }

  async deletePricing(item: EmployeePricing) {
    await this.employeePricingService().delete(item.id);
    this.pricings.splice(this.pricings.indexOf(item), 1);
  }

  showAddRole() {
    this.$root.$emit('bv::show::modal', 'add-employee-role-modal');
  }

  showEditPricing(item: EmployeePricing) {
    this.selectedPricing = item;
    this.$root.$emit('bv::show::modal', 'edit-pricing-modal');
  }

  showAddPricing() {
    this.selectedPricing = new EmployeePricing();
    this.selectedPricing.competitorId = Number(this.$route.params.id);
    this.$root.$emit('bv::show::modal', 'edit-pricing-modal');
  }

  addRole(role: EmployeeRole) {
    this.roles.push(role);
  }

  updatePricing(update: EmployeePricing) {
    const result = this.pricings.find(price => price.id === update.id);
    update.modified = new Date();
    if (result) {
      this.pricings.splice(this.pricings.indexOf(result), 1, update);
    } else {
      this.pricings.push(update);
    }
  }
}
