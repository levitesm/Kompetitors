import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListIndustries } from '@/shared/model/list-industries.model';
import AlertService from '@/shared/alert/alert.service';

import ListIndustriesService from './list-industries.service';

@Component
export default class ListIndustries extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listIndustriesService') private listIndustriesService: () => ListIndustriesService;
  private removeId: number = null;
  public listIndustries: IListIndustries[] = [];

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
    this.retrieveAllListIndustriess();
  }

  public clear(): void {
    this.retrieveAllListIndustriess();
  }

  public retrieveAllListIndustriess(): void {
    this.isFetching = true;

    this.listIndustriesService()
      .retrieve()
      .then(
        res => {
          this.listIndustries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListIndustries): void {
    this.removeId = instance.id;
  }

  public removeListIndustries(): void {
    this.listIndustriesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listIndustries.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListIndustriess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
