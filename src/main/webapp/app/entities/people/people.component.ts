import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPeople } from '@/shared/model/people.model';
import AlertService from '@/shared/alert/alert.service';

import PeopleService from './people.service';

@Component
export default class People extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('peopleService') private peopleService: () => PeopleService;
  private removeId: number = null;
  public people: IPeople[] = [];

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
    this.retrieveAllPeoples();
  }

  public clear(): void {
    this.retrieveAllPeoples();
  }

  public retrieveAllPeoples(): void {
    this.isFetching = true;

    this.peopleService()
      .retrieve()
      .then(
        res => {
          this.people = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPeople): void {
    this.removeId = instance.id;
  }

  public removePeople(): void {
    this.peopleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.people.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllPeoples();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
