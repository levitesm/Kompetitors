import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITechPractices } from '@/shared/model/tech-practices.model';
import AlertService from '@/shared/alert/alert.service';

import TechPracticesService from './tech-practices.service';

@Component
export default class TechPractices extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techPracticesService') private techPracticesService: () => TechPracticesService;
  private removeId: number = null;
  public techPractices: ITechPractices[] = [];

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
    this.retrieveAllTechPracticess();
  }

  public clear(): void {
    this.retrieveAllTechPracticess();
  }

  public retrieveAllTechPracticess(): void {
    this.isFetching = true;

    this.techPracticesService()
      .retrieve()
      .then(
        res => {
          this.techPractices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITechPractices): void {
    this.removeId = instance.id;
  }

  public removeTechPractices(): void {
    this.techPracticesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.techPractices.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTechPracticess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
