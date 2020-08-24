import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITechServices } from '@/shared/model/tech-services.model';
import AlertService from '@/shared/alert/alert.service';

import TechServicesService from './tech-services.service';

@Component
export default class TechServices extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techServicesService') private techServicesService: () => TechServicesService;
  private removeId: number = null;
  public techServices: ITechServices[] = [];

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
    this.retrieveAllTechServicess();
  }

  public clear(): void {
    this.retrieveAllTechServicess();
  }

  public retrieveAllTechServicess(): void {
    this.isFetching = true;

    this.techServicesService()
      .retrieve()
      .then(
        res => {
          this.techServices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITechServices): void {
    this.removeId = instance.id;
  }

  public removeTechServices(): void {
    this.techServicesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.techServices.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTechServicess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
