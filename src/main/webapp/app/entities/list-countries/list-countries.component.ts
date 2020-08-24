import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListCountries } from '@/shared/model/list-countries.model';
import AlertService from '@/shared/alert/alert.service';

import ListCountriesService from './list-countries.service';

@Component
export default class ListCountries extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCountriesService') private listCountriesService: () => ListCountriesService;
  private removeId: number = null;
  public listCountries: IListCountries[] = [];

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
    this.retrieveAllListCountriess();
  }

  public clear(): void {
    this.retrieveAllListCountriess();
  }

  public retrieveAllListCountriess(): void {
    this.isFetching = true;

    this.listCountriesService()
      .retrieve()
      .then(
        res => {
          this.listCountries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListCountries): void {
    this.removeId = instance.id;
  }

  public removeListCountries(): void {
    this.listCountriesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listCountries.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListCountriess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
