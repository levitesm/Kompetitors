import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListCities } from '@/shared/model/list-cities.model';
import AlertService from '@/shared/alert/alert.service';

import ListCitiesService from './list-cities.service';

@Component
export default class ListCities extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCitiesService') private listCitiesService: () => ListCitiesService;
  private removeId: number = null;
  public listCities: IListCities[] = [];

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
    this.retrieveAllListCitiess();
  }

  public clear(): void {
    this.retrieveAllListCitiess();
  }

  public retrieveAllListCitiess(): void {
    this.isFetching = true;

    this.listCitiesService()
      .retrieve()
      .then(
        res => {
          this.listCities = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListCities): void {
    this.removeId = instance.id;
  }

  public removeListCities(): void {
    this.listCitiesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listCities.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListCitiess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
