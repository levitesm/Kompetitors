import { Component, Inject, Mixins } from 'vue-property-decorator';
import EmployeesChart from '@/core/competitor/hr/employees/employees-chart.vue';
import { EmployeeSalaries } from '@/shared/model/employee-salaries.model';
import EmployeeSalariesService from '@/entities/employee-salaries/employee-salaries.service';
import AccessMixin from '@/account/AccessMixin';

@Component({
  components: {
    'employees-chart': EmployeesChart
  }
})
export default class EmployeesComponent extends Mixins(AccessMixin) {
  @Inject('employeeSalariesService') private employeeSalariesService: () => EmployeeSalariesService;

  public seniorityTypes = ['training', 'junior', 'middle', 'senior'];
  public editFlags = -1;
  public salariesFromDB: EmployeeSalaries[] = [];
  public salaries: EmployeeSalaries[] = [];

  async mounted() {
    await this.updateFromDB();
    this.getSalaries();
  }

  public async updateFromDB(): Promise<void> {
    this.salariesFromDB = await this.employeeSalariesService().findByCompetitorId(Number(this.$route.params.id));
  }

  public getSalaries(): void {
    this.salaries = [];
    for (let i = 0; i < this.seniorityTypes.length; i++) {
      const type = this.seniorityTypes[i];
      if (this.findByType(type)) {
        this.salaries.push(this.findByType(type));
      } else {
        const sal = new EmployeeSalaries();
        sal.competitor = Number(this.$route.params.id);
        sal.seniority = type;
        sal.salary = 0;
        this.salaries.push(sal);
      }
    }
  }

  findByType(type: string): any {
    let sal: EmployeeSalaries;
    for (let i = 0; i < this.salariesFromDB.length; i++) {
      sal = this.salariesFromDB[i];
      if (sal.seniority === type) {
        return sal;
      }
    }
    return null;
  }

  public showEditEmployeesNumber(): void {
    this.$root.$emit('bv::show::modal', 'edit-hr-employees');
  }

  edit(index: number): void {
    this.editFlags = index;
  }

  getEditFlags(index: number): boolean {
    return this.editFlags === index;
  }

  async save(index: number): Promise<void> {
    this.salaries[index].salary = Number.parseFloat(this.salaries[index].salary.toString().replace(/[^0-9.]/g, ''));
    this.salaries[index].updateDate = new Date();
    this.salaries[index].updatedBy = this.$store.getters.account.login;
    if (this.salaries[index].id) {
      await this.employeeSalariesService().update(this.salaries[index]);
    } else {
      await this.employeeSalariesService().create(this.salaries[index]);
    }
    this.editFlags = -1;
  }

  getDate(date: Date): string {
    return new Date(date).toLocaleDateString();
  }
}
