import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListOwnerships, ListOwnerships } from '@/shared/model/list-ownerships.model';
import ListOwnershipsService from './list-ownerships.service';

const validations: any = {
  listOwnerships: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListOwnershipsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listOwnershipsService') private listOwnershipsService: () => ListOwnershipsService;
  public listOwnerships: IListOwnerships = new ListOwnerships();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listOwnershipsId) {
        vm.retrieveListOwnerships(to.params.listOwnershipsId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listOwnerships.id) {
      this.listOwnershipsService()
        .update(this.listOwnerships)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listOwnerships.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listOwnershipsService()
        .create(this.listOwnerships)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listOwnerships.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListOwnerships(listOwnershipsId): void {
    this.listOwnershipsService()
      .find(listOwnershipsId)
      .then(res => {
        this.listOwnerships = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
