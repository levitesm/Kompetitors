import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmployeeSalaries } from '@/shared/model/employee-salaries.model';
import AlertService from '@/shared/alert/alert.service';

import EmployeeSalariesService from './employee-salaries.service';

@Component
export default class EmployeeSalaries extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeeSalariesService') private employeeSalariesService: () => EmployeeSalariesService;
  private removeId: number = null;
  public employeeSalaries: IEmployeeSalaries[] = [];

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
    this.retrieveAllEmployeeSalariess();
  }

  public clear(): void {
    this.retrieveAllEmployeeSalariess();
  }

  public retrieveAllEmployeeSalariess(): void {
    this.isFetching = true;

    this.employeeSalariesService()
      .retrieve()
      .then(
        res => {
          this.employeeSalaries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IEmployeeSalaries): void {
    this.removeId = instance.id;
  }

  public removeEmployeeSalaries(): void {
    this.employeeSalariesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.employeeSalaries.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllEmployeeSalariess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
