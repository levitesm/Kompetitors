import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IUpdatehistory, Updatehistory } from '@/shared/model/updatehistory.model';
import UpdatehistoryService from './updatehistory.service';

const validations: any = {
  updatehistory: {
    type: {},
    siren: {},
    date: {},
    status: {},
    responce: {}
  }
};

@Component({
  validations
})
export default class UpdatehistoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('updatehistoryService') private updatehistoryService: () => UpdatehistoryService;
  public updatehistory: IUpdatehistory = new Updatehistory();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.updatehistoryId) {
        vm.retrieveUpdatehistory(to.params.updatehistoryId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.updatehistory.id) {
      this.updatehistoryService()
        .update(this.updatehistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.updatehistory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.updatehistoryService()
        .create(this.updatehistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.updatehistory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUpdatehistory(updatehistoryId): void {
    this.updatehistoryService()
      .find(updatehistoryId)
      .then(res => {
        this.updatehistory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
