import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListPractices } from '@/shared/model/list-practices.model';
import AlertService from '@/shared/alert/alert.service';

import ListPracticesService from './list-practices.service';

@Component
export default class ListPractices extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listPracticesService') private listPracticesService: () => ListPracticesService;
  private removeId: number = null;
  public listPractices: IListPractices[] = [];

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
    this.retrieveAllListPracticess();
  }

  public clear(): void {
    this.retrieveAllListPracticess();
  }

  public retrieveAllListPracticess(): void {
    this.isFetching = true;

    this.listPracticesService()
      .retrieve()
      .then(
        res => {
          this.listPractices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListPractices): void {
    this.removeId = instance.id;
  }

  public removeListPractices(): void {
    this.listPracticesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listPractices.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListPracticess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
