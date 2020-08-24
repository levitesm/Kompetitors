import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListClientsProjectTypes, ListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';
import ListClientsProjectTypesService from './list-clients-project-types.service';

const validations: any = {
  listClientsProjectTypes: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListClientsProjectTypesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listClientsProjectTypesService') private listClientsProjectTypesService: () => ListClientsProjectTypesService;
  public listClientsProjectTypes: IListClientsProjectTypes = new ListClientsProjectTypes();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listClientsProjectTypesId) {
        vm.retrieveListClientsProjectTypes(to.params.listClientsProjectTypesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listClientsProjectTypes.id) {
      this.listClientsProjectTypesService()
        .update(this.listClientsProjectTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listClientsProjectTypes.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listClientsProjectTypesService()
        .create(this.listClientsProjectTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listClientsProjectTypes.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListClientsProjectTypes(listClientsProjectTypesId): void {
    this.listClientsProjectTypesService()
      .find(listClientsProjectTypesId)
      .then(res => {
        this.listClientsProjectTypes = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
