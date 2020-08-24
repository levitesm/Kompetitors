import Vuex from 'vuex';
import VueI18n from 'vue-i18n';
import JhiFormatter from './formatter';
import { setupAxiosInterceptors } from '@/shared/config/axios-interceptor';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faSort } from '@fortawesome/free-solid-svg-icons/faSort';
import { faEye } from '@fortawesome/free-solid-svg-icons/faEye';
import { faSync } from '@fortawesome/free-solid-svg-icons/faSync';
import { faBan } from '@fortawesome/free-solid-svg-icons/faBan';
import { faTrash } from '@fortawesome/free-solid-svg-icons/faTrash';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons/faArrowLeft';
import { faSave } from '@fortawesome/free-solid-svg-icons/faSave';
import { faPlus } from '@fortawesome/free-solid-svg-icons/faPlus';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons/faPencilAlt';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';
import { faHdd } from '@fortawesome/free-solid-svg-icons/faHdd';
import { faTachometerAlt } from '@fortawesome/free-solid-svg-icons/faTachometerAlt';
import { faHeart } from '@fortawesome/free-solid-svg-icons/faHeart';
import { faList } from '@fortawesome/free-solid-svg-icons/faList';
import { faTasks } from '@fortawesome/free-solid-svg-icons/faTasks';
import { faBook } from '@fortawesome/free-solid-svg-icons/faBook';
import { faLock } from '@fortawesome/free-solid-svg-icons/faLock';
import { faSignInAlt } from '@fortawesome/free-solid-svg-icons/faSignInAlt';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons/faSignOutAlt';
import { faThList } from '@fortawesome/free-solid-svg-icons/faThList';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons/faUserPlus';
import { faWrench } from '@fortawesome/free-solid-svg-icons/faWrench';
import { faAsterisk } from '@fortawesome/free-solid-svg-icons/faAsterisk';
import { faFlag } from '@fortawesome/free-solid-svg-icons/faFlag';
import { faBell } from '@fortawesome/free-solid-svg-icons/faBell';
import { faHome } from '@fortawesome/free-solid-svg-icons/faHome';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import { faSearch } from '@fortawesome/free-solid-svg-icons/faSearch';
import { faRoad } from '@fortawesome/free-solid-svg-icons/faRoad';
import { faCloud } from '@fortawesome/free-solid-svg-icons/faCloud';
import { faBars } from '@fortawesome/free-solid-svg-icons/faBars';
import { faTimes } from '@fortawesome/free-solid-svg-icons/faTimes';

import VueCookie from 'vue-cookie';
import Vuelidate from 'vuelidate';
import Vue2Filters from 'vue2-filters';

import * as filters from '@/shared/date/filters';
import { Competitors } from '@/shared/model/competitors.model';
import { Offices } from '@/shared/model/offices.model';
import { People } from '@/shared/model/people.model';
import { HrInfo } from '@/shared/model/hr-info.model';
import { ListCompetancies } from '@/shared/model/list-competancies.model';
import { ListTools } from '@/shared/model/list-tools.model';
import { ListServices } from '@/shared/model/list-services.model';
import { ListTechPartners } from '@/shared/model/list-tech-partners.model';
import { ListProjectTypes } from '@/shared/model/list-project-types.model';
import { ListIndustries } from '@/shared/model/list-industries.model';
import { faUndo } from '@fortawesome/free-solid-svg-icons';
import { TooltipPlugin } from 'bootstrap-vue';
import { DashboardUnit } from '@/shared/model/dashboard-unit.model';
import { Legal } from '@/shared/model/legal.model';
import { Workforce } from '@/shared/model/workforce.model';
import { Finance } from '@/shared/model/finance.model';
import { PrInfo } from '@/shared/model/pr-info.model';
import { ExternalUrls } from '@/shared/model/external-urls.model';
import { ListPractices } from '@/shared/model/list-practices.model';
import { CompetitiveRates } from '@/shared/model/competitive-rates.model';

export function initVueApp(vue) {
  vue.use(VueCookie);
  vue.use(Vuelidate);
  vue.use(Vue2Filters);
  vue.use(TooltipPlugin);
  setupAxiosInterceptors(() => console.log('Unauthorized!'));
  filters.initFilters();
}

export function initFortAwesome(vue) {
  library.add(
    faSort,
    faEye,
    faSync,
    faBan,
    faTrash,
    faArrowLeft,
    faSave,
    faPlus,
    faPencilAlt,
    faUser,
    faTachometerAlt,
    faHeart,
    faList,
    faTasks,
    faBook,
    faHdd,
    faLock,
    faSignInAlt,
    faSignOutAlt,
    faWrench,
    faThList,
    faUserPlus,
    faAsterisk,
    faFlag,
    faBell,
    faHome,
    faRoad,
    faCloud,
    faTimesCircle,
    faSearch,
    faBars,
    faTimes,
    faUndo,
    faTimes
  );
}

