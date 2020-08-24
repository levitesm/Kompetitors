import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListCityCountries } from '@/shared/model/list-city-countries.model';
import ListCityCountriesService from './list-city-countries.service';

@Component
export default class ListCityCountriesDetails extends Vue {
  @Inject('listCityCountriesService') private listCityCountriesService: () => ListCityCountriesService;
  public listCityCountries: IListCityCountries = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCityCountriesId) {
        vm.retrieveListCityCountries(to.params.listCityCountriesId);
      }
    });
  }

  public retrieveListCityCountries(listCityCountriesId) {
    this.listCityCountriesService()
      .find(listCityCountriesId)
      .then(res => {
        this.listCityCountries = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
