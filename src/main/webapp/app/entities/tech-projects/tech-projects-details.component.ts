import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITechProjects } from '@/shared/model/tech-projects.model';
import TechProjectsService from './tech-projects.service';

@Component
export default class TechProjectsDetails extends Vue {
  @Inject('techProjectsService') private techProjectsService: () => TechProjectsService;
  public techProjects: ITechProjects = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techProjectsId) {
        vm.retrieveTechProjects(to.params.techProjectsId);
      }
    });
  }

  public retrieveTechProjects(techProjectsId) {
    this.techProjectsService()
      .find(techProjectsId)
      .then(res => {
        this.techProjects = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
