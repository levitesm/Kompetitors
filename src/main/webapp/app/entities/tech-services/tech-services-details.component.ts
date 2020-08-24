import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechServices } from '@/shared/model/tech-services.model';
import TechServicesService from './tech-services.service';

@Component
export default class TechServicesDetails extends Vue {
  @Inject('techServicesService') private techServicesService: () => TechServicesService;
  public techServices: ITechServices = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techServicesId) {
        vm.retrieveTechServices(to.params.techServicesId);
      }
    });
  }

  public retrieveTechServices(techServicesId) {
    this.techServicesService()
      .find(techServicesId)
      .then(res => {
        this.techServices = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
