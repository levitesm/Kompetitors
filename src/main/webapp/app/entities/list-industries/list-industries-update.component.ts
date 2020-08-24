import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListIndustries, ListIndustries } from '@/shared/model/list-industries.model';
import ListIndustriesService from './list-industries.service';

const validations: any = {
  listIndustries: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListIndustriesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listIndustriesService') private listIndustriesService: () => ListIndustriesService;
  public listIndustries: IListIndustries = new ListIndustries();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listIndustriesId) {
        vm.retrieveListIndustries(to.params.listIndustriesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listIndustries.id) {
      this.listIndustriesService()
        .update(this.listIndustries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listIndustries.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listIndustriesService()
        .create(this.listIndustries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listIndustries.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListIndustries(listIndustriesId): void {
    this.listIndustriesService()
      .find(listIndustriesId)
      .then(res => {
        this.listIndustries = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
