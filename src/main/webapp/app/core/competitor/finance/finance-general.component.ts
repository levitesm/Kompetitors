import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';
import { IAnnualAccount } from '@/shared/model/annual-account.model';
import { mapGetters } from 'vuex';
import FinanceChart from '@/core/competitor/finance/finance-chart.vue';
import HeadOfComponent from '@/core/competitor/head-of.vue';
import CompetitiveRateComponent from '@/core/competitor/competitive-rate.vue';
import { People } from '@/shared/model/people.model';
import EditFinanceModal from '@/core/competitor/finance/edit-finance.vue';
import FinanceFormulaMixin from '@/shared/mixins/finance-formula-mixin';
import { Competitors } from '@/shared/model/competitors.model';
import PeopleService from '@/entities/people/people.service';
import AccessMixin from '@/account/AccessMixin';
import Question from '@/core/components/question.vue';
import { RateTypes } from '@/shared/model/competitive-rates.model';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

export const HEAD_FINANCE = 'HEAD_FINANCE';

@Component({
  components: {
    'finance-chart': FinanceChart,
    'head-of': HeadOfComponent,
    'competitive-rate': CompetitiveRateComponent,
    'edit-finance-modal': EditFinanceModal,
    Question
  },
  computed: {
    ...mapGetters({
      annualAccounts: 'annualAccounts',
      competitor: 'competitor'
    })
  },
  props: {
    siren: String
  }
})
export default class FinanceGeneral extends Mixins(FinanceFormulaMixin, AccessMixin) {
  public competitor!: Competitors;

  @Inject('annualAccountService') private annualAccountService: () => AnnualAccountService;
  @Inject('peopleService') private peopleService: () => PeopleService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;

  public annualAccounts!: IAnnualAccount[];
  public prevYear = new Date().getFullYear() - 1;

  get headFinance(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === HEAD_FINANCE) : null;
  }

  async mounted() {
    await Promise.all([this.fetchAnnualAccounts(), this.fetchHeadFinance()]);
    this.prevYear = new Date().getFullYear() - 1;
    while (this.grossSales(this.prevYear) === 0 && this.prevYear >= 2014) {
      this.prevYear--; // Show data for the last known year.
    }
    this.$root.$on('update_flags', this.updateRate);
  }

  @Watch('$route', { immediate: true, deep: true })
  async onUrlChange(from: any, to: any) {
    if (from && to && from.params.id !== to.params.id) {
      this.$store.commit('setAnnualAccounts', []);
    }
  }

  private async fetchHeadFinance() {
    const response = await this.peopleService().retrieveByCompetitorIdAndTitle(Number(this.$route.params.id), HEAD_FINANCE);
    response.forEach(employee => this.$store.commit('addCompetitorPeople', employee));
  }

  private async fetchAnnualAccounts() {
    if (!(this.$props.siren === '' || (this.annualAccounts.length > 0 && this.annualAccounts[0].siren === this.$props.siren))) {
      await this.annualAccountService()
        .getAllBySiren(this.$props.siren)
        .then(response => {
          this.$store.commit('setAnnualAccounts', response);
        });
    }
  }
  public showEditFinance(): void {
    this.$root.$emit('bv::show::modal', 'edit-finance-page');
  }
  get headDisplayName(): String {
    let result = '';
    if (this.headFinance) {
      result += this.headFinance.fName || '';
      result += this.headFinance.lName ? ' ' + this.headFinance.lName : '';
    }
    return result;
  }

  get rate() {
    return this.competitor.competitiveRates[0].financeRate;
  }

  async updateRate() {
    this.$store.commit(
      'setCompetitorCompetitiveRates',
      await this.competitiveRatesService().update(Number(this.$route.params.id), RateTypes.FINANCIAL, this.grossSales(this.prevYear))
    );
  }
}
