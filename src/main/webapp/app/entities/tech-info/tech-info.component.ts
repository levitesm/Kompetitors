import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITechInfo } from '@/shared/model/tech-info.model';
import AlertService from '@/shared/alert/alert.service';

import TechInfoService from './tech-info.service';

@Component
export default class TechInfo extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techInfoService') private techInfoService: () => TechInfoService;
  private removeId: number = null;
  public techInfos: ITechInfo[] = [];

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
    this.retrieveAllTechInfos();
  }

  public clear(): void {
    this.retrieveAllTechInfos();
  }

  public retrieveAllTechInfos(): void {
    this.isFetching = true;

    this.techInfoService()
      .retrieve()
      .then(
        res => {
          this.techInfos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITechInfo): void {
    this.removeId = instance.id;
  }

  public removeTechInfo(): void {
    this.techInfoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.techInfo.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTechInfos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
