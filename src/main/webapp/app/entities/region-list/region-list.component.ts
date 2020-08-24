import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRegionList } from '@/shared/model/region-list.model';
import AlertService from '@/shared/alert/alert.service';

import RegionListService from './region-list.service';

@Component
export default class RegionList extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('regionListService') private regionListService: () => RegionListService;
  private removeId: number = null;
  public regionLists: IRegionList[] = [];

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
    this.retrieveAllRegionLists();
  }

  public clear(): void {
    this.retrieveAllRegionLists();
  }

  public retrieveAllRegionLists(): void {
    this.isFetching = true;

    this.regionListService()
      .retrieve()
      .then(
        res => {
          this.regionLists = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRegionList): void {
    this.removeId = instance.id;
  }

  public removeRegionList(): void {
    this.regionListService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.regionList.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllRegionLists();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
