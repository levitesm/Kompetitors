import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmployeePricing } from '@/shared/model/employee-pricing.model';
import AlertService from '@/shared/alert/alert.service';

import EmployeePricingService from './employee-pricing.service';

@Component
export default class EmployeePricing extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeePricingService') private employeePricingService: () => EmployeePricingService;
  private removeId: number = null;
  public employeePricings: IEmployeePricing[] = [];

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
    this.retrieveAllEmployeePricings();
  }

  public clear(): void {
    this.retrieveAllEmployeePricings();
  }

  public retrieveAllEmployeePricings(): void {
    this.isFetching = true;

    this.employeePricingService()
      .retrieve()
      .then(
        res => {
          this.employeePricings = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IEmployeePricing): void {
    this.removeId = instance.id;
  }

  public removeEmployeePricing(): void {
    this.employeePricingService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.employeePricing.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllEmployeePricings();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
