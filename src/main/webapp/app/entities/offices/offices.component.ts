import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOffices } from '@/shared/model/offices.model';
import AlertService from '@/shared/alert/alert.service';

import OfficesService from './offices.service';

@Component
export default class Offices extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('officesService') private officesService: () => OfficesService;
  private removeId: number = null;
  public offices: IOffices[] = [];

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
    this.retrieveAllOfficess();
  }

  public clear(): void {
    this.retrieveAllOfficess();
  }

  public retrieveAllOfficess(): void {
    this.isFetching = true;

    this.officesService()
      .retrieve()
      .then(
        res => {
          this.offices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IOffices): void {
    this.removeId = instance.id;
  }

  public removeOffices(): void {
    this.officesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.offices.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllOfficess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
