import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IWorkforce } from '@/shared/model/workforce.model';
import AlertService from '@/shared/alert/alert.service';

import WorkforceService from './workforce.service';

@Component
export default class Workforce extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('workforceService') private workforceService: () => WorkforceService;
  private removeId: number = null;
  public workforces: IWorkforce[] = [];

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
    this.retrieveAllWorkforces();
  }

  public clear(): void {
    this.retrieveAllWorkforces();
  }

  public retrieveAllWorkforces(): void {
    this.isFetching = true;

    this.workforceService()
      .retrieve()
      .then(
        res => {
          this.workforces = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IWorkforce): void {
    this.removeId = instance.id;
  }

  public removeWorkforce(): void {
    this.workforceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.workforce.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllWorkforces();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
