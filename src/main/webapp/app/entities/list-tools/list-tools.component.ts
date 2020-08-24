import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListTools } from '@/shared/model/list-tools.model';
import AlertService from '@/shared/alert/alert.service';

import ListToolsService from './list-tools.service';

@Component
export default class ListTools extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listToolsService') private listToolsService: () => ListToolsService;
  private removeId: number = null;
  public listTools: IListTools[] = [];

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
    this.retrieveAllListToolss();
  }

  public clear(): void {
    this.retrieveAllListToolss();
  }

  public retrieveAllListToolss(): void {
    this.isFetching = true;

    this.listToolsService()
      .retrieve()
      .then(
        res => {
          this.listTools = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListTools): void {
    this.removeId = instance.id;
  }

  public removeListTools(): void {
    this.listToolsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listTools.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListToolss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
