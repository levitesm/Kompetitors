import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUpdatehistory } from '@/shared/model/updatehistory.model';
import AlertService from '@/shared/alert/alert.service';

import UpdatehistoryService from './updatehistory.service';

@Component
export default class Updatehistory extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('updatehistoryService') private updatehistoryService: () => UpdatehistoryService;
  private removeId: number = null;
  public updatehistories: IUpdatehistory[] = [];

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
    this.retrieveAllUpdatehistorys();
  }

  public clear(): void {
    this.retrieveAllUpdatehistorys();
  }

  public retrieveAllUpdatehistorys(): void {
    this.isFetching = true;

    this.updatehistoryService()
      .retrieve()
      .then(
        res => {
          this.updatehistories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IUpdatehistory): void {
    this.removeId = instance.id;
  }

  public removeUpdatehistory(): void {
    this.updatehistoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.updatehistory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllUpdatehistorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
