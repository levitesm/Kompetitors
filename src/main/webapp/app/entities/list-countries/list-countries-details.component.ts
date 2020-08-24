import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListCountries } from '@/shared/model/list-countries.model';
import ListCountriesService from './list-countries.service';

@Component
export default class ListCountriesDetails extends Vue {
  @Inject('listCountriesService') private listCountriesService: () => ListCountriesService;
  public listCountries: IListCountries = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCountriesId) {
        vm.retrieveListCountries(to.params.listCountriesId);
      }
    });
  }

  public retrieveListCountries(listCountriesId) {
    this.listCountriesService()
      .find(listCountriesId)
      .then(res => {
        this.listCountries = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
