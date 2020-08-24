import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ISocieteMain, SocieteMain } from '@/shared/model/societe-main.model';
import SocieteMainService from './societe-main.service';

const validations: any = {
  societeMain: {
    siren: {},
    deno: {},
    greffe: {},
    enseigne: {},
    psiret: {},
    adresse: {},
    codepostal: {},
    normcommune: {},
    commune: {},
    ape: {},
    apetexte: {},
    dateimmat: {},
    dcren: {},
    nationalite: {},
    formejur: {},
    capital: {},
    devisecap: {},
    typecap: {},
    url: {}
  }
};

@Component({
  validations
})
export default class SocieteMainUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('societeMainService') private societeMainService: () => SocieteMainService;
  public societeMain: ISocieteMain = new SocieteMain();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.societeMainId) {
        vm.retrieveSocieteMain(to.params.societeMainId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.societeMain.id) {
      this.societeMainService()
        .update(this.societeMain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.societeMain.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.societeMainService()
        .create(this.societeMain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.societeMain.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSocieteMain(societeMainId): void {
    this.societeMainService()
      .find(societeMainId)
      .then(res => {
        this.societeMain = res;
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
