import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import CompetitorsService from '@/entities/competitors/competitors.service';
import { Competitors, ICompetitors } from '@/shared/model/competitors.model';
import { IOffices, Offices } from '@/shared/model/offices.model';
import Results from '@/core/results/results.component';
import AccountService from '@/account/account.service';
import UserGroupRightsService from '@/entities/user-group-rights/user-group-rights.service';
import LegalService from '@/entities/legal/legal.service';
import { yandexMap, ymapMarker } from 'vue-yandex-maps';
import { HrInfo } from '@/shared/model/hr-info.model';
import AccessMixin from '@/account/AccessMixin';
import Representatives from '@/core/representatives/representatives.vue';
import Clients from '@/core/competitor/clients/clients.vue';
import FinanceGeneral from './finance/finance-general.vue';
import FinanceRatios from './finance/finance-ratios.vue';
import Shareholders from '@/core/competitor/legal/shareholders.vue';
import Dialogs from '@/core/dialogs/dialogs.vue';
import TechnologyGeneral from './technology/technology-general.vue';
import { mapGetters } from 'vuex';
import LegalAgenciesComponent from '@/core/competitor/legal/legal-agencies.vue';
import LegalInformationComponent from '@/core/competitor/legal/legal-information.vue';
import PrNetworksComponent from '@/core/competitor/pr/pr-networks.vue';
import HrInformationComponent from '@/core/competitor/hr/hr-information.vue';
import CapitalService from '@/entities/capital/capital.service';
import ClientsPricingComponent from '@/core/competitor/clients/clients-pricing.vue';
import ClientsIndustryComponent from '@/core/competitor/clients/clients-industry.vue';
import GoogleAlertsComponent from '@/core/competitor/pr/google-alerts.vue';
import WorkforceService from '@/entities/workforce/workforce.service';
import { IWorkforce, Workforce } from '@/shared/model/workforce.model';
import RecruitersComponent from '@/core/competitor/hr/recruiters.vue';
import AddAgency from '@/core/competitor/add-agency.vue';
import EditCompetitorMain from '@/core/competitor/edit-competitor-main.vue';
import { CompetitiveRates } from '@/shared/model/competitive-rates.model';
import TypologyComponent from '@/core/competitor/hr/typology.vue';
import { FillFlags } from '@/shared/model/fill-flags.model';
import EmployeesComponent from '@/core/competitor/hr/employees/employees.vue';
import ConfirmModal from '@/core/components/confirm-modal.vue';
import PrStatisticsComponent from '@/core/competitor/pr/pr-statistics.vue';
import { DialogsModel } from '@/shared/model/dialogs.model';
import DialogsService from '@/entities/dialogs/dialogs.service';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

const sections = {
  1: 'Legal',
  2: 'Finance',
  3: 'Clients',
  4: 'Technologies',
  5: 'HR',
  6: 'PR & Networks'
};

const sectionViewRoles = {
  LEGAL_VIEW: 1,
  FINANCE_VIEW: 2,
  CLIENT_VIEW: 3,
  TECH_VIEW: 4,
  HR_VIEW: 5,
  PR_VIEW: 6
};

@Component({
  components: {
    'yandex-map': yandexMap,
    'map-marker': ymapMarker,
    representatives: Representatives,
    clients: Clients,
    shareholders: Shareholders,
    dialogs: Dialogs,
    'finance-general': FinanceGeneral,
    'finance-ratios': FinanceRatios,
    'legal-agencies': LegalAgenciesComponent,
    'legal-information': LegalInformationComponent,
    'pr-networks': PrNetworksComponent,
    TechnologyGeneral,
    'hr-information': HrInformationComponent,
    'clients-pricing': ClientsPricingComponent,
    'clients-industry': ClientsIndustryComponent,
    'google-alerts': GoogleAlertsComponent,
    'add-agency-modal': AddAgency,
    'edit-competitor-main': EditCompetitorMain,
    recruiters: RecruitersComponent,
    typology: TypologyComponent,
    employees: EmployeesComponent,
    'confirm-modal': ConfirmModal,
    'pr-statistics': PrStatisticsComponent
  },
  computed: {
    ...mapGetters({
      referenceCompetitor: 'referenceCompetitor',
      competitor: 'competitor',
      competitorsInGroup: 'competitorsInGroup',
      competitorIsListed: 'competitorIsListed',
      competitorIsIndependent: 'competitorIsIndependent',
      competitorHasPrivateCapital: 'competitorHasPrivateCapital'
    })
  }
})
export default class Competitor extends Mixins(AccessMixin) {
  @Inject('competitorsService') private competitorsService: () => CompetitorsService;
  @Inject('workforceService') private workforceService: () => WorkforceService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('userGroupRightsService') private userGroupRights: () => UserGroupRightsService;
  @Inject('legalService') private legalService: () => LegalService;
  @Inject('capitalService') private capitalService: () => CapitalService;
  @Inject('dialogsService') private dialogsService: () => DialogsService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;

