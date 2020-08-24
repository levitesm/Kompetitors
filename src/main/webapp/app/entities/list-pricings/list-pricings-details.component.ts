import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListPricings } from '@/shared/model/list-pricings.model';
import ListPricingsService from './list-pricings.service';

@Component
export default class ListPricingsDetails extends Vue {
  @Inject('listPricingsService') private listPricingsService: () => ListPricingsService;
  public listPricings: IListPricings = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listPricingsId) {
        vm.retrieveListPricings(to.params.listPricingsId);
      }
    });
  }

  public retrieveListPricings(listPricingsId) {
    this.listPricingsService()
      .find(listPricingsId)
      .then(res => {
        this.listPricings = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
