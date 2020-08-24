import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICompetitiveRates } from '@/shared/model/competitive-rates.model';
import AlertService from '@/shared/alert/alert.service';

import CompetitiveRatesService from './competitive-rates.service';

@Component
export default class CompetitiveRates extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;
  private removeId: number = null;
  public competitiveRates: ICompetitiveRates[] = [];

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
    this.retrieveAllCompetitiveRatess();
  }

  public clear(): void {
    this.retrieveAllCompetitiveRatess();
  }

  public retrieveAllCompetitiveRatess(): void {
    this.isFetching = true;

    this.competitiveRatesService()
      .retrieve()
      .then(
        res => {
          this.competitiveRates = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICompetitiveRates): void {
    this.removeId = instance.id;
  }

  public removeCompetitiveRates(): void {}

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
