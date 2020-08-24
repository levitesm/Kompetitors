import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICompetitorIndustry } from '@/shared/model/competitor-industry.model';
import AlertService from '@/shared/alert/alert.service';

import CompetitorIndustryService from './competitor-industry.service';

@Component
export default class CompetitorIndustry extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitorIndustryService') private competitorIndustryService: () => CompetitorIndustryService;
  private removeId: number = null;
  public competitorIndustries: ICompetitorIndustry[] = [];

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
    this.retrieveAllCompetitorIndustrys();
  }

  public clear(): void {
    this.retrieveAllCompetitorIndustrys();
  }

  public retrieveAllCompetitorIndustrys(): void {
    this.isFetching = true;

    this.competitorIndustryService()
      .retrieve()
      .then(
        res => {
          this.competitorIndustries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICompetitorIndustry): void {
    this.removeId = instance.id;
  }

  public removeCompetitorIndustry(): void {
    this.competitorIndustryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.competitorIndustry.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllCompetitorIndustrys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
