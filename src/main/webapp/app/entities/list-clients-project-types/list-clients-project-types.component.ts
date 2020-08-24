import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';
import AlertService from '@/shared/alert/alert.service';

import ListClientsProjectTypesService from './list-clients-project-types.service';

@Component
export default class ListClientsProjectTypes extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listClientsProjectTypesService') private listClientsProjectTypesService: () => ListClientsProjectTypesService;
  private removeId: number = null;
  public listClientsProjectTypes: IListClientsProjectTypes[] = [];

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
    this.retrieveAllListClientsProjectTypess();
  }

  public clear(): void {
    this.retrieveAllListClientsProjectTypess();
  }

  public retrieveAllListClientsProjectTypess(): void {
    this.isFetching = true;

    this.listClientsProjectTypesService()
      .retrieve()
      .then(
        res => {
          this.listClientsProjectTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IListClientsProjectTypes): void {
    this.removeId = instance.id;
  }

  public removeListClientsProjectTypes(): void {
    this.listClientsProjectTypesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.listClientsProjectTypes.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllListClientsProjectTypess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
