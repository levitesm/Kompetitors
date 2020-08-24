import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmployeeType } from '@/shared/model/employee-type.model';
import AlertService from '@/shared/alert/alert.service';

import EmployeeTypeService from './employee-type.service';

@Component
export default class EmployeeType extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeeTypeService') private employeeTypeService: () => EmployeeTypeService;
  private removeId: number = null;
  public employeeTypes: IEmployeeType[] = [];

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
    this.retrieveAllEmployeeTypes();
  }

  public clear(): void {
    this.retrieveAllEmployeeTypes();
  }

  public retrieveAllEmployeeTypes(): void {
    this.isFetching = true;

    this.employeeTypeService()
      .retrieve()
      .then(
        res => {
          this.employeeTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IEmployeeType): void {
    this.removeId = instance.id;
  }

  public removeEmployeeType(): void {
    this.employeeTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.employeeType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllEmployeeTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
