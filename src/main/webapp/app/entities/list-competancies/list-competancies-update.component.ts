import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListCompetancies, ListCompetancies } from '@/shared/model/list-competancies.model';
import ListCompetanciesService from './list-competancies.service';

const validations: any = {
  listCompetancies: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListCompetanciesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listCompetanciesService') private listCompetanciesService: () => ListCompetanciesService;
  public listCompetancies: IListCompetancies = new ListCompetancies();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listCompetanciesId) {
        vm.retrieveListCompetancies(to.params.listCompetanciesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listCompetancies.id) {
      this.listCompetanciesService()
        .update(this.listCompetancies)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCompetancies.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listCompetanciesService()
        .create(this.listCompetancies)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listCompetancies.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListCompetancies(listCompetanciesId): void {
    this.listCompetanciesService()
      .find(listCompetanciesId)
      .then(res => {
        this.listCompetancies = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
