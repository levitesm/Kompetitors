import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListProjectTypes } from '@/shared/model/list-project-types.model';
import AlertService from '@/shared/alert/alert.service';

import ListProjectTypesService from './list-project-types.service';

@Component
export default class ListProjectTypes extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listProjectTypesService') private listProjectTypesService: () => ListProjectTypesService;
  private removeId: number = null;
  public listProjectTypes: IListProjectTypes[] = [];

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
    this.retrieveAllListProjectTypess();
  }

  public clear(): void {
    this.retrieveAllListProjectTypess();
  }

  public retrieveAllListProjectTypess(): void {
    this.isFetching = true;

    this.listProjectTypesService()
      .retrieve()
      .then(
        res => {
          this.listProjectTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListProjectTypes): void {
    this.removeId = instance.id;
  }

  public removeListProjectTypes(): void {
    this.listProjectTypesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listProjectTypes.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListProjectTypess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
