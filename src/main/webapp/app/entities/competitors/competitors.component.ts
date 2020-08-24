import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICompetitors } from '@/shared/model/competitors.model';
import AlertService from '@/shared/alert/alert.service';

import CompetitorsService from './competitors.service';

@Component
export default class Competitors extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitorsService') private competitorsService: () => CompetitorsService;
  private removeId: number = null;
  public competitors: ICompetitors[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllCompetitorss();
  }

  public clear(): void {
    this.retrieveAllCompetitorss();
  }

  public retrieveAllCompetitorss(): void {
    this.isFetching = true;

    this.competitorsService()
      .retrieve()
      .then(
        res => {
          this.competitors = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICompetitors): void {
    this.removeId = instance.id;
  }

  public removeCompetitors(): void {
    this.competitorsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.competitors.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllCompetitorss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
