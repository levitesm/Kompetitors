import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListPractices, ListPractices } from '@/shared/model/list-practices.model';
import ListPracticesService from './list-practices.service';

const validations: any = {
  listPractices: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListPracticesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listPracticesService') private listPracticesService: () => ListPracticesService;
  public listPractices: IListPractices = new ListPractices();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listPracticesId) {
        vm.retrieveListPractices(to.params.listPracticesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listPractices.id) {
      this.listPracticesService()
        .update(this.listPractices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listPractices.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listPracticesService()
        .create(this.listPractices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listPractices.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListPractices(listPracticesId): void {
    this.listPracticesService()
      .find(listPracticesId)
      .then(res => {
        this.listPractices = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
