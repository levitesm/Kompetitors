import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRepresentatives } from '@/shared/model/representatives.model';
import AlertService from '@/shared/alert/alert.service';

import RepresentativesService from './representatives.service';

@Component
export default class Representatives extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('representativesService') private representativesService: () => RepresentativesService;
  private removeId: number = null;
  public representatives: IRepresentatives[] = [];

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
    this.retrieveAllRepresentativess();
  }

  public clear(): void {
    this.retrieveAllRepresentativess();
  }

  public retrieveAllRepresentativess(): void {
    this.isFetching = true;

    this.representativesService()
      .retrieve()
      .then(
        res => {
          this.representatives = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRepresentatives): void {
    this.removeId = instance.id;
  }

  public removeRepresentatives(): void {
    this.representativesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.representatives.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllRepresentativess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
