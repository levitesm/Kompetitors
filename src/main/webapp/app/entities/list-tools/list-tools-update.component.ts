import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListTools, ListTools } from '@/shared/model/list-tools.model';
import ListToolsService from './list-tools.service';

const validations: any = {
  listTools: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListToolsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listToolsService') private listToolsService: () => ListToolsService;
  public listTools: IListTools = new ListTools();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listToolsId) {
        vm.retrieveListTools(to.params.listToolsId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listTools.id) {
      this.listToolsService()
        .update(this.listTools)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listTools.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listToolsService()
        .create(this.listTools)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listTools.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListTools(listToolsId): void {
    this.listToolsService()
      .find(listToolsId)
      .then(res => {
        this.listTools = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
