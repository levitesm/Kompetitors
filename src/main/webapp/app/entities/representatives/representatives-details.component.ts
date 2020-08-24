import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRepresentatives } from '@/shared/model/representatives.model';
import RepresentativesService from './representatives.service';

@Component
export default class RepresentativesDetails extends Vue {
  @Inject('representativesService') private representativesService: () => RepresentativesService;
  public representatives: IRepresentatives = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.representativesId) {
        vm.retrieveRepresentatives(to.params.representativesId);
      }
    });
  }

  public retrieveRepresentatives(representativesId) {
    this.representativesService()
      .find(representativesId)
      .then(res => {
        this.representatives = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
