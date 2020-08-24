import { Component, Inject, Mixins } from 'vue-property-decorator';
import { mapGetters } from 'vuex';
import { Competitors } from '@/shared/model/competitors.model';
import DashboardFinanceService from '@/entities/dashboard-finance/dashboard-finance.service';
import { IDashboardFinance } from '@/shared/model/dashboard-finance.model';
import { DashboardUnit } from '@/shared/model/dashboard-unit.model';
import { DashboardStatisticItem } from '@/shared/model/dashboard-statistic-item.model';
import DashboardStatisticItemComponent from '@/core/dashboard/dashboard-statistic-item.vue';
import DashboardCardComponent from '@/core/dashboard/dashboard-card.vue';
import AccessMixin from '@/account/AccessMixin';
import DashboardHrService from '@/entities/dashboard-hr/dashboard-hr.service';
import { IDashboardHR } from '@/shared/model/dashboard-hr.model';
import DashboardFightComponent from '@/core/dashboard/dashboard-fight.vue';
import DashboardMapComponent from '@/core/dashboard/dashboard-map.vue';

@Component({
  components: {
    'dashboard-statistic-item': DashboardStatisticItemComponent,
    'dashboard-card': DashboardCardComponent,
    'dashboard-fight': DashboardFightComponent,
    'dashboard-map': DashboardMapComponent
  },
  computed: {
    ...mapGetters({
      referenceCompetitor: 'referenceCompetitor',
      statistics: 'statistics'
    })
  }
})
export default class Dashboard extends Mixins(AccessMixin) {
  public referenceCompetitor!: Competitors;
  public statistics!: any;
  public check_flag = false;

  private ROW_LIMIT = 100;

  @Inject('dashboardFinanceService') private dashboardFinanceService: () => DashboardFinanceService;

  public tabs: Map<string, string> = new Map([['finance', 'dashboard.finance.tab-name'], ['hr', 'dashboard.hr.tab-name']]);
  public selectedTab = null;

  public selectedStat: DashboardStatisticItem = null;

  public isCompare = true;
  public isFight = false;
  public isMap = false;

  public get currentStatistics(): DashboardUnit[] {
    const units: DashboardUnit[] = this.statistics.hasOwnProperty(this.selectedTab) ? this.statistics[this.selectedTab] : [];
    return units.sort((a, b) => {
      const statA = this.selectedStat ? a.statistics.find(stat => stat.name === this.selectedStat.name).value : 0;
      const statB = this.selectedStat ? b.statistics.find(stat => stat.name === this.selectedStat.name).value : 0;
      return statA - statB;
    });
  }

  public get inferiorStatistics(): DashboardUnit[] {
    const index = this.currentStatistics.indexOf(this.referenceStatistics);
    return this.currentStatistics
      .slice(0, index)
      .reverse()
      .slice(0, this.ROW_LIMIT);
  }

  public get superiorStatistics(): DashboardUnit[] {
    const index = this.currentStatistics.indexOf(this.referenceStatistics);
    return this.currentStatistics.slice(index + 1).slice(0, this.ROW_LIMIT);
  }

  public get referenceStatistics(): DashboardUnit {
    return this.currentStatistics.find(unit => unit.competitorId === Number(this.$route.params.id)) || new DashboardUnit();
  }

  public statNotEmpty(unit: DashboardUnit): boolean {
    const statistic: DashboardStatisticItem = unit.statistics.find(item => item.name === this.selectedStat.name);
    return this.check_flag ? true : statistic ? statistic.value !== null : false;
  }

  async mounted() {
    await this.checkAccess();
  }

  public async checkAccess(): Promise<void> {
    for (const key of Array.from(this.tabs.keys())) {
      if (this.hasTabAccess(key)) {
        this.selectedTab = key;
        await this.setTab(this.selectedTab);
        return;
      }
    }
  }

  public hasTabAccess(tabName: string): boolean {
    if (!tabName) {
      return false;
    }
    return (tabName === 'finance' && this.hasAccess(this.FINANCE_VIEW)) || (tabName === 'hr' && this.hasAccess(this.HR_VIEW));
  }

  public getLogo(): string {
    return this.referenceCompetitor && this.referenceCompetitor.globalGroups && this.referenceCompetitor.globalGroups.logo
      ? 'data:' + this.referenceCompetitor.globalGroups.logoContentType + '";base64,' + this.referenceCompetitor.globalGroups.logo
      : '../../content/images/logo2.svg';
  }

  public selectStat(stat: DashboardStatisticItem) {
    this.selectedStat = stat;
  }

  public async setTab(name: string) {
    if (this.tabs.has(name)) {
      if (name === 'finance') {
        await this.fetchDashboardFinance();
      }
      if (name === 'hr') {
        await this.fetchDashboardHR();
      }
      this.selectedTab = name;
      this.selectedStat = null;
    }
  }

