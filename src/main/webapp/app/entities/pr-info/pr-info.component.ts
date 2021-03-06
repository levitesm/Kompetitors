import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPrInfo } from '@/shared/model/pr-info.model';
import AlertService from '@/shared/alert/alert.service';

import PrInfoService from './pr-info.service';

@Component
export default class PrInfo extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('prInfoService') private prInfoService: () => PrInfoService;
  private removeId: number = null;
  public prInfos: IPrInfo[] = [];

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
    this.retrieveAllPrInfos();
  }

  public clear(): void {
    this.retrieveAllPrInfos();
  }

  public retrieveAllPrInfos(): void {
    this.isFetching = true;

    this.prInfoService()
      .retrieve()
      .then(
        res => {
          this.prInfos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPrInfo): void {
    this.removeId = instance.id;
  }

  public removePrInfo(): void {
    this.prInfoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.prInfo.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllPrInfos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
