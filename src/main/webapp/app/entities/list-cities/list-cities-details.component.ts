import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListCities } from '@/shared/model/list-cities.model';
import ListCitiesService from './list-cities.service';

@Component
export default class ListCitiesDetails extends Vue {
  @Inject('listCitiesService') private listCitiesService: () => ListCitiesService;
  public listCities: IListCities = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCitiesId) {
        vm.retrieveListCities(to.params.listCitiesId);
      }
    });
  }

  public retrieveListCities(listCitiesId) {
    this.listCitiesService()
      .find(listCitiesId)
      .then(res => {
        this.listCities = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