  private async fetchDashboardFinance() {
    const response = (await this.dashboardFinanceService().retrieveByCompetitorId(Number(this.$route.params.id))).sort((a, b) => {
      return a.competitorName.localeCompare(b.competitorName);
    });
    this.$store.commit('setStatistics', { section: 'finance', data: this.mapDashboardFinance(response) });
  }

  private mapDashboardFinance(items: IDashboardFinance[]): DashboardUnit[] {
    const year = items.length > 0 ? items[0].year : 0;
    return items.map(item => {
      const unit = new DashboardUnit(item.competitorId, item.competitorName, year, item.groupLogo, item.groupLogoContentType, []);
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.ebit', item.ebit, '€'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.gross-sales', item.grossSales, '€'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.gross-sales-growth', item.grossSalesGrowth, '%'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.gross-sales-per-employee', item.grossSalesPerEmployee, '€'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.gross-sales-per-consultant', item.grossSalesPerConsultant, '€'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.net-result', item.netResult, '€'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.net-result-growth', item.netResultGrowth, '%'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.net-result-percent', item.netResultPercent, '%'));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.workforce', item.workforce, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.finance.average-pay', item.averagePay, '€'));

      return unit;
    });
  }

  private async fetchDashboardHR() {
    const response = await this.dashboardFinanceService().retrieveHRAll();
    this.$store.commit('setStatistics', { section: 'hr', data: this.mapDashboardHR(response) });
  }

  private mapDashboardHR(items: IDashboardHR[]): DashboardUnit[] {
    const year = new Date().getFullYear();

    return items.map(item => {
      const unit = new DashboardUnit(item.competitorid, item.competitorname, year, item.grouplogo, item.grouplogocontenttype, []);
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.glassdoor', item.glassdoor, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.viadeo', item.viadeo, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.efficiency', item.recruitersefficiency, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.recrNum', item.recruitersnumber, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.interNum', item.interviewsnumber, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.cooptationpremiumamount', item.cooptationpremiumamount, ''));
      unit.statistics.push(new DashboardStatisticItem('dashboard.hr.juniorsalary', item.juniorsalary, ''));
      return unit;
    });
  }

  private selectCompare(): void {
    this.isCompare = true;
    this.isFight = false;
    this.isMap = false;
    this.selectedStat = null;
  }

  private selectFight(): void {
    this.isCompare = false;
    this.isFight = true;
    this.isMap = false;
    this.selectedStat = null;
  }

  private selectMap(): void {
    this.isCompare = false;
    this.isFight = false;
    this.isMap = true;
    this.selectedStat = null;
  }

  checkFlag(): void {
    this.check_flag = !this.check_flag;
  }

  public statDeviationMy(unit: DashboardUnit): number {
    const statistic: DashboardStatisticItem = unit.statistics.find(item => item.name === this.selectedStat.name);
    if (!statistic) {
      return null;
    }
    let sum = 0;
    let num = 0;
    for (let i = 0; i < this.currentStatistics.length; i++) {
      const u = this.currentStatistics[i];
      if (u.competitorId !== unit.competitorId) {
        const ustatistic: DashboardStatisticItem = u.statistics.find(item => item.name === this.selectedStat.name);
        if (ustatistic) {
          sum += ustatistic.value;
          num++;
        }
      }
    }
    const avrgWithout = sum / num;
    const avrgWith = (sum + statistic.value) / (num + 1);
    return Number(((Math.abs(avrgWith - avrgWithout) / avrgWithout) * 100).toFixed(2));
  }

  public statDeviation(unit: DashboardUnit): number {
    const statistic: DashboardStatisticItem = unit.statistics.find(item => item.name === this.selectedStat.name);
    if (statistic === null || statistic.value === null) {
      return null;
    }

    const array = [];

    for (let i = 0; i < this.currentStatistics.length; i++) {
      const u = this.currentStatistics[i];
      const ustatistic: DashboardStatisticItem = u.statistics.find(item => item.name === this.selectedStat.name);
      if (ustatistic !== null && ustatistic.value !== null) {
        array.push(ustatistic.value);
      }
    }

    if (array.length < 4) {
      return null;
    }

    let values, q1, q3, iqr;

    values = array.slice().sort((a, b) => a - b); // copy array fast and sort
    // console.log(values);

    q1 = this.median(values.slice(0, Math.floor(values.length / 2)));
    q3 = this.median(values.slice(Math.ceil(values.length / 2), values.length));

    iqr = q3 - q1;

    // console.log('q1: ' + q1 + '; q3: ' + q3 + '; iqr: ' + iqr + '; value: ' + statistic.value);

    let ans = 0.0;
    if (statistic.value > q3) {
      ans = (statistic.value - q3) / iqr;
    }
    if (statistic.value < q1) {
      ans = (q1 - statistic.value) / iqr;
    }
    return ans;
  }

  median(array: any[]): number {
    if (array.length % 2 === 0) {
      return (array[array.length / 2 - 1] + array[array.length / 2]) / 2;
    } else {
      return array[Math.floor(array.length / 2)];
    }
  }
}
