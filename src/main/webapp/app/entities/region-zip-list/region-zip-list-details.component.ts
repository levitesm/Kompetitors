import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegionZipList } from '@/shared/model/region-zip-list.model';
import RegionZipListService from './region-zip-list.service';

@Component
export default class RegionZipListDetails extends Vue {
  @Inject('regionZipListService') private regionZipListService: () => RegionZipListService;
  public regionZipList: IRegionZipList = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionZipListId) {
        vm.retrieveRegionZipList(to.params.regionZipListId);
      }
    });
  }

  public retrieveRegionZipList(regionZipListId) {
    this.regionZipListService()
      .find(regionZipListId)
      .then(res => {
        this.regionZipList = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
