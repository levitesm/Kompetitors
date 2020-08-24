import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListCityCountriesService from '../list-city-countries/list-city-countries.service';
import { IListCityCountries } from '@/shared/model/list-city-countries.model';

import AlertService from '@/shared/alert/alert.service';
import { IListCities, ListCities } from '@/shared/model/list-cities.model';
import ListCitiesService from './list-cities.service';

const validations: any = {
  listCities: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListCitiesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCitiesService') private listCitiesService: () => ListCitiesService;
  public listCities: IListCities = new ListCities();

  @Inject('listCityCountriesService') private listCityCountriesService: () => ListCityCountriesService;

  public listCityCountries: IListCityCountries[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCitiesId) {
        vm.retrieveListCities(to.params.listCitiesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listCities.id) {
      this.listCitiesService()
        .update(this.listCities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCities.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listCitiesService()
        .create(this.listCities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCities.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListCities(listCitiesId): void {
    this.listCitiesService()
      .find(listCitiesId)
      .then(res => {
        this.listCities = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listCityCountriesService()
      .retrieve()
      .then(res => {
        this.listCityCountries = res.data;
      });
  }
}
