// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';

import VueTheMask from 'vue-the-mask';
import VueCurrencyInput from 'vue-currency-input';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import ClientsService from '@/entities/clients/clients.service';
import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';
import CompetitorsService from '@/entities/competitors/competitors.service';
import DialogsService from '@/entities/dialogs/dialogs.service';
import FinanceService from '@/entities/finance/finance.service';
import GlobalGroupsService from '@/entities/global-groups/global-groups.service';
import HrInfoService from '@/entities/hr-info/hr-info.service';
import LegalService from '@/entities/legal/legal.service';
import ListActivitiesService from '@/entities/list-activities/list-activities.service';
import ListCitiesService from '@/entities/list-cities/list-cities.service';
import ListCityCountriesService from '@/entities/list-city-countries/list-city-countries.service';
import ListClientsProjectTypesService from '@/entities/list-clients-project-types/list-clients-project-types.service';
import ListCompetanciesService from '@/entities/list-competancies/list-competancies.service';
import ListCountriesService from '@/entities/list-countries/list-countries.service';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';
import ListOwnershipsService from '@/entities/list-ownerships/list-ownerships.service';
import ListPricingsService from '@/entities/list-pricings/list-pricings.service';
import ListProjectTypesService from '@/entities/list-project-types/list-project-types.service';
import ListServicesService from '@/entities/list-services/list-services.service';
import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';
import ListToolsService from '@/entities/list-tools/list-tools.service';
import OfficesService from '@/entities/offices/offices.service';
import PeopleService from '@/entities/people/people.service';
import PrInfoService from '@/entities/pr-info/pr-info.service';
import SocieteMainService from '@/entities/societe-main/societe-main.service';
import TechCompetanciesService from '@/entities/tech-competancies/tech-competancies.service';
import TechPartnersService from '@/entities/tech-partners/tech-partners.service';
import TechProjectsService from '@/entities/tech-projects/tech-projects.service';
import TechServicesService from '@/entities/tech-services/tech-services.service';
import TechToolsService from '@/entities/tech-tools/tech-tools.service';
import UserGroupRightsService from '@/entities/user-group-rights/user-group-rights.service';
import AccessKeyService from '@/entities/access-key/access-key.service';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';
import AnnualAccountStatisticsService from '@/entities/annual-account-statistics/annual-account-statistics.service';
import RepresentativesService from '@/entities/representatives/representatives.service';
import CapitalService from '@/entities/capital/capital.service';
import ShareHoldersService from '@/entities/share-holders/share-holders.service';
import UpdatehistoryService from '@/entities/updatehistory/updatehistory.service';
import RegionListService from '@/entities/region-list/region-list.service';
import RegionZipListService from '@/entities/region-zip-list/region-zip-list.service';
import EmployeeRoleService from '@/entities/employee-role/employee-role.service';
import EmployeePricingService from '@/entities/employee-pricing/employee-pricing.service';
import CompetitorIndustryService from '@/entities/competitor-industry/competitor-industry.service';
import DashboardFinanceService from '@/entities/dashboard-finance/dashboard-finance.service';
import TechInfoService from '@/entities/tech-info/tech-info.service';
import WorkforceService from '@/entities/workforce/workforce.service';
import TimeTool from '@/tools/TimeTool';
import EmployeeTypeService from '@/entities/employee-type/employee-type.service';
import EmployeesTypologyService from '@/entities/employees-typology/employees-typology.service';
import EmployeeSalariesService from '@/entities/employee-salaries/employee-salaries.service';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';
import TechPracticesService from '@/entities/tech-practices/tech-practices.service';
import ListPracticesService from '@/entities/list-practices/list-practices.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here
// import VueLayers from 'vuelayers';
// import 'vuelayers/lib/style.css'; // needs css-loader
//
// Vue.use(VueLayers);

Vue.use(VueTheMask);
Vue.use(VueCurrencyInput, {
  globalOptions: {
    currency: 'RUB',
    locale: 'ru'
  }
});

Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n, (<any>Vue).cookie);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, (<any>Vue).cookie, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    if (!accountService.hasAnyAuthority(to.meta.authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
    } else {
      next();
    }
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,

    auditsService: () => new AuditsService(),

    healthService: () => new HealthService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    alertService: () => alertService,
    translationService: () => translationService,
    clientsService: () => new ClientsService(),
    clientsProjectsService: () => new ClientsProjectsService(),
    competitiveRatesService: () => new CompetitiveRatesService(),
    competitorsService: () => new CompetitorsService(),
    dialogsService: () => new DialogsService(),
    financeService: () => new FinanceService(),
    globalGroupsService: () => new GlobalGroupsService(),
    hrInfoService: () => new HrInfoService(),
    legalService: () => new LegalService(),
    listActivitiesService: () => new ListActivitiesService(),
    listCitiesService: () => new ListCitiesService(),
    listCityCountriesService: () => new ListCityCountriesService(),
    listClientsProjectTypesService: () => new ListClientsProjectTypesService(),
    listCompetanciesService: () => new ListCompetanciesService(),
    listPracticesService: () => new ListPracticesService(),
    listCountriesService: () => new ListCountriesService(),
    listIndustriesService: () => new ListIndustriesService(),
    listOwnershipsService: () => new ListOwnershipsService(),
    listPricingsService: () => new ListPricingsService(),
    listProjectTypesService: () => new ListProjectTypesService(),
    listServicesService: () => new ListServicesService(),
    listTechPartnersService: () => new ListTechPartnersService(),
    listToolsService: () => new ListToolsService(),
    officesService: () => new OfficesService(),
    peopleService: () => new PeopleService(),
    prInfoService: () => new PrInfoService(),
    societeMainService: () => new SocieteMainService(),
    techCompetanciesService: () => new TechCompetanciesService(),
    techPracticesService: () => new TechPracticesService(),
    techPartnersService: () => new TechPartnersService(),
    techProjectsService: () => new TechProjectsService(),
    techServicesService: () => new TechServicesService(),
    techToolsService: () => new TechToolsService(),
    userGroupRightsService: () => new UserGroupRightsService(),
    accessKeyService: () => new AccessKeyService(),
    annualAccountService: () => new AnnualAccountService(),
    annualAccountStatisticsService: () => new AnnualAccountStatisticsService(),
    representativesService: () => new RepresentativesService(),
    capitalService: () => new CapitalService(),
    shareHoldersService: () => new ShareHoldersService(),
    updatehistoryService: () => new UpdatehistoryService(),
    regionListService: () => new RegionListService(),
    regionZipListService: () => new RegionZipListService(),
    employeeRoleService: () => new EmployeeRoleService(),
    employeePricingService: () => new EmployeePricingService(),
    competitorIndustryService: () => new CompetitorIndustryService(),
    dashboardFinanceService: () => new DashboardFinanceService(),
    techInfoService: () => new TechInfoService(),
    workforceService: () => new WorkforceService(),
    employeeTypeService: () => new EmployeeTypeService(),
    employeesTypologyService: () => new EmployeesTypologyService(),
    employeeSalariesService: () => new EmployeeSalariesService(),
    externalUrlsService: () => new ExternalUrlsService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
    timeTool: () => new TimeTool()
  },
  i18n,
  store
});
