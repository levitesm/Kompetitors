import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListServices } from '@/shared/model/list-services.model';
import AlertService from '@/shared/alert/alert.service';

import ListServicesService from './list-services.service';

@Component
export default class ListServices extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listServicesService') private listServicesService: () => ListServicesService;
  private removeId: number = null;
  public listServices: IListServices[] = [];

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
    this.retrieveAllListServicess();
  }

  public clear(): void {
    this.retrieveAllListServicess();
  }

  public retrieveAllListServicess(): void {
    this.isFetching = true;

    this.listServicesService()
      .retrieve()
      .then(
        res => {
          this.listServices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListServices): void {
    this.removeId = instance.id;
  }

  public removeListServices(): void {
    this.listServicesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listServices.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListServicess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
