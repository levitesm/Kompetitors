import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import ListOwnershipsService from '../list-ownerships/list-ownerships.service';
import { IListOwnerships } from '@/shared/model/list-ownerships.model';

import ListActivitiesService from '../list-activities/list-activities.service';
import { IListActivities } from '@/shared/model/list-activities.model';

import ListPricingsService from '../list-pricings/list-pricings.service';
import { IListPricings } from '@/shared/model/list-pricings.model';

import AlertService from '@/shared/alert/alert.service';
import { ILegal, Legal } from '@/shared/model/legal.model';
import LegalService from './legal.service';

const validations: any = {
  legal: {
    legalAddress: {},
    siren: {},
    greffe: {},
    founded: {},
    updateDate: {},
    legalForm: {}
  }
};

@Component({
  validations
})
export default class LegalUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('legalService') private legalService: () => LegalService;
  public legal: ILegal = new Legal();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];

  @Inject('listOwnershipsService') private listOwnershipsService: () => ListOwnershipsService;

  public listOwnerships: IListOwnerships[] = [];

  @Inject('listActivitiesService') private listActivitiesService: () => ListActivitiesService;

  public listActivities: IListActivities[] = [];

  @Inject('listPricingsService') private listPricingsService: () => ListPricingsService;

  public listPricings: IListPricings[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.legalId) {
        vm.retrieveLegal(to.params.legalId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.legal.id) {
      this.legalService()
        .update(this.legal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.legal.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.legalService()
        .create(this.legal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.legal.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveLegal(legalId): void {
    this.legalService()
      .find(legalId)
      .then(res => {
        this.legal = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
    this.listOwnershipsService()
      .retrieve()
      .then(res => {
        this.listOwnerships = res.data;
      });
    this.listActivitiesService()
      .retrieve()
      .then(res => {
        this.listActivities = res.data;
      });
    this.listPricingsService()
      .retrieve()
      .then(res => {
        this.listPricings = res.data;
      });
  }
}
