import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHrInfo } from '@/shared/model/hr-info.model';
import AlertService from '@/shared/alert/alert.service';

import HrInfoService from './hr-info.service';

@Component
export default class HrInfo extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('hrInfoService') private hrInfoService: () => HrInfoService;
  private removeId: number = null;
  public hrInfos: IHrInfo[] = [];

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
    this.retrieveAllHrInfos();
  }

  public clear(): void {
    this.retrieveAllHrInfos();
  }

  public retrieveAllHrInfos(): void {
    this.isFetching = true;

    this.hrInfoService()
      .retrieve()
      .then(
        res => {
          this.hrInfos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IHrInfo): void {
    this.removeId = instance.id;
  }

  public removeHrInfo(): void {
    this.hrInfoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.hrInfo.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllHrInfos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
