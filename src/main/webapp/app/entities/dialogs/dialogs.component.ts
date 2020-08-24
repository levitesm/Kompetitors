import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDialogs } from '@/shared/model/dialogs.model';
import AlertService from '@/shared/alert/alert.service';

import DialogsService from './dialogs.service';

@Component
export default class Dialogs extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dialogsService') private dialogsService: () => DialogsService;
  private removeId: number = null;
  public dialogs: IDialogs[] = [];

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
    this.retrieveAllDialogss();
  }

  public clear(): void {
    this.retrieveAllDialogss();
  }

  public retrieveAllDialogss(): void {
    this.isFetching = true;

    this.dialogsService()
      .retrieve()
      .then(
        res => {
          this.dialogs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDialogs): void {
    this.removeId = instance.id;
  }

  public removeDialogs(): void {
    this.dialogsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.dialogs.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDialogss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
