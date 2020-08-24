import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITechTools } from '@/shared/model/tech-tools.model';
import AlertService from '@/shared/alert/alert.service';

import TechToolsService from './tech-tools.service';

@Component
export default class TechTools extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techToolsService') private techToolsService: () => TechToolsService;
  private removeId: number = null;
  public techTools: ITechTools[] = [];

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
    this.retrieveAllTechToolss();
  }

  public clear(): void {
    this.retrieveAllTechToolss();
  }

  public retrieveAllTechToolss(): void {
    this.isFetching = true;

    this.techToolsService()
      .retrieve()
      .then(
        res => {
          this.techTools = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITechTools): void {
    this.removeId = instance.id;
  }

  public removeTechTools(): void {
    this.techToolsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.techTools.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTechToolss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
