import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListActivities } from '@/shared/model/list-activities.model';
import AlertService from '@/shared/alert/alert.service';

import ListActivitiesService from './list-activities.service';

@Component
export default class ListActivities extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listActivitiesService') private listActivitiesService: () => ListActivitiesService;
  private removeId: number = null;
  public listActivities: IListActivities[] = [];

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
    this.retrieveAllListActivitiess();
  }

  public clear(): void {
    this.retrieveAllListActivitiess();
  }

  public retrieveAllListActivitiess(): void {
    this.isFetching = true;

    this.listActivitiesService()
      .retrieve()
      .then(
        res => {
          this.listActivities = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListActivities): void {
    this.removeId = instance.id;
  }

  public removeListActivities(): void {
    this.listActivitiesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listActivities.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListActivitiess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
