import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListPricings } from '@/shared/model/list-pricings.model';
import AlertService from '@/shared/alert/alert.service';

import ListPricingsService from './list-pricings.service';

@Component
export default class ListPricings extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listPricingsService') private listPricingsService: () => ListPricingsService;
  private removeId: number = null;
  public listPricings: IListPricings[] = [];

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
    this.retrieveAllListPricingss();
  }

  public clear(): void {
    this.retrieveAllListPricingss();
  }

  public retrieveAllListPricingss(): void {
    this.isFetching = true;

    this.listPricingsService()
      .retrieve()
      .then(
        res => {
          this.listPricings = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListPricings): void {
    this.removeId = instance.id;
  }

  public removeListPricings(): void {
    this.listPricingsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listPricings.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListPricingss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
