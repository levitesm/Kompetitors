import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListOwnerships } from '@/shared/model/list-ownerships.model';
import AlertService from '@/shared/alert/alert.service';

import ListOwnershipsService from './list-ownerships.service';

@Component
export default class ListOwnerships extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listOwnershipsService') private listOwnershipsService: () => ListOwnershipsService;
  private removeId: number = null;
  public listOwnerships: IListOwnerships[] = [];

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
    this.retrieveAllListOwnershipss();
  }

  public clear(): void {
    this.retrieveAllListOwnershipss();
  }

  public retrieveAllListOwnershipss(): void {
    this.isFetching = true;

    this.listOwnershipsService()
      .retrieve()
      .then(
        res => {
          this.listOwnerships = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListOwnerships): void {
    this.removeId = instance.id;
  }

  public removeListOwnerships(): void {
    this.listOwnershipsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listOwnerships.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListOwnershipss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
