import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILegal } from '@/shared/model/legal.model';
import AlertService from '@/shared/alert/alert.service';

import LegalService from './legal.service';

@Component
export default class Legal extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('legalService') private legalService: () => LegalService;
  private removeId: number = null;
  public legals: ILegal[] = [];

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
    this.retrieveAllLegals();
  }

  public clear(): void {
    this.retrieveAllLegals();
  }

  public retrieveAllLegals(): void {
    this.isFetching = true;

    this.legalService()
      .retrieve()
      .then(
        res => {
          this.legals = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ILegal): void {
    this.removeId = instance.id;
  }

  public removeLegal(): void {
    this.legalService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.legal.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllLegals();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
