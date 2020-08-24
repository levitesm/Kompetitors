import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IDialogs, DialogsModel } from '@/shared/model/dialogs.model';
import DialogsService from './dialogs.service';

const validations: any = {
  dialogs: {
    section: {
      required
    },
    topic: {
      required
    },
    message: {
      required
    },
    author: {
      required
    },
    date: {}
  }
};

@Component({
  validations
})
export default class DialogsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dialogsService') private dialogsService: () => DialogsService;
  public dialogs: IDialogs = new DialogsModel();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dialogsId) {
        vm.retrieveDialogs(to.params.dialogsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.dialogs.id) {
      this.dialogsService()
        .update(this.dialogs)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.dialogs.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.dialogsService()
        .create(this.dialogs)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.dialogs.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDialogs(dialogsId): void {
    this.dialogsService()
      .find(dialogsId)
      .then(res => {
        this.dialogs = res;
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
