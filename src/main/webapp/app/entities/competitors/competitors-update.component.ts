import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import DialogsService from '../dialogs/dialogs.service';
import { IDialogs } from '@/shared/model/dialogs.model';

import FinanceService from '../finance/finance.service';
import { IFinance } from '@/shared/model/finance.model';

import OfficesService from '../offices/offices.service';
import { IOffices } from '@/shared/model/offices.model';

import PeopleService from '../people/people.service';
import { IPeople } from '@/shared/model/people.model';

import PrInfoService from '../pr-info/pr-info.service';
import { IPrInfo } from '@/shared/model/pr-info.model';

import ListCountriesService from '../list-countries/list-countries.service';
import { IListCountries } from '@/shared/model/list-countries.model';

import SocieteMainService from '../societe-main/societe-main.service';
import { ISocieteMain } from '@/shared/model/societe-main.model';

import GlobalGroupsService from '../global-groups/global-groups.service';
import { IGlobalGroups } from '@/shared/model/global-groups.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitors, Competitors } from '@/shared/model/competitors.model';
import CompetitorsService from './competitors.service';

const validations: any = {
  competitors: {
    name: {},
    webSite: {},
    countryPhone: {}
  }
};

@Component({
  validations
})
export default class CompetitorsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitorsService') private competitorsService: () => CompetitorsService;
  public competitors: ICompetitors = new Competitors();

  @Inject('dialogsService') private dialogsService: () => DialogsService;

  public dialogs: IDialogs[] = [];

  @Inject('financeService') private financeService: () => FinanceService;

  public finances: IFinance[] = [];

  @Inject('officesService') private officesService: () => OfficesService;

  public offices: IOffices[] = [];

  @Inject('peopleService') private peopleService: () => PeopleService;

  public people: IPeople[] = [];

  @Inject('prInfoService') private prInfoService: () => PrInfoService;

  public prInfos: IPrInfo[] = [];

  @Inject('listCountriesService') private listCountriesService: () => ListCountriesService;

  public listCountries: IListCountries[] = [];

  @Inject('societeMainService') private societeMainService: () => SocieteMainService;

  public societeMains: ISocieteMain[] = [];

  @Inject('globalGroupsService') private globalGroupsService: () => GlobalGroupsService;

  public globalGroups: IGlobalGroups[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitorsId) {
        vm.retrieveCompetitors(to.params.competitorsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitors.id) {
      this.competitorsService()
        .update(this.competitors)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.competitors.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitorsService()
        .create(this.competitors)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.competitors.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitors(competitorsId): void {
    this.competitorsService()
      .find(competitorsId)
      .then(res => {
        this.competitors = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.dialogsService()
      .retrieve()
      .then(res => {
        this.dialogs = res.data;
      });
    this.financeService()
      .retrieve()
      .then(res => {
        this.finances = res.data;
      });
    this.officesService()
      .retrieve()
      .then(res => {
        this.offices = res.data;
      });
    this.peopleService()
      .retrieve()
      .then(res => {
        this.people = res.data;
      });
    this.prInfoService()
      .retrieve()
      .then(res => {
        this.prInfos = res.data;
      });
    this.listCountriesService()
      .retrieve()
      .then(res => {
        this.listCountries = res.data;
      });
    this.societeMainService()
      .retrieve()
      .then(res => {
        this.societeMains = res.data;
      });
    this.globalGroupsService()
      .retrieve()
      .then(res => {
        this.globalGroups = res.data;
      });
  }
}
