import { Inject, Mixins } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { mapGetters } from 'vuex';
import WorkforceService from '@/entities/workforce/workforce.service';
import { Competitors, ICompetitors } from '@/shared/model/competitors.model';
import EmployeesMixin from '@/shared/mixins/employees-mixin';

const NO_WORKFORCE = 0;

@Component({
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  }
})
export default class EditHrEmployees extends Mixins(EmployeesMixin) {
  @Inject('workforceService') private workforceService: () => WorkforceService;
  public competitor!: Competitors;
  public lastYearNumber: number = NO_WORKFORCE;
  public secondToLastYearNumber: number = NO_WORKFORCE;
  public thirdToLastYearNumber: number = NO_WORKFORCE;
  public fourthToLastYearNumber: number = NO_WORKFORCE;
  public fifthToLastYearNumber: number = NO_WORKFORCE;

  mounted() {
    this.setEmployeesNumber();
  }

  private setEmployeesNumber() {
    this.lastYearNumber = this.getWorkforceForYear(this.currentYear - 1);
    this.secondToLastYearNumber = this.getWorkforceForYear(this.currentYear - 2);
    this.thirdToLastYearNumber = this.getWorkforceForYear(this.currentYear - 3);
    this.fourthToLastYearNumber = this.getWorkforceForYear(this.currentYear - 4);
    this.fifthToLastYearNumber = this.getWorkforceForYear(this.currentYear - 5);
  }

  async saveEmployeesNumber() {
    for (const textFieldIndex of [1, 2, 3, 4, 5]) {
      await this.saveEmployeesNumberForTextFieldIndex(textFieldIndex);
    }
    const workforces = await this.workforceService().findByCompetitorId(this.competitor.id);
    this.$store.commit('setWorkforces', workforces);
    this.$root.$emit('computeWorkforces');
    this.$root.$emit('bv::hide::modal', 'edit-hr-employees');
  }

  private async saveEmployeesNumberForTextFieldIndex(textFieldIndex: number) {
    const year: number = this.currentYear - textFieldIndex;
    const employeeNumber: number = this.employeeNumberForTextFieldIndex(textFieldIndex);
    const competitor: ICompetitors = new Competitors(this.competitor.id);
    if (this.workForce(year) === NO_WORKFORCE && employeeNumber > NO_WORKFORCE) {
      const workforce = {
        year: new Date(year.toString()),
        employeeNumber,
        competitor
      };
      const maybeExistingWorkforce = this.workforces[year.toString()];
      if (maybeExistingWorkforce) {
        await this.workforceService().update({
          id: maybeExistingWorkforce.id,
          ...workforce
        });
      } else {
        await this.workforceService().create(workforce);
      }
    }
  }

  employeeNumberForTextFieldIndex(textFieldIndex: number) {
    switch (textFieldIndex) {
      case 1:
        return this.lastYearNumber;
      case 2:
        return this.secondToLastYearNumber;
      case 3:
        return this.thirdToLastYearNumber;
      case 4:
        return this.fourthToLastYearNumber;
      case 5:
        return this.fifthToLastYearNumber;
      default:
        return 0;
    }
  }
}
