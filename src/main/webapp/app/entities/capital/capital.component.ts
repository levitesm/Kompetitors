import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICapital } from '@/shared/model/capital.model';
import AlertService from '@/shared/alert/alert.service';

import CapitalService from './capital.service';

@Component
export default class Capital extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('capitalService') private capitalService: () => CapitalService;
  private removeId: number = null;
  public capitals: ICapital[] = [];

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
    this.retrieveAllCapitals();
  }

  public clear(): void {
    this.retrieveAllCapitals();
  }

  public retrieveAllCapitals(): void {
    this.isFetching = true;

    this.capitalService()
      .retrieve()
      .then(
        res => {
          this.capitals = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICapital): void {
    this.removeId = instance.id;
  }

  public removeCapital(): void {
    this.capitalService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.capital.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllCapitals();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
