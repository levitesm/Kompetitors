import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFinance } from '@/shared/model/finance.model';
import AlertService from '@/shared/alert/alert.service';

import FinanceService from './finance.service';

@Component
export default class Finance extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('financeService') private financeService: () => FinanceService;
  private removeId: number = null;
  public finances: IFinance[] = [];

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
    this.retrieveAllFinances();
  }

  public clear(): void {
    this.retrieveAllFinances();
  }

  public retrieveAllFinances(): void {
    this.isFetching = true;

    this.financeService()
      .retrieve()
      .then(
        res => {
          this.finances = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IFinance): void {
    this.removeId = instance.id;
  }

  public removeFinance(): void {
    this.financeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.finance.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllFinances();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