  public referenceCompetitor!: Competitors;
  public competitorsInGroup!: Competitors[];
  public competitor!: Competitors;
  public countrySelected = true;
  public selectedOffice: IOffices = null;
  public editOffice = false;
  public selectedOfficeNum = -1;
  public selectedSection = -1;
  public selectedSubSection = -1;
  public shownOffices = null;
  public hasLeft = false;
  public hasRight = false;
  public start = 0;
  public menuSize = 100; // NO SCROLLING
  public stop = this.menuSize;
  public selectedCompetitor = null;
  public showBack = false;
  public fillFlags = new FillFlags();
  public miniMessage = '';
  public maximalTotalRate = null;

  data() {
    return {
      competitor_id: this.$route.params.id ? this.$route.params.id : 0,
      what_field: this.$route.params.what ? this.$route.params.what : '',
      where_field: this.$route.params.where ? this.$route.params.where : '',
      filter: this.$route.params.filter ? this.$route.params.filter : '',
      region: this.$route.params.region ? this.$route.params.region : '',
      country: this.$route.params.country ? this.$route.params.country : '',
      empFrom: this.$route.params.empFrom ? this.$route.params.empFrom : 0,
      empTo: this.$route.params.empTo ? this.$route.params.empTo : 0,
      transparency: this.$route.params.transparency ? this.$route.params.transparency : null,
      privateEquity: this.$route.params.privateEquity ? this.$route.params.privateEquity : null,
      listed: this.$route.params.listed ? this.$route.params.listed : null
    };
  }

  @Watch('$route', { immediate: true, deep: true })
  async onUrlChange(from: any, to: any) {
    if (from && to && from.params.id !== to.params.id) {
      await this.refill();
      this.updateFillFlags();
    }
  }

  async mounted() {
    window.addEventListener('scroll', this.showFloat);
    this.$root.$on('agency_added', this.selectCity);
    this.$root.$on('agency_deleted', this.selectLastCityIfCurrentIsDeleted);
    this.$root.$on('update_flags', this.updateFillFlags);
    await this.refill();
    this.selectTab();
    this.updateFillFlags();
    this.maximalTotalRate = await this.competitiveRatesService().getMaximalTotalRate();
  }

  public get office(): Offices | null {
    return this.competitor && this.competitor.offices && this.selectedOffice
      ? this.competitor.offices.find(o => o.id === this.selectedOffice.id)
      : null;
  }

  public get mainOffice(): IOffices | null {
    return this.competitor && this.competitor.offices ? this.competitor.offices.find(o => o.mainOffice) : null;
  }

  public get hrInfo(): HrInfo {
    return this.competitor && this.competitor.hr && this.competitor.hr.length > 0 ? this.competitor.hr[0] : new HrInfo();
  }

  public get isReference(): boolean {
    return this.referenceCompetitor && Number(this.$route.params.id) === this.referenceCompetitor.id;
  }

  public get revenue(): number {
    if (this.countrySelected) {
      return (
        this.lastYearRevenue(this.competitor) ||
        (this.competitor.infogreffe && this.competitor.infogreffe.length > 0 ? parseFloat(this.competitor.infogreffe[0].ca1) : 0)
      );
    }
    return 0;
  }

  public get employees(): number {
    return this.office
      ? this.office.numberEmployees
      : this.competitor.offices
      ? this.competitor.offices.reduce((a, b) => a + b.numberEmployees, 0)
      : 0;
  }

  public get consultants(): number {
    return this.office
      ? this.office.numberConsultants
      : this.competitor.offices
      ? this.competitor.offices.reduce((a, b) => a + b.numberConsultants, 0)
      : 0;
  }

  public get site(): string {
    let site = this.competitor && this.competitor.webSite ? this.competitor.webSite : '';
    if (site.length > 0 && !site.includes('http')) {
      site = '\\\\' + site;
    }
    return site;
  }

  public get phone(): string {
    const office = this.office || this.mainOffice;
    return office && office.phone ? office.phone : this.competitor.countryPhone || '';
  }

