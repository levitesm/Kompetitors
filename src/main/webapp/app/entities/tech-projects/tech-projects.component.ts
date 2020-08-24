import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITechProjects } from '@/shared/model/tech-projects.model';
import AlertService from '@/shared/alert/alert.service';

import TechProjectsService from './tech-projects.service';

@Component
export default class TechProjects extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techProjectsService') private techProjectsService: () => TechProjectsService;
  private removeId: number = null;
  public techProjects: ITechProjects[] = [];

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
    this.retrieveAllTechProjectss();
  }

  public clear(): void {
    this.retrieveAllTechProjectss();
  }

  public retrieveAllTechProjectss(): void {
    this.isFetching = true;

    this.techProjectsService()
      .retrieve()
      .then(
        res => {
          this.techProjects = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITechProjects): void {
    this.removeId = instance.id;
  }

  public removeTechProjects(): void {
    this.techProjectsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.techProjects.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTechProjectss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
