import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IClientsProjects } from '@/shared/model/clients-projects.model';
import AlertService from '@/shared/alert/alert.service';

import ClientsProjectsService from './clients-projects.service';

@Component
export default class ClientsProjects extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('clientsProjectsService') private clientsProjectsService: () => ClientsProjectsService;
  private removeId: number = null;
  public clientsProjects: IClientsProjects[] = [];

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
    this.retrieveAllClientsProjectss();
  }

  public clear(): void {
    this.retrieveAllClientsProjectss();
  }

  public retrieveAllClientsProjectss(): void {
    this.isFetching = true;

    this.clientsProjectsService()
      .retrieve()
      .then(
        res => {
          this.clientsProjects = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IClientsProjects): void {
    this.removeId = instance.id;
  }

  public removeClientsProjects(): void {
    this.clientsProjectsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.clientsProjects.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllClientsProjectss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
