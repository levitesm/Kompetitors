import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegionList } from '@/shared/model/region-list.model';
import RegionListService from './region-list.service';

@Component
export default class RegionListDetails extends Vue {
  @Inject('regionListService') private regionListService: () => RegionListService;
  public regionList: IRegionList = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionListId) {
        vm.retrieveRegionList(to.params.regionListId);
      }
    });
  }

  public retrieveRegionList(regionListId) {
    this.regionListService()
      .find(regionListId)
      .then(res => {
        this.regionList = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
