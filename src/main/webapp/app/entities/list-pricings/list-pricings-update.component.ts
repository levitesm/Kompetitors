import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListPricings, ListPricings } from '@/shared/model/list-pricings.model';
import ListPricingsService from './list-pricings.service';

const validations: any = {
  listPricings: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListPricingsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listPricingsService') private listPricingsService: () => ListPricingsService;
  public listPricings: IListPricings = new ListPricings();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listPricingsId) {
        vm.retrieveListPricings(to.params.listPricingsId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listPricings.id) {
      this.listPricingsService()
        .update(this.listPricings)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listPricings.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listPricingsService()
        .create(this.listPricings)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listPricings.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListPricings(listPricingsId): void {
    this.listPricingsService()
      .find(listPricingsId)
      .then(res => {
        this.listPricings = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