  public get founded(): string {
    let str = '';
    if (this.office) {
      str += this.$t('competitor.openedIn', [this.getCity(this.office).toString()]);
      if (this.office.established) {
        str += ', ' + new Date(this.office.established).getFullYear();
      }
    } else {
      str += this.$t('competitor.establishedIn', [this.competitor && this.competitor.country ? this.competitor.country.value : '']);
      if (this.competitor.legal && this.competitor.legal.length > 0 && this.competitor.legal[0].founded) {
        str += ', ' + new Date(this.competitor.legal[0].founded).getFullYear();
      }
    }
    return str;
  }

  public get address(): string {
    let str = '';
    const office = this.office || this.mainOffice;
    if (office) {
      str = (office.address || 'XXXXXXX') + '<br>' + (office.post ? `${office.post} ` : '') + (office.cityAsText || '');
    }
    return str;
  }

  public get mapAddress(): string {
    let str = this.address;
    if (str == null) {
      return '';
    }
    str = str.trim();
    str = str.replace('<br>', ' ');
    str = str.replace('&nbsp;', '+');
    str = str.replace(' ', '+');
    str = str.replace(/ /g, '+');
    return str;
  }

  get dialogCount(): number {
    const dialogs = [];
    if (this.competitor && this.competitor.dialogs && this.selectedSection !== -1 && this.selectedSubSection !== -1) {
      const messages = this.competitor.dialogs.filter(dialog => dialog.section === 'General');
      messages.forEach(message => {
        if (!dialogs.includes(message.topic)) {
          dialogs.push(message.topic);
        }
      });
    }
    return dialogs.length;
  }

  public get groupTitle(): string {
    return this.competitor.globalGroups && this.competitor.globalGroups ? this.competitor.globalGroups.name : '';
  }

  public get groupSite() {
    let site =
      this.competitor && this.competitor.globalGroups && this.competitor.globalGroups.webSite ? this.competitor.globalGroups.webSite : '';
    if (site.length > 0 && !site.includes('http')) {
      site = '\\\\' + site;
    }
    return site;
  }

  public get groupConsultants(): number {
    return this.competitorsInGroup.reduce((prev, next) => {
      return (
        prev +
        (next.offices && next.offices.length > 0
          ? next.offices.reduce((prevOff, nextOff) => {
              return prevOff + nextOff.numberConsultants;
            }, 0)
          : 0)
      );
    }, 0);
  }

  public get groupEmployees(): number {
    return this.competitorsInGroup.reduce((prev, next) => {
      return (
        prev +
        (next.offices && next.offices.length > 0
          ? next.offices.reduce((prevOff, nextOff) => {
              return prevOff + nextOff.numberEmployees;
            }, 0)
          : 0)
      );
    }, 0);
  }

  public get groupRevenue() {
    return this.competitorsInGroup.reduce((prev, next) => {
      return (
        prev + (this.lastYearRevenue(next) || (next.infogreffe && next.infogreffe.length > 0 ? parseFloat(next.infogreffe[0].ca1) : 0))
      );
    }, 0);
  }

  public get rates(): CompetitiveRates {
    if (this.competitor && this.competitor.competitiveRates && this.competitor.competitiveRates.length > 0) {
      return this.competitor.competitiveRates[0];
    }
    return new CompetitiveRates();
  }

  public navigateBack(): void {
    const query = {
      what: this.$data.what_field,
      where: this.$data.where_field,
      filter: this.$data.filter,
      region: this.$data.region,
      country: this.$data.country
    };
    if (this.$data.empFrom !== 0) {
      Object.assign(query, { empFrom: this.$data.empFrom.toString() });
    }
    if (this.$data.empTo !== 0) {
      Object.assign(query, { empTo: this.$data.empTo.toString() });
    }
    if (this.$data.transparency !== null) {
      Object.assign(query, { transparency: this.$data.transparency.toString() });
    }
    if (this.$data.privateEquity !== null) {
      Object.assign(query, { privateEquity: this.$data.privateEquity.toString() });
    }
    if (this.$data.listed !== null) {
      Object.assign(query, { listed: this.$data.listed.toString() });
    }
    this.$router.push({
      name: 'Results',
      query
    });
  }

  public lastYearRevenue(competitor: Competitors): number {
    const lastYear =
      competitor.finances && competitor.finances.length > 0
        ? competitor.finances.reduce((prev, current) => (prev.year > current.year ? prev : current))
        : null;
    return lastYear ? lastYear.revenue : null;
  }

  private selectLastCityIfCurrentIsDeleted() {
    const isSelectedCityDeleted =
      this.competitor.offices.filter(office => this.selectedOffice && office.id === this.selectedOffice.id).length === 0;
    if (isSelectedCityDeleted) {
      this.selectCityNum(this.competitor.offices.length - 1);
    } else {
      this.refresh();
    }
  }

