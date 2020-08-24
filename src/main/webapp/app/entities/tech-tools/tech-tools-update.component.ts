import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListToolsService from '../list-tools/list-tools.service';
import { IListTools } from '@/shared/model/list-tools.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechTools, TechTools } from '@/shared/model/tech-tools.model';
import TechToolsService from './tech-tools.service';

const validations: any = {
  techTools: {}
};

@Component({
  validations
})
export default class TechToolsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techToolsService') private techToolsService: () => TechToolsService;
  public techTools: ITechTools = new TechTools();

  @Inject('listToolsService') private listToolsService: () => ListToolsService;

  public listTools: IListTools[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techToolsId) {
        vm.retrieveTechTools(to.params.techToolsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techTools.id) {
      this.techToolsService()
        .update(this.techTools)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techTools.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techToolsService()
        .create(this.techTools)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techTools.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechTools(techToolsId): void {
    this.techToolsService()
      .find(techToolsId)
      .then(res => {
        this.techTools = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listToolsService()
      .retrieve()
      .then(res => {
        this.listTools = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
