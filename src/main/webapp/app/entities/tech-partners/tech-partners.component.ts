import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITechPartners } from '@/shared/model/tech-partners.model';
import AlertService from '@/shared/alert/alert.service';

import TechPartnersService from './tech-partners.service';

@Component
export default class TechPartners extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techPartnersService') private techPartnersService: () => TechPartnersService;
  private removeId: number = null;
  public techPartners: ITechPartners[] = [];

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
    this.retrieveAllTechPartnerss();
  }

  public clear(): void {
    this.retrieveAllTechPartnerss();
  }

  public retrieveAllTechPartnerss(): void {
    this.isFetching = true;

    this.techPartnersService()
      .retrieve()
      .then(
        res => {
          this.techPartners = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITechPartners): void {
    this.removeId = instance.id;
  }

  public removeTechPartners(): void {
    this.techPartnersService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.techPartners.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTechPartnerss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
