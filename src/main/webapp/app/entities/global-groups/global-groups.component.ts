import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGlobalGroups } from '@/shared/model/global-groups.model';
import AlertService from '@/shared/alert/alert.service';

import JhiDataUtils from '@/shared/data/data-utils.service';

import GlobalGroupsService from './global-groups.service';

@Component
export default class GlobalGroups extends mixins(JhiDataUtils, Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('globalGroupsService') private globalGroupsService: () => GlobalGroupsService;
  private removeId: number = null;
  public globalGroups: IGlobalGroups[] = [];

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
    this.retrieveAllGlobalGroupss();
  }

  public clear(): void {
    this.retrieveAllGlobalGroupss();
  }

  public retrieveAllGlobalGroupss(): void {
    this.isFetching = true;

    this.globalGroupsService()
      .retrieve()
      .then(
        res => {
          this.globalGroups = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IGlobalGroups): void {
    this.removeId = instance.id;
  }

  public removeGlobalGroups(): void {
    this.globalGroupsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.globalGroups.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllGlobalGroupss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
