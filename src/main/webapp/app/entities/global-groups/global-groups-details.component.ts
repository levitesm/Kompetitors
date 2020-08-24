import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IGlobalGroups } from '@/shared/model/global-groups.model';
import GlobalGroupsService from './global-groups.service';

@Component
export default class GlobalGroupsDetails extends mixins(JhiDataUtils) {
  @Inject('globalGroupsService') private globalGroupsService: () => GlobalGroupsService;
  public globalGroups: IGlobalGroups = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.globalGroupsId) {
        vm.retrieveGlobalGroups(to.params.globalGroupsId);
      }
    });
  }

  public retrieveGlobalGroups(globalGroupsId) {
    this.globalGroupsService()
      .find(globalGroupsId)
      .then(res => {
        this.globalGroups = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
