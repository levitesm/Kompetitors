import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListTechPartners } from '@/shared/model/list-tech-partners.model';
import AlertService from '@/shared/alert/alert.service';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ListTechPartnersService from './list-tech-partners.service';

@Component
export default class ListTechPartners extends mixins(JhiDataUtils, Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listTechPartnersService') private listTechPartnersService: () => ListTechPartnersService;
  private removeId: number = null;
  public listTechPartners: IListTechPartners[] = [];

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
    this.retrieveAllListTechPartnerss();
  }

  public clear(): void {
    this.retrieveAllListTechPartnerss();
  }

  public retrieveAllListTechPartnerss(): void {
    this.isFetching = true;

    this.listTechPartnersService()
      .retrieve()
      .then(
        res => {
          this.listTechPartners = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListTechPartners): void {
    this.removeId = instance.id;
  }

  public removeListTechPartners(): void {
    this.listTechPartnersService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listTechPartners.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListTechPartnerss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
