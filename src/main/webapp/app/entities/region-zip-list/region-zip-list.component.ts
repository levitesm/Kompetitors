import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRegionZipList } from '@/shared/model/region-zip-list.model';
import AlertService from '@/shared/alert/alert.service';

import RegionZipListService from './region-zip-list.service';

@Component
export default class RegionZipList extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('regionZipListService') private regionZipListService: () => RegionZipListService;
  private removeId: number = null;
  public regionZipLists: IRegionZipList[] = [];

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
    this.retrieveAllRegionZipLists();
  }

  public clear(): void {
    this.retrieveAllRegionZipLists();
  }

  public retrieveAllRegionZipLists(): void {
    this.isFetching = true;

    this.regionZipListService()
      .retrieve()
      .then(
        res => {
          this.regionZipLists = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRegionZipList): void {
    this.removeId = instance.id;
  }

  public removeRegionZipList(): void {
    this.regionZipListService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.regionZipList.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllRegionZipLists();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
