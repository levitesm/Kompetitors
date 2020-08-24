import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAccessKey } from '@/shared/model/access-key.model';
import AlertService from '@/shared/alert/alert.service';

import AccessKeyService from './access-key.service';

@Component
export default class AccessKey extends mixins(Vue2Filters.mixin) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('accessKeyService') private accessKeyService: () => AccessKeyService;
  private removeId: number = null;
  public accessKeys: IAccessKey[] = [];

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
    this.retrieveAllAccessKeys();
  }

  public clear(): void {
    this.retrieveAllAccessKeys();
  }

  public retrieveAllAccessKeys(): void {
    this.isFetching = true;

    this.accessKeyService()
      .retrieve()
      .then(
        res => {
          this.accessKeys = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAccessKey): void {
    this.removeId = instance.id;
  }

  public removeAccessKey(): void {
    this.accessKeyService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('kompetitors2App.accessKey.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllAccessKeys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
