import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IClients } from '@/shared/model/clients.model';
import AlertService from '@/shared/alert/alert.service';

import ClientsService from './clients.service';

@Component
export default class Clients extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('clientsService') private clientsService: () => ClientsService;
  private removeId: number = null;
  public clients: IClients[] = [];

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
    this.retrieveAllClientss();
  }

  public clear(): void {
    this.retrieveAllClientss();
  }

  public retrieveAllClientss(): void {
    this.isFetching = true;

    this.clientsService()
      .retrieve()
      .then(
        res => {
          this.clients = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IClients): void {
    this.removeId = instance.id;
  }

  public removeClients(): void {
    this.clientsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.clients.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllClientss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
