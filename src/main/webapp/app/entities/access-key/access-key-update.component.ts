import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IAccessKey, AccessKey } from '@/shared/model/access-key.model';
import AccessKeyService from './access-key.service';

const validations: any = {
  accessKey: {
    name: {
      required
    },
    description: {}
  }
};

@Component({
  validations
})
export default class AccessKeyUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('accessKeyService') private accessKeyService: () => AccessKeyService;
  public accessKey: IAccessKey = new AccessKey();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.accessKeyId) {
        vm.retrieveAccessKey(to.params.accessKeyId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.accessKey.id) {
      this.accessKeyService()
        .update(this.accessKey)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.accessKey.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.accessKeyService()
        .create(this.accessKey)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.accessKey.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAccessKey(accessKeyId): void {
    this.accessKeyService()
      .find(accessKeyId)
      .then(res => {
        this.accessKey = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
