import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPeople } from '@/shared/model/people.model';
import PeopleService from './people.service';

@Component
export default class PeopleDetails extends Vue {
  @Inject('peopleService') private peopleService: () => PeopleService;
  public people: IPeople = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.peopleId) {
        vm.retrievePeople(to.params.peopleId);
      }
    });
  }

  public retrievePeople(peopleId) {
    this.peopleService()
      .find(peopleId)
      .then(res => {
        this.people = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
