import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmployeeRole } from '@/shared/model/employee-role.model';
import AlertService from '@/shared/alert/alert.service';

import EmployeeRoleService from './employee-role.service';

@Component
export default class EmployeeRole extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('employeeRoleService') private employeeRoleService: () => EmployeeRoleService;
  private removeId: number = null;
  public employeeRoles: IEmployeeRole[] = [];

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
    this.retrieveAllEmployeeRoles();
  }

  public clear(): void {
    this.retrieveAllEmployeeRoles();
  }

  public retrieveAllEmployeeRoles(): void {
    this.isFetching = true;

    this.employeeRoleService()
      .retrieve()
      .then(
        res => {
          this.employeeRoles = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IEmployeeRole): void {
    this.removeId = instance.id;
  }

  public removeEmployeeRole(): void {
    this.employeeRoleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.employeeRole.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllEmployeeRoles();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
