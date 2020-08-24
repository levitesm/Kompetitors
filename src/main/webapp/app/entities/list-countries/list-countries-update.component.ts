import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListCountries, ListCountries } from '@/shared/model/list-countries.model';
import ListCountriesService from './list-countries.service';

const validations: any = {
  listCountries: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListCountriesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCountriesService') private listCountriesService: () => ListCountriesService;
  public listCountries: IListCountries = new ListCountries();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCountriesId) {
        vm.retrieveListCountries(to.params.listCountriesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listCountries.id) {
      this.listCountriesService()
        .update(this.listCountries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCountries.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listCountriesService()
        .create(this.listCountries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCountries.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListCountries(listCountriesId): void {
    this.listCountriesService()
      .find(listCountriesId)
      .then(res => {
        this.listCountries = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
