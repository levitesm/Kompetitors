import { Inject, Mixins } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { mapGetters } from 'vuex';
import { Competitors } from '@/shared/model/competitors.model';
import { HrInfo } from '@/shared/model/hr-info.model';
import { People } from '@/shared/model/people.model';
import HeadOfComponent from '@/core/competitor/head-of.vue';
import CompetitiveRateComponent from '@/core/competitor/competitive-rate.vue';
import PeopleService from '@/entities/people/people.service';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';
import { IAnnualAccount } from '@/shared/model/annual-account.model';
import AccessMixin from '@/account/AccessMixin';
import EmployeesMixin from '@/shared/mixins/employees-mixin';
import { RateTypes } from '@/shared/model/competitive-rates.model';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

export const HEAD_HR = 'HEAD_HR';
export const HEAD_RECR = 'HEAD_RECR';
export const NO_WORKFORCE = 0;

@Component({
  computed: {
    ...mapGetters({
      workforces: 'workforces',
      competitor: 'competitor'
    })
  },
  components: {
    'head-of': HeadOfComponent,
    'competitive-rate': CompetitiveRateComponent
  }
})
export default class HrInformationComponent extends Mixins(EmployeesMixin, AccessMixin) {
  @Inject('annualAccountService') private annualAccountService: () => AnnualAccountService;
  @Inject('peopleService') private peopleService: () => PeopleService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;

  public competitor!: Competitors;
  public workforces!: Object;
  public annualAccounts!: IAnnualAccount[];
  public lastWorkforce = NO_WORKFORCE;
  public lastWorkforceGap = NO_WORKFORCE;

  async mounted() {
    await this.fetchHeadHR();
    await this.fetchHeadRECR();

    this.$store.commit('setAnnualAccounts', await this.annualAccountService().getAllBySiren(this.competitor.legal[0].siren));
    this.$root.$on('computeWorkforces', this.computeWorkforces);
    this.$root.$on('update_flags', this.updateRate);

    this.computeWorkforces();
  }

  computeWorkforces() {
    this.lastWorkforce = this.getLatestAvailableWorkforce();
    this.lastWorkforceGap = this.getLatestWorkforceGap();
  }

  get hrInfo(): HrInfo {
    return this.competitor.hr && this.competitor.hr.length > 0 ? this.competitor.hr[0] : new HrInfo();
  }

  get head_hr(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === HEAD_HR) : null;
  }

  get head_recr(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === HEAD_RECR) : null;
  }

  private async fetchHeadHR() {
    const response = await this.peopleService().retrieveByCompetitorIdAndTitle(Number(this.$route.params.id), HEAD_HR);
    response.forEach(employee => this.$store.commit('addCompetitorPeople', employee));
  }

  private async fetchHeadRECR() {
    const response = await this.peopleService().retrieveByCompetitorIdAndTitle(Number(this.$route.params.id), HEAD_RECR);
    response.forEach(employee => this.$store.commit('addCompetitorPeople', employee));
  }

  public showEditHr(): void {
    localStorage.setItem('competitorId', this.competitor.id.toString());
    this.$root.$emit('bv::show::modal', 'edit-hr-page');
  }

  get headHRDisplayName(): String {
    let result = '';
    if (this.head_hr) {
      result += this.head_hr.fName || '';
      result += this.head_hr.lName ? ' ' + this.head_hr.lName : '';
    }
    return result;
  }

  get headRECRDisplayName(): String {
    let result = '';
    if (this.head_recr) {
      result += this.head_recr.fName || '';
      result += this.head_recr.lName ? ' ' + this.head_recr.lName : '';
    }
    return result;
  }

  get rate() {
    return this.competitor.competitiveRates[0].hrRate;
  }

  async updateRate() {
    this.$store.commit(
      'setCompetitorCompetitiveRates',
      await this.competitiveRatesService().update(Number(this.$route.params.id), RateTypes.HR, this.hrInfo.recruitersNumber)
    );
  }
}
