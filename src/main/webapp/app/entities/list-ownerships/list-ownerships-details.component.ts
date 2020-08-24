import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListOwnerships } from '@/shared/model/list-ownerships.model';
import ListOwnershipsService from './list-ownerships.service';

@Component
export default class ListOwnershipsDetails extends Vue {
  @Inject('listOwnershipsService') private listOwnershipsService: () => ListOwnershipsService;
  public listOwnerships: IListOwnerships = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listOwnershipsId) {
        vm.retrieveListOwnerships(to.params.listOwnershipsId);
      }
    });
  }

  public retrieveListOwnerships(listOwnershipsId) {
    this.listOwnershipsService()
      .find(listOwnershipsId)
      .then(res => {
        this.listOwnerships = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
