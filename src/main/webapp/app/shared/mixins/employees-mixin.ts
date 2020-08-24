import Component from 'vue-class-component';
import { mapGetters } from 'vuex';
import { ICompetitors } from '@/shared/model/competitors.model';
import { Inject, Mixins } from 'vue-property-decorator';
import AnnualAccountMixin from '@/shared/mixins/annual-account-mixin';
import TimeTool from '@/tools/TimeTool';

const NO_WORKFORCE = 0;

const MAX_YEARS_GAP = 5;
@Component({
  computed: {
    ...mapGetters({
      workforces: 'workforces'
    })
  }
})
export default class EmployeesMixin extends Mixins(AnnualAccountMixin) {
  @Inject('timeTool') private timeTool: () => TimeTool;
  public competitor!: ICompetitors;
  public workforces!: object;
  public currentYear: number = this.timeTool().getFullYear();

  workForce(year: number): number {
    return this.valueByCodeAndYear('876', year) || this.valueByCodeAndYear('MYP', year) || NO_WORKFORCE;
  }

  getWorkforceForYear(year: number) {
    const infogreffeWorkforce = this.workForce(year);
    if (infogreffeWorkforce !== NO_WORKFORCE) {
      return infogreffeWorkforce;
    }
    const maybeWorkforce = this.workforces[year.toString()];
    if (maybeWorkforce) {
      return maybeWorkforce.employeeNumber;
    }
    return NO_WORKFORCE;
  }

  getLatestYearWithWorkforce() {
    let year = this.currentYear;
    while (this.getWorkforceForYear(year) === NO_WORKFORCE && this.currentYear - year < MAX_YEARS_GAP) {
      year--;
    }
    return year;
  }

  getLatestWorkforces() {
    let workforces = [];
    [1, 2, 3, 4, 5].forEach(yearGap => {
      const year = this.currentYear - yearGap;
      const employeeNumber = this.getWorkforceForYear(year);
      workforces = [
        ...workforces,
        {
          year,
          employeeNumber
        }
      ];
    });
    return workforces;
  }

  hasInfogreffeForYear(year: number) {
    return this.workForce(year) !== NO_WORKFORCE;
  }

  getLatestAvailableWorkforce() {
    const latestYearWithWorkforce = this.getLatestYearWithWorkforce();
    return this.getWorkforceForYear(latestYearWithWorkforce);
  }

  getLatestWorkforceGap() {
    const latestYearWithWorkforce = this.getLatestYearWithWorkforce();
    const latestAvailableWorkforce = this.getLatestAvailableWorkforce();
    const workforceBeforeLatestAvailableWorkforce = this.getWorkforceForYear(latestYearWithWorkforce - 1);
    if (latestAvailableWorkforce === NO_WORKFORCE || workforceBeforeLatestAvailableWorkforce === NO_WORKFORCE) {
      return NO_WORKFORCE;
    }
    return latestAvailableWorkforce - workforceBeforeLatestAvailableWorkforce;
  }
}
