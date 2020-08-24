import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IPeople, People } from '@/shared/model/people.model';
import PeopleService from './people.service';

const validations: any = {
  people: {
    fName: {
      required
    },
    lName: {
      required
    },
    title: {
      required
    },
    linkedPage: {},
    isKey: {}
  }
};

@Component({
  validations
})
export default class PeopleUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('peopleService') private peopleService: () => PeopleService;
  public people: IPeople = new People();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.peopleId) {
        vm.retrievePeople(to.params.peopleId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.people.id) {
      this.peopleService()
        .update(this.people)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.people.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.peopleService()
        .create(this.people)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.people.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePeople(peopleId): void {
    this.peopleService()
      .find(peopleId)
      .then(res => {
        this.people = res;
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