export function initI18N(vue) {
  vue.use(VueI18n);
  return new VueI18n({
    silentTranslationWarn: true,
    formatter: new JhiFormatter()
  });
}

export function initVueXStore(vue) {
  vue.use(Vuex);
  return new Vuex.Store({
    state: {
      dismissSecs: 0,
      dismissCountDown: 0,
      alertType: '',
      alertMessage: {},
      logon: false,
      userIdentity: null,
      authenticated: false,
      ribbonOnProfiles: '',
      activeProfiles: '',
      currentLanguage: localStorage.getItem('currentLanguage') || 'en',
      languages: {
        en: { name: 'English' },
        fr: { name: 'Français' },
        ru: { name: 'Русский' }
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
      },
      accessKeys: [],
      annualAccounts: [],
      referenceCompetitor: undefined,
      competitor: new Competitors(),
      workforces: {},
      createGroup: true,
      competitorIsListed: false,
      competitorIsIndependent: false,
      competitorHasPrivateCapital: false,
      regionZipList: [],
      regionList: [],
      competitorsInGroup: [],
      dictionary: {
        techCompetancies: [] as ListCompetancies[],
        techPractices: [] as ListPractices[],
        techTools: [] as ListTools[],
        techServices: [] as ListServices[],
        techPartners: [] as ListTechPartners[],
        techProjects: [] as ListProjectTypes[],
        industries: {} as Map<number, ListIndustries>
      },
      statistics: {
        finance: [] as DashboardUnit[],
        hr: [] as DashboardUnit[]
      },
      externalUrls: new ExternalUrls()
    },
    mutations: {
      setWorkforces(state, workforces: Workforce[]) {
        state.workforces = [];
        workforces.forEach(workforce => {
          const year = new Date(workforce.year).getFullYear();
          state.workforces = {
            ...state.workforces,
            [year]: {
              ...workforce,
              year,
              competitor: workforce.competitor.id
            }
          };
        });
      },
      setCompetitorsInGroup(state, list) {
        state.competitorsInGroup = list;
      },
      setRegionZipList(state, list) {
        state.regionZipList = list;
      },
      setRegionList(state, list) {
        state.regionList = list;
      },
      setCompetitorIsListed(state, is) {
        state.competitorIsListed = is;
      },
      setCompetitorIsIndependent(state, is) {
        state.competitorIsIndependent = is;
      },
      setCompetitorHasPrivateCapital(state, has) {
        state.competitorHasPrivateCapital = has;
      },
      initAlert(state) {
        state.dismissSecs = 0;
        state.dismissCountDown = 0;
        state.alertType = '';
        state.alertMessage = {};
      },
      setAlertType(state, alertType) {
        state.alertType = alertType;
      },
      setAlertMessage(state, alertMessage) {
        state.dismissSecs = 5;
        state.dismissCountDown = 5;
        state.alertMessage = alertMessage;
      },
      countDownChanged(state, newCountDown) {
        state.dismissCountDown = newCountDown;
      },
      currentLanguage(state, newLanguage) {
        state.currentLanguage = newLanguage;
        localStorage.setItem('currentLanguage', newLanguage);
      },
      authenticate(state) {
        state.logon = true;
      },
      authenticated(state, identity) {
        state.userIdentity = identity;
        state.authenticated = true;
        state.logon = false;
      },
      logout(state) {
        state.userIdentity = null;
        state.authenticated = false;
        state.logon = false;
      },
      setActiveProfiles(state, profile) {
        state.activeProfiles = profile;
      },
      setRibbonOnProfiles(state, ribbon) {
        state.ribbonOnProfiles = ribbon;
      },
      setAccessKeys(state, accessKeys) {
        state.accessKeys = accessKeys;
      },
      setAnnualAccounts(state, annualAccounts) {
        state.annualAccounts = annualAccounts;
      },
      setReferenceCompetitor(state, competitor) {
        state.referenceCompetitor = competitor;
      },
      setCompetitor(state, competitor) {
        state.competitor = competitor;
      },
      addCompetitorOffice(state, office: Offices) {
        state.competitorsInGroup = state.competitorsInGroup.map(competitor => {
          if (state.competitor.id === competitor.id) {
            return { ...competitor, offices: [...competitor.offices, office] };
          } else {
            return competitor;
          }
        });
        state.competitor.offices.push(office);
      },
      updateCompetitorOffice(state, office: Offices) {
        state.competitorsInGroup = state.competitorsInGroup.map(competitor => {
          if (competitor.id === state.competitor.id) {
            const ind = competitor.offices.findIndex(o => o.id === office.id);
            competitor.offices.splice(ind, 1, office);
          }
          return competitor;
        });
        const index = state.competitor.offices.findIndex(o => o.id === office.id);
        state.competitor.offices.splice(index, 1, office);
      },
      deleteCompetitorOffice(state, office: Offices) {
        state.competitorsInGroup = state.competitorsInGroup.map(competitor => {
          return { ...competitor, offices: competitor.offices.filter(o => o.id !== office.id) };
        });
        state.competitor = { ...state.competitor, offices: state.competitor.offices.filter(o => o.id !== office.id) };
      },
      setCompetitorLegal(state, legal: Legal) {
        if (!state.competitor.legal) {
          state.competitor.legal = [];
        }
        const existing: Legal = state.competitor.legal.find(p => p.id === legal.id);
        if (existing) {
          const index = state.competitor.legal.indexOf(existing);
          state.competitor.legal.splice(index, 1, legal);
        } else {
          state.competitor.people.push(legal);
        }
      },
      updateTechInfo(state, { collectionName, values }) {
        state.competitor[collectionName] = values;
      },
      addCompetitorPeople(state, people: People) {
        if (!state.competitor.people) {
          state.competitor.people = [];
        }
        const existing: People = state.competitor.people.find(p => p.id === people.id);
        if (existing) {
          const index = state.competitor.people.indexOf(existing);
          state.competitor.people.splice(index, 1, people);
        } else {
          state.competitor.people.push(people);
        }
      },
      setCompetitorHrInfo(state, info: HrInfo) {
        state.competitor['hr'].splice(0, 1, info);
      },
      setCompetitorCompetitiveRates(state, rates: CompetitiveRates) {
        state.competitor['competitiveRates'].splice(0, 1, rates);
      },
      setCreateGroup(state, value: boolean) {
        state.createGroup = value;
      },
      setTechSection(state, { section, data }) {
        if (state.competitor.hasOwnProperty(section)) {
          state.competitor[section] = data;
        }
      },
      setDictionary(state, { section, data, add }) {
        if (state.dictionary.hasOwnProperty(section)) {
          let changes = {};
          if (add) {
            changes[data.id] = data;
          } else {
            changes = data.reduce((result, techCompetancy) => {
              result[techCompetancy.id] = { ...state.dictionary[section][techCompetancy.id], ...techCompetancy };
              return result;
            }, {});
          }
          state.dictionary[section] = { ...state.dictionary[section], ...changes };
        }
      },
      setStatistics(state, { section, data }) {
        if (state.statistics.hasOwnProperty(section)) {
          state.statistics[section] = data;
        }
      },
      setGlobalGroup(state, data) {
        state.competitor.globalGroups = data;
      },
      setLegal(state, data: Legal) {
        const index = state.competitor.legal.findIndex(el => el.id === data.id);
        if (index >= 0) {
          state.competitor.legal.splice(index, 1, data);
        } else {
          state.competitor.legal.push(data);
        }
      },
      setFinance(state, data: Finance) {
        const index = state.competitor.finances.findIndex(el => el.id === data.id);
        if (index >= 0) {
          state.competitor.finances.splice(index, 1, data);
          state.competitorsInGroup.map(competitor => {
            if (state.competitor.id === competitor.id) {
              const i = competitor.finances.findIndex(el => el.id === data.id);
              competitor.finances.splice(i, 1, data);
            }
            return competitor;
          });
        } else {
          state.competitor.finances.push(data);
          state.competitorsInGroup.map(competitor => {
            if (state.competitor.id === competitor.id) {
              competitor.finances.push(data);
            }
            return competitor;
          });
        }
      },
      setPrInfo(state, data: PrInfo) {
        if (!state.competitor.prinfo) {
          state.competitor.prinfo = [];
        }
        const index = state.competitor.prinfo.findIndex(el => el.id === data.id);
        if (index >= 0) {
          state.competitor.prinfo.splice(index, 1, data);
        } else {
          state.competitor.prinfo.push(data);
        }
      },
      setDialogs(state, data) {
        state.competitor.dialogs = data;
      },
      setExternalUrls(state, data) {
        state.externalUrls = data;
      }
    },
    getters: {
      workforces: state => state.workforces,
      competitorsInGroup: state => state.competitorsInGroup,
      regionList: state => state.regionList,
      regionZipList: state => state.regionZipList,
      dismissSecs: state => state.dismissSecs,
      dismissCountDown: state => state.dismissCountDown,
      alertType: state => state.alertType,
      alertMessage: state => state.alertMessage,
      currentLanguage: state => state.currentLanguage,
      languages: state => state.languages,
      logon: state => state.logon,
      account: state => state.userIdentity,
      authenticated: state => state.authenticated,
      activeProfiles: state => state.activeProfiles,
      ribbonOnProfiles: state => state.ribbonOnProfiles,
      accessKeys: state => state.accessKeys,
      annualAccounts: state => state.annualAccounts,
      referenceCompetitor: state => state.referenceCompetitor,
      competitor: state => state.competitor,
      createGroup: state => state.createGroup,
      competitorIsListed: state => state.competitorIsListed,
      competitorIsIndependent: state => state.competitorIsIndependent,
      competitorHasPrivateCapital: state => state.competitorHasPrivateCapital,
      dictionaries: state => state.dictionary,
      statistics: state => state.statistics,
      externalUrls: state => state.externalUrls
    }
  });
}
