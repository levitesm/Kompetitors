import { Component, Inject, Mixins, Prop } from 'vue-property-decorator';
import { EmployeesTypology } from '@/shared/model/employees-typology.model';
import LocalisationMixin from '@/locale/localisation.mixin';
import EmployeesTypologyService from '@/entities/employees-typology/employees-typology.service';
import AccessMixin from '@/account/AccessMixin';

@Component
export default class TypologyTableComponent extends Mixins(LocalisationMixin, AccessMixin) {
  @Inject('employeesTypologyService') private employeesTypologyService: () => EmployeesTypologyService;
  @Prop() readonly typologies: EmployeesTypology[];
  public typologiesModel: EmployeesTypology[] = [];
  public editMode = false;
  public saving = false;

  public get years(): number[] {
    const years = this.typologies.reduce((result, typology) => {
      if (!result.includes(typology.year)) {
        result.push(typology.year);
      }
      return result;
    }, []);
    return years.sort((a, b) => a - b);
  }

  public get typologyNames(): string[] {
    return this.years.length > 0
      ? this.typologies
          .filter(typology => typology.year === this.years[0])
          .reduce((result, typology) => {
            if (!result.includes(typology.employeeTypeName)) {
              result.push(typology.employeeTypeName);
            }
            return result;
          }, [])
          .sort((a, b) => {
            if (a < b) {
              return -1;
            }
            if (a > b) {
              return 1;
            }
            return 0;
          })
      : [];
  }

  public valueByNameAndYear(name: string, year: number): number {
    const typology = this.typologies.find(t => t.employeeTypeName === name && t.year === year);
    return typology ? typology.value : 0;
  }

  public prepareModel(): void {
    this.typologiesModel = JSON.parse(JSON.stringify(this.typologies));
    this.editMode = true;
  }

  public modelByNameAndYear(name: string, year: number): EmployeesTypology {
    return this.typologiesModel.find(typology => typology.employeeTypeName === name && typology.year === year);
  }

  async save() {
    try {
      const changed = this.typologiesModel.filter(typology => {
        typology.value = typology.value ? typology.value : 0;
        if (this.valueByNameAndYear(typology.employeeTypeName, typology.year) !== typology.value) {
          return typology;
        }
      });
      if (changed.length > 0) {
        this.saving = true;
        const response = await this.employeesTypologyService().updateAll(changed);
        this.$emit('onUpdate', response);
        this.$root.$emit('update_flags');
      }
    } catch (err) {
      alert('Failed to update data');
      console.log(err);
    } finally {
      this.editMode = false;
      this.saving = false;
    }
  }
}
