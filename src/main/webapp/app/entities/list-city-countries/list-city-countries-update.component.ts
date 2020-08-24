import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListCityCountries, ListCityCountries } from '@/shared/model/list-city-countries.model';
import ListCityCountriesService from './list-city-countries.service';

const validations: any = {
  listCityCountries: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListCityCountriesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCityCountriesService') private listCityCountriesService: () => ListCityCountriesService;
  public listCityCountries: IListCityCountries = new ListCityCountries();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCityCountriesId) {
        vm.retrieveListCityCountries(to.params.listCityCountriesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listCityCountries.id) {
      this.listCityCountriesService()
        .update(this.listCityCountries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCityCountries.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listCityCountriesService()
        .create(this.listCityCountries)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCityCountries.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListCityCountries(listCityCountriesId): void {
    this.listCityCountriesService()
      .find(listCityCountriesId)
      .then(res => {
        this.listCityCountries = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
