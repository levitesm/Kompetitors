import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IGlobalGroups, GlobalGroups } from '@/shared/model/global-groups.model';
import GlobalGroupsService from './global-groups.service';

const validations: any = {
  globalGroups: {
    name: {
      required
    },
    logo: {},
    webSite: {},
    reference: {}
  }
};

@Component({
  validations
})
export default class GlobalGroupsUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('globalGroupsService') private globalGroupsService: () => GlobalGroupsService;
  public globalGroups: IGlobalGroups = new GlobalGroups();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.globalGroupsId) {
        vm.retrieveGlobalGroups(to.params.globalGroupsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.globalGroups.id) {
      this.globalGroupsService()
        .update(this.globalGroups)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.globalGroups.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.globalGroupsService()
        .create(this.globalGroups)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.globalGroups.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveGlobalGroups(globalGroupsId): void {
    this.globalGroupsService()
      .find(globalGroupsId)
      .then(res => {
        this.globalGroups = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