  public selectTab(): void {
    if (this.$route.query.section && this.$route.query.subsection) {
      const section = Number(this.$route.query.section);
      const accessKey = Object.keys(sectionViewRoles).find(key => sectionViewRoles[key] === section);
      if (this.hasAccess(accessKey)) {
        this.selectedSection = section;
        this.selectedSubSection = Number(this.$route.query.subsection);
      }
    } else {
      for (const sectionViewRolesKey in sectionViewRoles) {
        if (this.hasAccess(sectionViewRolesKey)) {
          this.selectedSection = sectionViewRoles[sectionViewRolesKey];
          this.selectedSubSection = 1;
          break;
        }
      }
    }
  }

  public async updateFillFlags(): Promise<void> {
    this.fillFlags = await this.competitorsService().updateFillFlags(this.competitor.id, this.competitor.legal[0].siren);
  }

  public async refill(): Promise<void> {
    this.$root.$emit('loading_start');

    const response = await this.competitorsService().find(Number(this.$route.params.id));
    const competitorsInGroup = await this.competitorsService().findByGroupId(response.globalGroups.id);
    this.$store.commit('setCompetitorsInGroup', competitorsInGroup);
    if (response.legal && response.legal.length > 0) {
      const capital = await this.capitalService().retrieveBySiren(response.legal[0].siren);
      if (capital && capital[0]) {
        this.$store.commit('setCompetitorIsListed', capital[0].listed);
        this.$store.commit('setCompetitorIsIndependent', capital[0].independentC);
        this.$store.commit('setCompetitorHasPrivateCapital', capital[0].privateCapital);
      } else {
        this.$store.commit('setCompetitorIsListed', false);
        this.$store.commit('setCompetitorIsIndependent', false);
        this.$store.commit('setCompetitorHasPrivateCapital', false);
      }
    }

    response.offices = response.offices.sort((a, b) => {
      return parseInt(a.name, 10) - parseInt(b.name, 10);
    });

    this.selectedCompetitor = this.competitorsInGroup.find(comp => comp.id === Number(this.$route.params.id));
    this.$store.commit('setCompetitor', response);

    const workforces: IWorkforce[] = await this.workforceService().findByCompetitorId(this.competitor.id);
    this.$store.commit('setWorkforces', workforces);

    this.refresh();
    if (!this.countrySelected) {
      this.selectCityNum(this.selectedOfficeNum); // Refresh FIX
    }

    this.selectInfo();
    this.$root.$emit('loading_stop');
  }

  public selectCountry(): void {
    this.countrySelected = true;
    this.selectedOffice = null;
    this.selectedOfficeNum = -1;
    this.refresh();
  }

  public formatNumber(number: number): String {
    let result = number.toString();
    if (number > 999999999) {
      result = (number / 1000000000).toFixed(0) + 'B';
    } else if (number > 999999) {
      result = (number / 1000000).toFixed(0) + 'M';
    } else if (number > 9999) {
      result = (number / 1000).toFixed() + 'K';
    }
    return result;
  }

  isSelected(section: number): boolean {
    return this.selectedSection === section;
  }

  isSelectedSubSection(section: number, subSection: number): boolean {
    return this.isSelected(section) && this.selectedSubSection === subSection;
  }

  isSubsectionShown(subSection: number): boolean {
    return this.selectedSubSection === subSection;
  }

  public setSection(num: number): void {
    this.selectedSection = num;
    this.selectedSubSection = 1;
  }

  setSubsection(subSection: number): void {
    this.selectedSubSection = subSection;
  }

  public selectInfo(): void {
    this.setSection(this.selectedSection);
  }

  public getCity(office: IOffices): String {
    if (!office) {
      return '';
    }
    return office.cityAsText ? office.cityAsText : office.city ? office.city.value : '';
  }

  public selectCity(id): void {
    this.countrySelected = false;

    for (let i = 0; i < this.shownOffices.length; i++) {
      if (this.shownOffices[i].id === id) {
        this.selectedOffice = this.shownOffices[i];
        this.selectedOfficeNum = i;
      }
    }
    this.refresh();
  }

  public selectCityNum(n): void {
    this.countrySelected = false;

    for (let i = 0; i < this.shownOffices.length; i++) {
      if (i === n) {
        this.selectedOffice = this.shownOffices[i];
        this.selectedOfficeNum = i;
      }
    }
    this.refresh();
  }

  public refresh(): void {
    if (this.competitor && this.competitor.offices.length > this.menuSize) {
      this.shownOffices = this.competitor.offices.slice(this.start, this.stop);
      this.hasRight = this.stop < this.competitor.offices.length;
      this.hasLeft = this.start > 0;
    } else {
      this.shownOffices = this.competitor.offices;
      this.hasLeft = false;
      this.hasRight = false;
    }
  }

