import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ListTechPartnersService from '../list-tech-partners/list-tech-partners.service';
import { IListTechPartners } from '@/shared/model/list-tech-partners.model';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { ITechPartners, TechPartners } from '@/shared/model/tech-partners.model';
import TechPartnersService from './tech-partners.service';

const validations: any = {
  techPartners: {}
};

@Component({
  validations
})
export default class TechPartnersUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('techPartnersService') private techPartnersService: () => TechPartnersService;
  public techPartners: ITechPartners = new TechPartners();

  @Inject('listTechPartnersService') private listTechPartnersService: () => ListTechPartnersService;

  public listTechPartners: IListTechPartners[] = [];

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.techPartnersId) {
        vm.retrieveTechPartners(to.params.techPartnersId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.techPartners.id) {
      this.techPartnersService()
        .update(this.techPartners)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techPartners.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.techPartnersService()
        .create(this.techPartners)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.techPartners.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTechPartners(techPartnersId): void {
    this.techPartnersService()
      .find(techPartnersId)
      .then(res => {
        this.techPartners = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listTechPartnersService()
      .retrieve()
      .then(res => {
        this.listTechPartners = res.data;
      });
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
