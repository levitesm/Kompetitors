import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListIndustries } from '@/shared/model/list-industries.model';
import ListIndustriesService from './list-industries.service';

@Component
export default class ListIndustriesDetails extends Vue {
  @Inject('listIndustriesService') private listIndustriesService: () => ListIndustriesService;
  public listIndustries: IListIndustries = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listIndustriesId) {
        vm.retrieveListIndustries(to.params.listIndustriesId);
      }
    });
  }

  public retrieveListIndustries(listIndustriesId) {
    this.listIndustriesService()
      .find(listIndustriesId)
      .then(res => {
        this.listIndustries = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
