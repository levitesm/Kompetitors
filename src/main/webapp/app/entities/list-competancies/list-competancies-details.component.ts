import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListCompetancies } from '@/shared/model/list-competancies.model';
import ListCompetanciesService from './list-competancies.service';

@Component
export default class ListCompetanciesDetails extends Vue {
  @Inject('listCompetanciesService') private listCompetanciesService: () => ListCompetanciesService;
  public listCompetancies: IListCompetancies = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCompetanciesId) {
        vm.retrieveListCompetancies(to.params.listCompetanciesId);
      }
    });
  }

  public retrieveListCompetancies(listCompetanciesId) {
    this.listCompetanciesService()
      .find(listCompetanciesId)
      .then(res => {
        this.listCompetancies = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
