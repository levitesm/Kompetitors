import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListActivities } from '@/shared/model/list-activities.model';
import ListActivitiesService from './list-activities.service';

@Component
export default class ListActivitiesDetails extends Vue {
  @Inject('listActivitiesService') private listActivitiesService: () => ListActivitiesService;
  public listActivities: IListActivities = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listActivitiesId) {
        vm.retrieveListActivities(to.params.listActivitiesId);
      }
    });
  }

  public retrieveListActivities(listActivitiesId) {
    this.listActivitiesService()
      .find(listActivitiesId)
      .then(res => {
        this.listActivities = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