  public moveLeft(): void {
    this.start--;
    this.stop--;
    this.shownOffices = this.competitor ? this.competitor.offices.slice(this.start, this.stop) : 0;
    if (this.countrySelected) {
      this.selectCountry();
    } else {
      if (this.selectedOfficeNum < this.menuSize - 1) {
        this.selectCityNum(this.selectedOfficeNum + 1);
      } else {
        this.selectCityNum(this.selectedOfficeNum);
      }
    }
  }

  public moveRight(): void {
    this.start++;
    this.stop++;
    this.shownOffices = this.competitor ? this.competitor.offices.slice(this.start, this.stop) : 0;
    if (this.countrySelected) {
      this.selectCountry();
    } else {
      if (this.selectedOfficeNum > 0) {
        this.selectCityNum(this.selectedOfficeNum - 1);
      } else {
        this.selectCityNum(this.selectedOfficeNum);
      }
    }
  }

  public showEditMain(): void {
    if (this.countrySelected) {
      this.$root.$emit('bv::show::modal', 'edit-competitor-main');
    } else {
      this.editOffice = true;
      this.$root.$emit('bv::show::modal', 'add-agency-modal');
    }
  }

  public showDeleteCompetitorModal() {
    this.$root.$emit('bv::show::modal', 'confirm-modal');
  }

  public async deleteCompetitor(): Promise<void> {
    const currentId = Number(this.$route.params.id);
    await this.competitorsService().delete(currentId);
    if (this.competitorsInGroup.length > 1) {
      this.$router.push({
        name: 'Competitor',
        params: {
          id: this.competitorsInGroup.find(competitor => competitor.id !== currentId).id.toString()
        }
      });
    } else {
      this.$router.push({ name: 'Results' });
    }
  }

  public addAgency(): void {
    this.editOffice = false;
    this.$root.$emit('bv::show::modal', 'add-agency-modal');
  }

  public getLogo(): string {
    return this.competitor && this.competitor.globalGroups && this.competitor.globalGroups.logo
      ? 'data:' + this.competitor.globalGroups.logoContentType + '";base64,' + this.competitor.globalGroups.logo
      : '../../content/images/logo2.svg';
  }

  public selectRepresentatives(): void {
    this.setSubsection(3);
    if (this.competitor.legal && this.competitor.legal.length > 0) {
      localStorage.setItem('siren', this.competitor.legal[0].siren);
    }
  }

  public selectShareholders(): void {
    this.setSubsection(4);
  }

  public selectFinanceRatios(): void {
    this.setSubsection(3);
  }

  public showCompetitor(inId): void {
    this.$router.push({
      name: 'Competitor',
      params: { id: inId, what: this.$data.what_field, where: this.$data.where_field, filter: this.$data.filter }
    });
    this.$data.competitor_id = inId;
    this.setSection(1);
    this.setSubsection(1);
  }

  public addCompany(): void {
    this.$store.commit('setCreateGroup', false);
    this.$root.$emit('bv::show::modal', 'add-competitor');
  }

  public getLocationsNumber(): number {
    let ans = 0;
    for (let j = 0; j < this.competitorsInGroup.length; j++) {
      const comp = this.competitorsInGroup[j];
      ans += comp.offices.length;
    }
    return ans;
  }

  public showFloat(): void {
    this.showBack = !this.isReference && document.documentElement.scrollTop > 130;
  }

  public flagColors(flag: number): string {
    let str = ' ';
    if (flag === 0) {
      str += 'red';
    } else if (flag === 1) {
      str += 'yellow';
    } else if (flag === 2) {
      str += 'green';
    }
    return str;
  }

  public async saveMiniDialog(): Promise<void> {
    this.miniMessage = this.miniMessage.trim();
    if (this.miniMessage === '') {
      return;
    }
    const newDialog = new DialogsModel();
    newDialog.topic = '@QUICK';
    newDialog.message = this.miniMessage;
    newDialog.author = this.$store.getters.account.login;
    newDialog.section = 'General';
    const comp = new Competitors();
    comp.id = Number(this.$route.params.id);
    newDialog.competitors = comp;
    newDialog.date = new Date();

    await this.dialogsService().create(newDialog);
    this.$root.$emit('change_in_dia');
    this.miniMessage = '';
  }

  getStars(): number {
    if (this.maximalTotalRate === null) {
      return 0;
    }
    return Math.round((5 * this.rates.totalRate) / this.maximalTotalRate);
  }
}
