import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListServices, ListServices } from '@/shared/model/list-services.model';
import ListServicesService from './list-services.service';

const validations: any = {
  listServices: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListServicesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listServicesService') private listServicesService: () => ListServicesService;
  public listServices: IListServices = new ListServices();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listServicesId) {
        vm.retrieveListServices(to.params.listServicesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listServices.id) {
      this.listServicesService()
        .update(this.listServices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listServices.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listServicesService()
        .create(this.listServices)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listServices.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListServices(listServicesId): void {
    this.listServicesService()
      .find(listServicesId)
      .then(res => {
        this.listServices = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
