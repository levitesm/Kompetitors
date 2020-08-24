import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDashboardFinance } from '@/shared/model/dashboard-finance.model';
import AlertService from '@/shared/alert/alert.service';

import DashboardFinanceService from './dashboard-finance.service';

@Component
export default class DashboardFinance extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dashboardFinanceService') private dashboardFinanceService: () => DashboardFinanceService;
  private removeId: number = null;
  public dashboardFinances: IDashboardFinance[] = [];

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
    this.retrieveAllDashboardFinances();
  }

  public clear(): void {
    this.retrieveAllDashboardFinances();
  }

  public retrieveAllDashboardFinances(): void {
    this.isFetching = true;

    this.dashboardFinanceService()
      .retrieve()
      .then(
        res => {
          this.dashboardFinances = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDashboardFinance): void {
    this.removeId = instance.id;
  }

  public removeDashboardFinance(): void {
    this.dashboardFinanceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.dashboardFinance.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllDashboardFinances();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
