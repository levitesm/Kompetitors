import { Inject, Prop, Vue, Watch } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { Currency, EmployeePricing, PaymentType } from '@/shared/model/employee-pricing.model';
import EmployeePricingService from '@/entities/employee-pricing/employee-pricing.service';
import { EmployeeLevel } from '@/shared/model/employee-pricing.model';
import { EmployeeRole } from '@/shared/model/employee-role.model';
import Question from '@/core/components/question.vue';

@Component({
  name: 'edit-pricing-modal',
  components: {
    Question
  }
})
export default class EditPricingModal extends Vue {
  @Inject('employeePricingService') private employeePricingService: () => EmployeePricingService;

  @Prop() public pricings: EmployeePricing[];
  @Prop() public selected: EmployeePricing;
  @Prop() public roles: EmployeeRole[];
  @Prop() public paymentTypes: [];

  public levels = this.arrayFromEnum(EmployeeLevel);
  public currencies = this.arrayFromEnum(Currency);

  public roleOptions = [];

  public savingError = null;

  public pricingModel: EmployeePricing = new EmployeePricing();

  @Watch('selected')
  onChange(newValue: any) {
    this.pricingModel = { ...this.selected };
  }

  @Watch('roles')
  onRolesChange(newValue: any) {
    this.roleOptions = this.roles.map(role => {
      return { value: role.id, text: role.name };
    });
  }

  async save() {
    if (!this.validateForm()) {
      return;
    }

    let response;
    try {
      if (this.pricingModel.id) {
        response = await this.employeePricingService().update(this.pricingModel);
      } else {
        response = await this.employeePricingService().create(this.pricingModel);
      }
    } catch (err) {
      this.savingError = err;
    }
    this.$root.$emit('bv::hide::modal', 'edit-pricing-modal');
    this.$emit('pricingUpdate', response);
    this.$root.$emit('update_flags');
  }

  arrayFromEnum(enumeration: any): any[] {
    const result = [];
    for (const item in enumeration) {
      if (enumeration.hasOwnProperty(item)) {
        result.push({ value: item, text: item });
      }
    }
    return result;
  }

  validateForm(): boolean {
    if (isNaN(this.pricingModel.price)) {
      this.savingError = 'Price should be a number!';
      return false;
    }
    if (!this.pricingModel.employeeRoleId) {
      this.savingError = 'You have to choose a role!';
      return false;
    }
    if (!this.pricingModel.paymentType) {
      this.savingError = 'You have to choose a payment type!';
      return false;
    }
    if (!this.pricingModel.currency) {
      this.savingError = 'You have to choose a currency!';
      return false;
    }
    const existing = this.pricings.find(
      pricing => pricing.employeeRoleId === this.pricingModel.employeeRoleId && pricing.level === this.pricingModel.level
    );
    if (existing && existing.id !== this.pricingModel.id) {
      this.savingError = 'Pricing with this role and level already exists in the list!';
      return false;
    }
    this.savingError = null;
    return true;
  }

  reset() {
    this.savingError = null;
  }
}
