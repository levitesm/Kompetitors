import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListProjectTypesService from '../list-project-types/list-project-types.service';
import { IListProjectTypes } from '@/shared/model/list-project-types.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechProjects, TechProjects } from '@/shared/model/tech-projects.model';
import TechProjectsService from './tech-projects.service';

const validations: any = {
  techProjects: {}
};

@Component({
  validations
})
export default class TechProjectsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techProjectsService') private techProjectsService: () => TechProjectsService;
  public techProjects: ITechProjects = new TechProjects();

  @Inject('listProjectTypesService') private listProjectTypesService: () => ListProjectTypesService;

  public listProjectTypes: IListProjectTypes[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techProjectsId) {
        vm.retrieveTechProjects(to.params.techProjectsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techProjects.id) {
      this.techProjectsService()
        .update(this.techProjects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techProjects.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techProjectsService()
        .create(this.techProjects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techProjects.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechProjects(techProjectsId): void {
    this.techProjectsService()
      .find(techProjectsId)
      .then(res => {
        this.techProjects = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listProjectTypesService()
      .retrieve()
      .then(res => {
        this.listProjectTypes = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
