import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListActivities, ListActivities } from '@/shared/model/list-activities.model';
import ListActivitiesService from './list-activities.service';

const validations: any = {
  listActivities: {
    value: {
      required,
      minLength: minLength(3)
    }
  }
};

@Component({
  validations
})
export default class ListActivitiesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listActivitiesService') private listActivitiesService: () => ListActivitiesService;
  public listActivities: IListActivities = new ListActivities();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listActivitiesId) {
        vm.retrieveListActivities(to.params.listActivitiesId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listActivities.id) {
      this.listActivitiesService()
        .update(this.listActivities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listActivities.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listActivitiesService()
        .create(this.listActivities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listActivities.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListActivities(listActivitiesId): void {
    this.listActivitiesService()
      .find(listActivitiesId)
      .then(res => {
        this.listActivities = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
