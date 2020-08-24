import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IInfogreffe } from '@/shared/model/infogreffe.model';
import AlertService from '@/shared/alert/alert.service';

import InfogreffeService from './infogreffe.service';

@Component
export default class Infogreffe extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('infogreffeService') private infogreffeService: () => InfogreffeService;
  private removeId: number = null;
  public infogreffes: IInfogreffe[] = [];

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
    this.retrieveAllInfogreffes();
  }

  public clear(): void {
    this.retrieveAllInfogreffes();
  }

  public retrieveAllInfogreffes(): void {
    this.isFetching = true;

    this.infogreffeService()
      .retrieve()
      .then(
        res => {
          this.infogreffes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IInfogreffe): void {
    this.removeId = instance.id;
  }

  public removeInfogreffe(): void {
    this.infogreffeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.infogreffe.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllInfogreffes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
