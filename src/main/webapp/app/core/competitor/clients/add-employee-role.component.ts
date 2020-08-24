import { Inject, Prop, Vue } from 'vue-property-decorator';
import { EmployeeRole } from '@/shared/model/employee-role.model';
import EmployeeRoleService from '@/entities/employee-role/employee-role.service';
import Component from 'vue-class-component';

@Component
export default class AddEmployeeRoleModal extends Vue {
  @Prop() public roles: EmployeeRole[];

  @Inject('employeeRoleService') private employeeRoleService: () => EmployeeRoleService;

  public nameModel = '';
  public savingError = null;

  async save() {
    if (!this.validate()) {
      return;
    }

    try {
      const role = new EmployeeRole();
      role.name = this.nameModel;
      const response = await this.employeeRoleService().create(role);
      this.$emit('addRole', response);
    } catch (err) {
      this.savingError = err;
    }
    this.$root.$emit('bv::hide::modal', 'add-employee-role-modal');
  }

  validate(): boolean {
    if (this.nameModel.length === 0) {
      this.savingError = 'Name should not be empty!';
      return false;
    }
    if (this.roles.find(role => role.name === this.nameModel)) {
      this.savingError = 'Role with that name already exist!';
      return false;
    }
    this.savingError = null;
    return true;
  }
}
