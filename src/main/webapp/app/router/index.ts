import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');

const TermsComponent = () => import('../core/terms/terms.vue');
const PrivacyComponent = () => import('../core/privacy/privacy.vue');
const SecurityComponent = () => import('../core/security/security.vue');
const HelpComponent = () => import('../core/help/help.vue');

const Results = () => import('../core/results/results.vue');
const Competitor = () => import('../core/competitor/competitor.vue');
const Dashboard = () => import('../core/dashboard/dashboard.vue');

const Error = () => import('../core/error/error.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const Clients = () => import('../entities/clients/clients.vue');
// prettier-ignore
const ClientsUpdate = () => import('../entities/clients/clients-update.vue');
// prettier-ignore
const ClientsDetails = () => import('../entities/clients/clients-details.vue');
// prettier-ignore
const ClientsProjects = () => import('../entities/clients-projects/clients-projects.vue');
// prettier-ignore
const ClientsProjectsUpdate = () => import('../entities/clients-projects/clients-projects-update.vue');
// prettier-ignore
const ClientsProjectsDetails = () => import('../entities/clients-projects/clients-projects-details.vue');
// prettier-ignore
const CompetitiveRates = () => import('../entities/competitive-rates/competitive-rates.vue');
// prettier-ignore
const CompetitiveRatesUpdate = () => import('../entities/competitive-rates/competitive-rates-update.vue');
// prettier-ignore
const CompetitiveRatesDetails = () => import('../entities/competitive-rates/competitive-rates-details.vue');
// prettier-ignore
const Competitors = () => import('../entities/competitors/competitors.vue');
// prettier-ignore
const CompetitorsUpdate = () => import('../entities/competitors/competitors-update.vue');
// prettier-ignore
const CompetitorsDetails = () => import('../entities/competitors/competitors-details.vue');
// prettier-ignore
const Dialogs = () => import('../entities/dialogs/dialogs.vue');
// prettier-ignore
const DialogsUpdate = () => import('../entities/dialogs/dialogs-update.vue');
// prettier-ignore
const DialogsDetails = () => import('../entities/dialogs/dialogs-details.vue');
// prettier-ignore
const Finance = () => import('../entities/finance/finance.vue');
// prettier-ignore
const FinanceUpdate = () => import('../entities/finance/finance-update.vue');
// prettier-ignore
const FinanceDetails = () => import('../entities/finance/finance-details.vue');
// prettier-ignore
const GlobalGroups = () => import('../entities/global-groups/global-groups.vue');
// prettier-ignore
const GlobalGroupsUpdate = () => import('../entities/global-groups/global-groups-update.vue');
// prettier-ignore
const GlobalGroupsDetails = () => import('../entities/global-groups/global-groups-details.vue');
// prettier-ignore
const HrInfo = () => import('../entities/hr-info/hr-info.vue');
// prettier-ignore
const HrInfoUpdate = () => import('../entities/hr-info/hr-info-update.vue');
// prettier-ignore
const HrInfoDetails = () => import('../entities/hr-info/hr-info-details.vue');
// prettier-ignore
const Legal = () => import('../entities/legal/legal.vue');
// prettier-ignore
const LegalUpdate = () => import('../entities/legal/legal-update.vue');
// prettier-ignore
const LegalDetails = () => import('../entities/legal/legal-details.vue');
// prettier-ignore
const ListActivities = () => import('../entities/list-activities/list-activities.vue');
// prettier-ignore
const ListActivitiesUpdate = () => import('../entities/list-activities/list-activities-update.vue');
// prettier-ignore
const ListActivitiesDetails = () => import('../entities/list-activities/list-activities-details.vue');
// prettier-ignore
const ListCities = () => import('../entities/list-cities/list-cities.vue');
// prettier-ignore
const ListCitiesUpdate = () => import('../entities/list-cities/list-cities-update.vue');
// prettier-ignore
const ListCitiesDetails = () => import('../entities/list-cities/list-cities-details.vue');
// prettier-ignore
const ListCityCountries = () => import('../entities/list-city-countries/list-city-countries.vue');
// prettier-ignore
const ListCityCountriesUpdate = () => import('../entities/list-city-countries/list-city-countries-update.vue');
// prettier-ignore
const ListCityCountriesDetails = () => import('../entities/list-city-countries/list-city-countries-details.vue');
// prettier-ignore
const ListClientsProjectTypes = () => import('../entities/list-clients-project-types/list-clients-project-types.vue');
// prettier-ignore
const ListClientsProjectTypesUpdate = () => import('../entities/list-clients-project-types/list-clients-project-types-update.vue');
// prettier-ignore
const ListClientsProjectTypesDetails = () => import('../entities/list-clients-project-types/list-clients-project-types-details.vue');
// prettier-ignore
const ListCompetancies = () => import('../entities/list-competancies/list-competancies.vue');
// prettier-ignore
const ListCompetanciesUpdate = () => import('../entities/list-competancies/list-competancies-update.vue');
// prettier-ignore
const ListCompetanciesDetails = () => import('../entities/list-competancies/list-competancies-details.vue');
// prettier-ignore
const ListCountries = () => import('../entities/list-countries/list-countries.vue');
// prettier-ignore
const ListCountriesUpdate = () => import('../entities/list-countries/list-countries-update.vue');
// prettier-ignore
const ListCountriesDetails = () => import('../entities/list-countries/list-countries-details.vue');
// prettier-ignore
const ListIndustries = () => import('../entities/list-industries/list-industries.vue');
// prettier-ignore
const ListIndustriesUpdate = () => import('../entities/list-industries/list-industries-update.vue');
// prettier-ignore
const ListIndustriesDetails = () => import('../entities/list-industries/list-industries-details.vue');
// prettier-ignore
const ListOwnerships = () => import('../entities/list-ownerships/list-ownerships.vue');
// prettier-ignore
const ListOwnershipsUpdate = () => import('../entities/list-ownerships/list-ownerships-update.vue');
// prettier-ignore
const ListOwnershipsDetails = () => import('../entities/list-ownerships/list-ownerships-details.vue');
// prettier-ignore
const ListPricings = () => import('../entities/list-pricings/list-pricings.vue');
// prettier-ignore
const ListPricingsUpdate = () => import('../entities/list-pricings/list-pricings-update.vue');
// prettier-ignore
const ListPricingsDetails = () => import('../entities/list-pricings/list-pricings-details.vue');
// prettier-ignore
const ListProjectTypes = () => import('../entities/list-project-types/list-project-types.vue');
// prettier-ignore
const ListProjectTypesUpdate = () => import('../entities/list-project-types/list-project-types-update.vue');
// prettier-ignore
const ListProjectTypesDetails = () => import('../entities/list-project-types/list-project-types-details.vue');
// prettier-ignore
const ListServices = () => import('../entities/list-services/list-services.vue');
// prettier-ignore
const ListServicesUpdate = () => import('../entities/list-services/list-services-update.vue');
// prettier-ignore
const ListServicesDetails = () => import('../entities/list-services/list-services-details.vue');
// prettier-ignore
const ListTechPartners = () => import('../entities/list-tech-partners/list-tech-partners.vue');
// prettier-ignore
const ListTechPartnersUpdate = () => import('../entities/list-tech-partners/list-tech-partners-update.vue');
// prettier-ignore
const ListTechPartnersDetails = () => import('../entities/list-tech-partners/list-tech-partners-details.vue');
// prettier-ignore
const ListTools = () => import('../entities/list-tools/list-tools.vue');
// prettier-ignore
const ListToolsUpdate = () => import('../entities/list-tools/list-tools-update.vue');
// prettier-ignore
const ListToolsDetails = () => import('../entities/list-tools/list-tools-details.vue');
// prettier-ignore
const Offices = () => import('../entities/offices/offices.vue');
// prettier-ignore
const OfficesUpdate = () => import('../entities/offices/offices-update.vue');
// prettier-ignore
const OfficesDetails = () => import('../entities/offices/offices-details.vue');
// prettier-ignore
const People = () => import('../entities/people/people.vue');
// prettier-ignore
const PeopleUpdate = () => import('../entities/people/people-update.vue');
// prettier-ignore
const PeopleDetails = () => import('../entities/people/people-details.vue');
// prettier-ignore
const PrInfo = () => import('../entities/pr-info/pr-info.vue');
// prettier-ignore
const PrInfoUpdate = () => import('../entities/pr-info/pr-info-update.vue');
// prettier-ignore
const PrInfoDetails = () => import('../entities/pr-info/pr-info-details.vue');
// prettier-ignore
const SocieteMain = () => import('../entities/societe-main/societe-main.vue');
// prettier-ignore
const SocieteMainUpdate = () => import('../entities/societe-main/societe-main-update.vue');
// prettier-ignore
const SocieteMainDetails = () => import('../entities/societe-main/societe-main-details.vue');
// prettier-ignore
const TechCompetancies = () => import('../entities/tech-competancies/tech-competancies.vue');
// prettier-ignore
const TechCompetanciesUpdate = () => import('../entities/tech-competancies/tech-competancies-update.vue');
// prettier-ignore
const TechCompetanciesDetails = () => import('../entities/tech-competancies/tech-competancies-details.vue');
// prettier-ignore
const TechPartners = () => import('../entities/tech-partners/tech-partners.vue');
// prettier-ignore
const TechPartnersUpdate = () => import('../entities/tech-partners/tech-partners-update.vue');
// prettier-ignore
const TechPartnersDetails = () => import('../entities/tech-partners/tech-partners-details.vue');
// prettier-ignore
const TechProjects = () => import('../entities/tech-projects/tech-projects.vue');
// prettier-ignore
const TechProjectsUpdate = () => import('../entities/tech-projects/tech-projects-update.vue');
// prettier-ignore
const TechProjectsDetails = () => import('../entities/tech-projects/tech-projects-details.vue');
// prettier-ignore
const TechServices = () => import('../entities/tech-services/tech-services.vue');
// prettier-ignore
const TechServicesUpdate = () => import('../entities/tech-services/tech-services-update.vue');
// prettier-ignore
const TechServicesDetails = () => import('../entities/tech-services/tech-services-details.vue');
// prettier-ignore
const TechTools = () => import('../entities/tech-tools/tech-tools.vue');
// prettier-ignore
const TechToolsUpdate = () => import('../entities/tech-tools/tech-tools-update.vue');
// prettier-ignore
const TechToolsDetails = () => import('../entities/tech-tools/tech-tools-details.vue');
// prettier-ignore
const UserGroupRights = () => import('../entities/user-group-rights/user-group-rights.vue');
// prettier-ignore
const UserGroupRightsUpdate = () => import('../entities/user-group-rights/user-group-rights-update.vue');
// prettier-ignore
const UserGroupRightsDetails = () => import('../entities/user-group-rights/user-group-rights-details.vue');
// prettier-ignore
const AccessKey = () => import('../entities/access-key/access-key.vue');
// prettier-ignore
const AccessKeyUpdate = () => import('../entities/access-key/access-key-update.vue');
// prettier-ignore
const AccessKeyDetails = () => import('../entities/access-key/access-key-details.vue');
// prettier-ignore
const AnnualAccount = () => import('../entities/annual-account/annual-account.vue');
// prettier-ignore
const AnnualAccountUpdate = () => import('../entities/annual-account/annual-account-update.vue');
// prettier-ignore
const AnnualAccountDetails = () => import('../entities/annual-account/annual-account-details.vue');
// prettier-ignore
const AnnualAccountStatistics = () => import('../entities/annual-account-statistics/annual-account-statistics.vue');
// prettier-ignore
const AnnualAccountStatisticsUpdate = () => import('../entities/annual-account-statistics/annual-account-statistics-update.vue');
// prettier-ignore
const AnnualAccountStatisticsDetails = () => import('../entities/annual-account-statistics/annual-account-statistics-details.vue');
// prettier-ignore
const Representatives = () => import('../entities/representatives/representatives.vue');
// prettier-ignore
const RepresentativesUpdate = () => import('../entities/representatives/representatives-update.vue');
// prettier-ignore
const RepresentativesDetails = () => import('../entities/representatives/representatives-details.vue');
// prettier-ignore
const Capital = () => import('../entities/capital/capital.vue');
// prettier-ignore
const CapitalUpdate = () => import('../entities/capital/capital-update.vue');
// prettier-ignore
const CapitalDetails = () => import('../entities/capital/capital-details.vue');
// prettier-ignore
const ShareHolders = () => import('../entities/share-holders/share-holders.vue');
// prettier-ignore
const ShareHoldersUpdate = () => import('../entities/share-holders/share-holders-update.vue');
// prettier-ignore
const ShareHoldersDetails = () => import('../entities/share-holders/share-holders-details.vue');
// prettier-ignore
const Updatehistory = () => import('../entities/updatehistory/updatehistory.vue');
// prettier-ignore
const UpdatehistoryUpdate = () => import('../entities/updatehistory/updatehistory-update.vue');
// prettier-ignore
const UpdatehistoryDetails = () => import('../entities/updatehistory/updatehistory-details.vue');
// prettier-ignore
const RegionList = () => import('../entities/region-list/region-list.vue');
// prettier-ignore
const RegionListUpdate = () => import('../entities/region-list/region-list-update.vue');
// prettier-ignore
const RegionListDetails = () => import('../entities/region-list/region-list-details.vue');
// prettier-ignore
const RegionZipList = () => import('../entities/region-zip-list/region-zip-list.vue');
// prettier-ignore
const RegionZipListUpdate = () => import('../entities/region-zip-list/region-zip-list-update.vue');
// prettier-ignore
const RegionZipListDetails = () => import('../entities/region-zip-list/region-zip-list-details.vue');
// prettier-ignore
const EmployeeRole = () => import('../entities/employee-role/employee-role.vue');
// prettier-ignore
const EmployeeRoleUpdate = () => import('../entities/employee-role/employee-role-update.vue');
// prettier-ignore
const EmployeeRoleDetails = () => import('../entities/employee-role/employee-role-details.vue');
// prettier-ignore
const EmployeePricing = () => import('../entities/employee-pricing/employee-pricing.vue');
// prettier-ignore
const EmployeePricingUpdate = () => import('../entities/employee-pricing/employee-pricing-update.vue');
// prettier-ignore
const EmployeePricingDetails = () => import('../entities/employee-pricing/employee-pricing-details.vue');
// prettier-ignore
const CompetitorIndustry = () => import('../entities/competitor-industry/competitor-industry.vue');
// prettier-ignore
const CompetitorIndustryUpdate = () => import('../entities/competitor-industry/competitor-industry-update.vue');
// prettier-ignore
const CompetitorIndustryDetails = () => import('../entities/competitor-industry/competitor-industry-details.vue');
// prettier-ignore
const DashboardFinance = () => import('../entities/dashboard-finance/dashboard-finance.vue');
// prettier-ignore
const DashboardFinanceUpdate = () => import('../entities/dashboard-finance/dashboard-finance-update.vue');
// prettier-ignore
const DashboardFinanceDetails = () => import('../entities/dashboard-finance/dashboard-finance-details.vue');
// prettier-ignore
const TechInfo = () => import('../entities/tech-info/tech-info.vue');
// prettier-ignore
const TechInfoUpdate = () => import('../entities/tech-info/tech-info-update.vue');
// prettier-ignore
const TechInfoDetails = () => import('../entities/tech-info/tech-info-details.vue');
// prettier-ignore
const Workforce = () => import('../entities/workforce/workforce.vue');
// prettier-ignore
const WorkforceUpdate = () => import('../entities/workforce/workforce-update.vue');
// prettier-ignore
const WorkforceDetails = () => import('../entities/workforce/workforce-details.vue');
const EmployeeType = () => import('../entities/employee-type/employee-type.vue');
// prettier-ignore
const EmployeeTypeUpdate = () => import('../entities/employee-type/employee-type-update.vue');
// prettier-ignore
const EmployeeTypeDetails = () => import('../entities/employee-type/employee-type-details.vue');
// prettier-ignore
const EmployeesTypology = () => import('../entities/employees-typology/employees-typology.vue');
// prettier-ignore
const EmployeesTypologyUpdate = () => import('../entities/employees-typology/employees-typology-update.vue');
// prettier-ignore
const EmployeesTypologyDetails = () => import('../entities/employees-typology/employees-typology-details.vue');
// prettier-ignore
const ExternalUrls = () => import('../entities/external-urls/external-urls.vue');
// prettier-ignore
const ExternalUrlsUpdate = () => import('../entities/external-urls/external-urls-update.vue');
// prettier-ignore
const ExternalUrlsDetails = () => import('../entities/external-urls/external-urls-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/terms',
      name: 'TermsComponent',
      component: TermsComponent
    },
    {
      path: '/privacy',
      name: 'PrivacyComponent',
      component: PrivacyComponent
    },
    {
      path: '/security',
      name: 'SecurityComponent',
      component: SecurityComponent
    },
    {
      path: '/help',
      name: 'HelpComponent',
      component: HelpComponent
    },
    {
      path: '/results',
      name: 'Results',
      component: Results
    },
    {
      path: '/competitor/:id',
      name: 'Competitor',
      component: Competitor
    },
    {
      path: '/dashboard/:id',
      name: 'Dashboard',
      component: Dashboard
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,
    {
      path: '/entity/clients',
      name: 'Clients',
      component: Clients,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/clients/new',
      name: 'ClientsCreate',
      component: ClientsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/clients/:clientsId/edit',
      name: 'ClientsEdit',
      component: ClientsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/clients/:clientsId/view',
      name: 'ClientsView',
      component: ClientsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/clients-projects',
      name: 'ClientsProjects',
      component: ClientsProjects,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/clients-projects/new',
      name: 'ClientsProjectsCreate',
      component: ClientsProjectsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/clients-projects/:clientsProjectsId/edit',
      name: 'ClientsProjectsEdit',
      component: ClientsProjectsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/clients-projects/:clientsProjectsId/view',
      name: 'ClientsProjectsView',
      component: ClientsProjectsDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitive-rates',
      name: 'CompetitiveRates',
      component: CompetitiveRates,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitive-rates/new',
      name: 'CompetitiveRatesCreate',
      component: CompetitiveRatesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitive-rates/:competitiveRatesId/edit',
      name: 'CompetitiveRatesEdit',
      component: CompetitiveRatesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitive-rates/:competitiveRatesId/view',
      name: 'CompetitiveRatesView',
      component: CompetitiveRatesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competitors',
      name: 'Competitors',
      component: Competitors,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitors/new',
      name: 'CompetitorsCreate',
      component: CompetitorsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitors/:competitorsId/edit',
      name: 'CompetitorsEdit',
      component: CompetitorsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitors/:competitorsId/view',
      name: 'CompetitorsView',
      component: CompetitorsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/dialogs',
      name: 'Dialogs',
      component: Dialogs,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dialogs/new',
      name: 'DialogsCreate',
      component: DialogsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dialogs/:dialogsId/edit',
      name: 'DialogsEdit',
      component: DialogsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dialogs/:dialogsId/view',
      name: 'DialogsView',
      component: DialogsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/finance',
      name: 'Finance',
      component: Finance,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/finance/new',
      name: 'FinanceCreate',
      component: FinanceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/finance/:financeId/edit',
      name: 'FinanceEdit',
      component: FinanceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/finance/:financeId/view',
      name: 'FinanceView',
      component: FinanceDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/global-groups',
      name: 'GlobalGroups',
      component: GlobalGroups,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-groups/new',
      name: 'GlobalGroupsCreate',
      component: GlobalGroupsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-groups/:globalGroupsId/edit',
      name: 'GlobalGroupsEdit',
      component: GlobalGroupsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-groups/:globalGroupsId/view',
      name: 'GlobalGroupsView',
      component: GlobalGroupsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/hr-info',
      name: 'HrInfo',
      component: HrInfo,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/hr-info/new',
      name: 'HrInfoCreate',
      component: HrInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/hr-info/:hrInfoId/edit',
      name: 'HrInfoEdit',
      component: HrInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/hr-info/:hrInfoId/view',
      name: 'HrInfoView',
      component: HrInfoDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/legal',
      name: 'Legal',
      component: Legal,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/legal/new',
      name: 'LegalCreate',
      component: LegalUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/legal/:legalId/edit',
      name: 'LegalEdit',
      component: LegalUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/legal/:legalId/view',
      name: 'LegalView',
      component: LegalDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-activities',
      name: 'ListActivities',
      component: ListActivities,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-activities/new',
      name: 'ListActivitiesCreate',
      component: ListActivitiesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-activities/:listActivitiesId/edit',
      name: 'ListActivitiesEdit',
      component: ListActivitiesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-activities/:listActivitiesId/view',
      name: 'ListActivitiesView',
      component: ListActivitiesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-cities',
      name: 'ListCities',
      component: ListCities,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-cities/new',
      name: 'ListCitiesCreate',
      component: ListCitiesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-cities/:listCitiesId/edit',
      name: 'ListCitiesEdit',
      component: ListCitiesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-cities/:listCitiesId/view',
      name: 'ListCitiesView',
      component: ListCitiesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-city-countries',
      name: 'ListCityCountries',
      component: ListCityCountries,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-city-countries/new',
      name: 'ListCityCountriesCreate',
      component: ListCityCountriesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-city-countries/:listCityCountriesId/edit',
      name: 'ListCityCountriesEdit',
      component: ListCityCountriesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-city-countries/:listCityCountriesId/view',
      name: 'ListCityCountriesView',
      component: ListCityCountriesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-clients-project-types',
      name: 'ListClientsProjectTypes',
      component: ListClientsProjectTypes,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-clients-project-types/new',
      name: 'ListClientsProjectTypesCreate',
      component: ListClientsProjectTypesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-clients-project-types/:listClientsProjectTypesId/edit',
      name: 'ListClientsProjectTypesEdit',
      component: ListClientsProjectTypesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-clients-project-types/:listClientsProjectTypesId/view',
      name: 'ListClientsProjectTypesView',
      component: ListClientsProjectTypesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-competancies',
      name: 'ListCompetancies',
      component: ListCompetancies,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-competancies/new',
      name: 'ListCompetanciesCreate',
      component: ListCompetanciesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-competancies/:listCompetanciesId/edit',
      name: 'ListCompetanciesEdit',
      component: ListCompetanciesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-competancies/:listCompetanciesId/view',
      name: 'ListCompetanciesView',
      component: ListCompetanciesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-countries',
      name: 'ListCountries',
      component: ListCountries,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-countries/new',
      name: 'ListCountriesCreate',
      component: ListCountriesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-countries/:listCountriesId/edit',
      name: 'ListCountriesEdit',
      component: ListCountriesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-countries/:listCountriesId/view',
      name: 'ListCountriesView',
      component: ListCountriesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-industries',
      name: 'ListIndustries',
      component: ListIndustries,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-industries/new',
      name: 'ListIndustriesCreate',
      component: ListIndustriesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-industries/:listIndustriesId/edit',
      name: 'ListIndustriesEdit',
      component: ListIndustriesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-industries/:listIndustriesId/view',
      name: 'ListIndustriesView',
      component: ListIndustriesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-ownerships',
      name: 'ListOwnerships',
      component: ListOwnerships,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-ownerships/new',
      name: 'ListOwnershipsCreate',
      component: ListOwnershipsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-ownerships/:listOwnershipsId/edit',
      name: 'ListOwnershipsEdit',
      component: ListOwnershipsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-ownerships/:listOwnershipsId/view',
      name: 'ListOwnershipsView',
      component: ListOwnershipsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-pricings',
      name: 'ListPricings',
      component: ListPricings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-pricings/new',
      name: 'ListPricingsCreate',
      component: ListPricingsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-pricings/:listPricingsId/edit',
      name: 'ListPricingsEdit',
      component: ListPricingsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-pricings/:listPricingsId/view',
      name: 'ListPricingsView',
      component: ListPricingsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-project-types',
      name: 'ListProjectTypes',
      component: ListProjectTypes,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-project-types/new',
      name: 'ListProjectTypesCreate',
      component: ListProjectTypesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-project-types/:listProjectTypesId/edit',
      name: 'ListProjectTypesEdit',
      component: ListProjectTypesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-project-types/:listProjectTypesId/view',
      name: 'ListProjectTypesView',
      component: ListProjectTypesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-services',
      name: 'ListServices',
      component: ListServices,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-services/new',
      name: 'ListServicesCreate',
      component: ListServicesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-services/:listServicesId/edit',
      name: 'ListServicesEdit',
      component: ListServicesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-services/:listServicesId/view',
      name: 'ListServicesView',
      component: ListServicesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-tech-partners',
      name: 'ListTechPartners',
      component: ListTechPartners,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-tech-partners/new',
      name: 'ListTechPartnersCreate',
      component: ListTechPartnersUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-tech-partners/:listTechPartnersId/edit',
      name: 'ListTechPartnersEdit',
      component: ListTechPartnersUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-tech-partners/:listTechPartnersId/view',
      name: 'ListTechPartnersView',
      component: ListTechPartnersDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/list-tools',
      name: 'ListTools',
      component: ListTools,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-tools/new',
      name: 'ListToolsCreate',
      component: ListToolsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-tools/:listToolsId/edit',
      name: 'ListToolsEdit',
      component: ListToolsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/list-tools/:listToolsId/view',
      name: 'ListToolsView',
      component: ListToolsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/offices',
      name: 'Offices',
      component: Offices,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/offices/new',
      name: 'OfficesCreate',
      component: OfficesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/offices/:officesId/edit',
      name: 'OfficesEdit',
      component: OfficesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/offices/:officesId/view',
      name: 'OfficesView',
      component: OfficesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/people',
      name: 'People',
      component: People,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/people/new',
      name: 'PeopleCreate',
      component: PeopleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/people/:peopleId/edit',
      name: 'PeopleEdit',
      component: PeopleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/people/:peopleId/view',
      name: 'PeopleView',
      component: PeopleDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/pr-info',
      name: 'PrInfo',
      component: PrInfo,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/pr-info/new',
      name: 'PrInfoCreate',
      component: PrInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/pr-info/:prInfoId/edit',
      name: 'PrInfoEdit',
      component: PrInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/pr-info/:prInfoId/view',
      name: 'PrInfoView',
      component: PrInfoDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/societe-main',
      name: 'SocieteMain',
      component: SocieteMain,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/societe-main/new',
      name: 'SocieteMainCreate',
      component: SocieteMainUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/societe-main/:societeMainId/edit',
      name: 'SocieteMainEdit',
      component: SocieteMainUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/societe-main/:societeMainId/view',
      name: 'SocieteMainView',
      component: SocieteMainDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/tech-competancies',
      name: 'TechCompetancies',
      component: TechCompetancies,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-competancies/new',
      name: 'TechCompetanciesCreate',
      component: TechCompetanciesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-competancies/:techCompetanciesId/edit',
      name: 'TechCompetanciesEdit',
      component: TechCompetanciesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-competancies/:techCompetanciesId/view',
      name: 'TechCompetanciesView',
      component: TechCompetanciesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/tech-partners',
      name: 'TechPartners',
      component: TechPartners,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-partners/new',
      name: 'TechPartnersCreate',
      component: TechPartnersUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-partners/:techPartnersId/edit',
      name: 'TechPartnersEdit',
      component: TechPartnersUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-partners/:techPartnersId/view',
      name: 'TechPartnersView',
      component: TechPartnersDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/tech-projects',
      name: 'TechProjects',
      component: TechProjects,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-projects/new',
      name: 'TechProjectsCreate',
      component: TechProjectsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-projects/:techProjectsId/edit',
      name: 'TechProjectsEdit',
      component: TechProjectsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-projects/:techProjectsId/view',
      name: 'TechProjectsView',
      component: TechProjectsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/tech-services',
      name: 'TechServices',
      component: TechServices,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-services/new',
      name: 'TechServicesCreate',
      component: TechServicesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-services/:techServicesId/edit',
      name: 'TechServicesEdit',
      component: TechServicesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-services/:techServicesId/view',
      name: 'TechServicesView',
      component: TechServicesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/tech-tools',
      name: 'TechTools',
      component: TechTools,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-tools/new',
      name: 'TechToolsCreate',
      component: TechToolsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-tools/:techToolsId/edit',
      name: 'TechToolsEdit',
      component: TechToolsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-tools/:techToolsId/view',
      name: 'TechToolsView',
      component: TechToolsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/user-group-rights',
      name: 'UserGroupRights',
      component: UserGroupRights,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/user-group-rights/new',
      name: 'UserGroupRightsCreate',
      component: UserGroupRightsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/user-group-rights/:userGroupRightsId/edit',
      name: 'UserGroupRightsEdit',
      component: UserGroupRightsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/user-group-rights/:userGroupRightsId/view',
      name: 'UserGroupRightsView',
      component: UserGroupRightsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/access-key',
      name: 'AccessKey',
      component: AccessKey,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/access-key/new',
      name: 'AccessKeyCreate',
      component: AccessKeyUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/access-key/:accessKeyId/edit',
      name: 'AccessKeyEdit',
      component: AccessKeyUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/access-key/:accessKeyId/view',
      name: 'AccessKeyView',
      component: AccessKeyDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account',
      name: 'AnnualAccount',
      component: AnnualAccount,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account/new',
      name: 'AnnualAccountCreate',
      component: AnnualAccountUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account/:annualAccountId/edit',
      name: 'AnnualAccountEdit',
      component: AnnualAccountUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account/:annualAccountId/view',
      name: 'AnnualAccountView',
      component: AnnualAccountDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/annual-account-statistics',
      name: 'AnnualAccountStatistics',
      component: AnnualAccountStatistics,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account-statistics/new',
      name: 'AnnualAccountStatisticsCreate',
      component: AnnualAccountStatisticsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account-statistics/:annualAccountStatisticsId/edit',
      name: 'AnnualAccountStatisticsEdit',
      component: AnnualAccountStatisticsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/annual-account-statistics/:annualAccountStatisticsId/view',
      name: 'AnnualAccountStatisticsView',
      component: AnnualAccountStatisticsDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/representatives',
      name: 'Representatives',
      component: Representatives,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/representatives/new',
      name: 'RepresentativesCreate',
      component: RepresentativesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/representatives/:representativesId/edit',
      name: 'RepresentativesEdit',
      component: RepresentativesUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/representatives/:representativesId/view',
      name: 'RepresentativesView',
      component: RepresentativesDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/capital',
      name: 'Capital',
      component: Capital,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/capital/new',
      name: 'CapitalCreate',
      component: CapitalUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/capital/:capitalId/edit',
      name: 'CapitalEdit',
      component: CapitalUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/capital/:capitalId/view',
      name: 'CapitalView',
      component: CapitalDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/share-holders',
      name: 'ShareHolders',
      component: ShareHolders,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/share-holders/new',
      name: 'ShareHoldersCreate',
      component: ShareHoldersUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/share-holders/:shareHoldersId/edit',
      name: 'ShareHoldersEdit',
      component: ShareHoldersUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/share-holders/:shareHoldersId/view',
      name: 'ShareHoldersView',
      component: ShareHoldersDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/updatehistory',
      name: 'Updatehistory',
      component: Updatehistory,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/updatehistory/new',
      name: 'UpdatehistoryCreate',
      component: UpdatehistoryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/updatehistory/:updatehistoryId/edit',
      name: 'UpdatehistoryEdit',
      component: UpdatehistoryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/updatehistory/:updatehistoryId/view',
      name: 'UpdatehistoryView',
      component: UpdatehistoryDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/region-list',
      name: 'RegionList',
      component: RegionList,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/region-list/new',
      name: 'RegionListCreate',
      component: RegionListUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/region-list/:regionListId/edit',
      name: 'RegionListEdit',
      component: RegionListUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/region-list/:regionListId/view',
      name: 'RegionListView',
      component: RegionListDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/region-zip-list',
      name: 'RegionZipList',
      component: RegionZipList,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/region-zip-list/new',
      name: 'RegionZipListCreate',
      component: RegionZipListUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/region-zip-list/:regionZipListId/edit',
      name: 'RegionZipListEdit',
      component: RegionZipListUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/region-zip-list/:regionZipListId/view',
      name: 'RegionZipListView',
      component: RegionZipListDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/employee-role',
      name: 'EmployeeRole',
      component: EmployeeRole,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-role/new',
      name: 'EmployeeRoleCreate',
      component: EmployeeRoleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-role/:employeeRoleId/edit',
      name: 'EmployeeRoleEdit',
      component: EmployeeRoleUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-role/:employeeRoleId/view',
      name: 'EmployeeRoleView',
      component: EmployeeRoleDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-pricing',
      name: 'EmployeePricing',
      component: EmployeePricing,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-pricing/new',
      name: 'EmployeePricingCreate',
      component: EmployeePricingUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-pricing/:employeePricingId/edit',
      name: 'EmployeePricingEdit',
      component: EmployeePricingUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-pricing/:employeePricingId/view',
      name: 'EmployeePricingView',
      component: EmployeePricingDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitor-industry',
      name: 'CompetitorIndustry',
      component: CompetitorIndustry,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitor-industry/new',
      name: 'CompetitorIndustryCreate',
      component: CompetitorIndustryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitor-industry/:competitorIndustryId/edit',
      name: 'CompetitorIndustryEdit',
      component: CompetitorIndustryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competitor-industry/:competitorIndustryId/view',
      name: 'CompetitorIndustryView',
      component: CompetitorIndustryDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dashboard-finance',
      name: 'DashboardFinance',
      component: DashboardFinance,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dashboard-finance/new',
      name: 'DashboardFinanceCreate',
      component: DashboardFinanceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dashboard-finance/:dashboardFinanceId/edit',
      name: 'DashboardFinanceEdit',
      component: DashboardFinanceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/dashboard-finance/:dashboardFinanceId/view',
      name: 'DashboardFinanceView',
      component: DashboardFinanceDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-info',
      name: 'TechInfo',
      component: TechInfo,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-info/new',
      name: 'TechInfoCreate',
      component: TechInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-info/:techInfoId/edit',
      name: 'TechInfoEdit',
      component: TechInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/tech-info/:techInfoId/view',
      name: 'TechInfoView',
      component: TechInfoDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/workforce',
      name: 'Workforce',
      component: Workforce,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/workforce/new',
      name: 'WorkforceCreate',
      component: WorkforceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/workforce/:workforceId/edit',
      name: 'WorkforceEdit',
      component: WorkforceUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/workforce/:workforceId/view',
      name: 'WorkforceView',
      component: WorkforceDetails,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-type',
      name: 'EmployeeType',
      component: EmployeeType,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-type/new',
      name: 'EmployeeTypeCreate',
      component: EmployeeTypeUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-type/:employeeTypeId/edit',
      name: 'EmployeeTypeEdit',
      component: EmployeeTypeUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employee-type/:employeeTypeId/view',
      name: 'EmployeeTypeView',
      component: EmployeeTypeDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/employees-typology',
      name: 'EmployeesTypology',
      component: EmployeesTypology,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employees-typology/new',
      name: 'EmployeesTypologyCreate',
      component: EmployeesTypologyUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employees-typology/:employeesTypologyId/edit',
      name: 'EmployeesTypologyEdit',
      component: EmployeesTypologyUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/employees-typology/:employeesTypologyId/view',
      name: 'EmployeesTypologyView',
      component: EmployeesTypologyDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/external-urls',
      name: 'ExternalUrls',
      component: ExternalUrls,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/external-urls/new',
      name: 'ExternalUrlsCreate',
      component: ExternalUrlsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/external-urls/:externalUrlsId/edit',
      name: 'ExternalUrlsEdit',
      component: ExternalUrlsUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/external-urls/:externalUrlsId/view',
      name: 'ExternalUrlsView',
      component: ExternalUrlsDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
