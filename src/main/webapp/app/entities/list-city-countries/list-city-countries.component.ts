import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListCityCountries } from '@/shared/model/list-city-countries.model';
import AlertService from '@/shared/alert/alert.service';

import ListCityCountriesService from './list-city-countries.service';

@Component
export default class ListCityCountries extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCityCountriesService') private listCityCountriesService: () => ListCityCountriesService;
  private removeId: number = null;
  public listCityCountries: IListCityCountries[] = [];

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
    this.retrieveAllListCityCountriess();
  }

  public clear(): void {
    this.retrieveAllListCityCountriess();
  }

  public retrieveAllListCityCountriess(): void {
    this.isFetching = true;

    this.listCityCountriesService()
      .retrieve()
      .then(
        res => {
          this.listCityCountries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListCityCountries): void {
    this.removeId = instance.id;
  }

  public removeListCityCountries(): void {
    this.listCityCountriesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listCityCountries.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListCityCountriess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
