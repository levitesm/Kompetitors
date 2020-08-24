import { Vue, Component, Inject } from 'vue-property-decorator';
import EmployeesTypologyService from '@/entities/employees-typology/employees-typology.service';
import { EmployeesTypology } from '@/shared/model/employees-typology.model';
import TypologyTableComponent from '@/core/competitor/hr/typology-table.vue';
import EmployeeTypeService from '@/entities/employee-type/employee-type.service';
import { EmployeeType } from '@/shared/model/employee-type.model';
import TypologyChartComponent from '@/core/competitor/hr/typology-chart.vue';
import CustomSpinner from '../../../../components/spinner/spinner.vue';

@Component({
  components: {
    'typology-chart': TypologyChartComponent,
    'typology-table': TypologyTableComponent,
    'custom-spinner': CustomSpinner
  }
})
export default class TypologyComponent extends Vue {
  @Inject('employeesTypologyService') private employeesTypologyService: () => EmployeesTypologyService;
  @Inject('employeeTypeService') private employeeTypeService: () => EmployeeTypeService;

  public typologies: EmployeesTypology[] = [];
  public employeeTypes: EmployeeType[] = [];
  public loading = false;

  public get years(): number[] {
    const currentYear = new Date().getFullYear();
    return [currentYear - 1, currentYear, currentYear - 2];
  }

  public get chartTypologies(): EmployeesTypology[] {
    const current = new Date().getFullYear();
    const year = this.haveTypologiesForYear(current) ? current : this.haveTypologiesForYear(current - 1) ? current - 1 : -1;
    return this.typologies.filter(typology => typology.year === year);
  }

  public async mounted(): Promise<void> {
    try {
      this.loading = true;
      await Promise.all([this.fetchEmployeeTypes(), this.fetchTopologies()]);
      this.normaliseTopologies();
    } catch (err) {
      console.log(err);
    } finally {
      this.loading = false;
    }
  }

  public async fetchTopologies(): Promise<void> {
    this.typologies = await this.employeesTypologyService().retrieveByCompetitorId(Number(this.$route.params.id));
  }

  public async fetchEmployeeTypes(): Promise<void> {
    this.employeeTypes = (await this.employeeTypeService().retrieve()).data;
  }

  public normaliseTopologies(): void {
    this.employeeTypes.forEach(type => {
      this.years.forEach(year => {
        const exist = this.typologies.find(typology => typology.year === year && typology.employeeTypeName === type.name);
        if (!exist) {
          this.typologies.push(new EmployeesTypology(null, 0, year, type.id, type.name, Number(this.$route.params.id)));
        }
      });
    });
  }

  public haveTypologiesForYear(year: number): boolean {
    return this.typologies
      .filter(typology => typology.year === year)
      .reduce((result, typology) => {
        return typology.value > 0 || result;
      }, false);
  }

  public updateTypologies(event, data: EmployeesTypology[]): void {
    this.typologies = this.typologies.map(typology => {
      const modified = data.find(item => item.year === typology.year && item.employeeTypeId === typology.employeeTypeId);
      if (modified && !modified.employeeTypeName) {
        modified.employeeTypeName = typology.employeeTypeName;
      }
      return modified ? modified : typology;
    });
  }
}
