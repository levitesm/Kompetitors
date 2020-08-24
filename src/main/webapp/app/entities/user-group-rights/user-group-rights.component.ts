import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUserGroupRights } from '@/shared/model/user-group-rights.model';
import AlertService from '@/shared/alert/alert.service';

import UserGroupRightsService from './user-group-rights.service';

@Component
export default class UserGroupRights extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userGroupRightsService') private userGroupRightsService: () => UserGroupRightsService;
  private removeId: number = null;
  public userGroupRights: IUserGroupRights[] = [];

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
    this.retrieveAllUserGroupRightss();
  }

  public clear(): void {
    this.retrieveAllUserGroupRightss();
  }

  public retrieveAllUserGroupRightss(): void {
    this.isFetching = true;

    this.userGroupRightsService()
      .retrieve()
      .then(
        res => {
          this.userGroupRights = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IUserGroupRights): void {
    this.removeId = instance.id;
  }

  public removeUserGroupRights(): void {
    this.userGroupRightsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.userGroupRights.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllUserGroupRightss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
