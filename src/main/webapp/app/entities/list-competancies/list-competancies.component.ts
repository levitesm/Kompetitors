import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListCompetancies } from '@/shared/model/list-competancies.model';
import AlertService from '@/shared/alert/alert.service';

import ListCompetanciesService from './list-competancies.service';

@Component
export default class ListCompetancies extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCompetanciesService') private listCompetanciesService: () => ListCompetanciesService;
  private removeId: number = null;
  public listCompetancies: IListCompetancies[] = [];

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
    this.retrieveAllListCompetanciess();
  }

  public clear(): void {
    this.retrieveAllListCompetanciess();
  }

  public retrieveAllListCompetanciess(): void {
    this.isFetching = true;

    this.listCompetanciesService()
      .retrieve()
      .then(
        res => {
          this.listCompetancies = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListCompetancies): void {
    this.removeId = instance.id;
  }

  public removeListCompetancies(): void {
    this.listCompetanciesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listCompetancies.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListCompetanciess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
