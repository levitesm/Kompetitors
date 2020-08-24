import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListPractices } from '@/shared/model/list-practices.model';
import ListPracticesService from './list-practices.service';

@Component
export default class ListPracticesDetails extends Vue {
  @Inject('listPracticesService') private listPracticesService: () => ListPracticesService;
  public listPractices: IListPractices = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listPracticesId) {
        vm.retrieveListPractices(to.params.listPracticesId);
      }
    });
  }

  public retrieveListPractices(listPracticesId) {
    this.listPracticesService()
      .find(listPracticesId)
      .then(res => {
        this.listPractices = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
