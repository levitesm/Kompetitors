import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListProjectTypes, ListProjectTypes } from '@/shared/model/list-project-types.model';
import ListProjectTypesService from './list-project-types.service';

const validations: any = {
  listProjectTypes: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListProjectTypesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listProjectTypesService') private listProjectTypesService: () => ListProjectTypesService;
  public listProjectTypes: IListProjectTypes = new ListProjectTypes();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listProjectTypesId) {
        vm.retrieveListProjectTypes(to.params.listProjectTypesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listProjectTypes.id) {
      this.listProjectTypesService()
        .update(this.listProjectTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listProjectTypes.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listProjectTypesService()
        .create(this.listProjectTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listProjectTypes.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListProjectTypes(listProjectTypesId): void {
    this.listProjectTypesService()
      .find(listProjectTypesId)
      .then(res => {
        this.listProjectTypes = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
