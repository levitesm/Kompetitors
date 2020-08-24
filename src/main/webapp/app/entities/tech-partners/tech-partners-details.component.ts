import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechPartners } from '@/shared/model/tech-partners.model';
import TechPartnersService from './tech-partners.service';

@Component
export default class TechPartnersDetails extends Vue {
  @Inject('techPartnersService') private techPartnersService: () => TechPartnersService;
  public techPartners: ITechPartners = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techPartnersId) {
        vm.retrieveTechPartners(to.params.techPartnersId);
      }
    });
  }

  public retrieveTechPartners(techPartnersId) {
    this.techPartnersService()
      .find(techPartnersId)
      .then(res => {
        this.techPartners = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
