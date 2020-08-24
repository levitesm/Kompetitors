import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IShareHolders, ShareHolders } from '@/shared/model/share-holders.model';
import ShareHoldersService from './share-holders.service';

const validations: any = {
  shareHolders: {
    competitorSiren: {},
    typePersonne: {},
    denomination: {},
    civilite: {},
    nomPatronymique: {},
    nomUsage: {},
    prenom: {},
    libelleFormeJuridique: {},
    codeFormeJuridique: {},
    siren: {},
    codeApe: {},
    dateNaissance: {},
    nbrParts: {},
    pourcentageDetention: {},
    old: {}
  }
};

@Component({
  validations
})
export default class ShareHoldersUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('shareHoldersService') private shareHoldersService: () => ShareHoldersService;
  public shareHolders: IShareHolders = new ShareHolders();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.shareHoldersId) {
        vm.retrieveShareHolders(to.params.shareHoldersId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.shareHolders.id) {
      this.shareHoldersService()
        .update(this.shareHolders)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.shareHolders.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.shareHoldersService()
        .create(this.shareHolders)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.shareHolders.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveShareHolders(shareHoldersId): void {
    this.shareHoldersService()
      .find(shareHoldersId)
      .then(res => {
        this.shareHolders = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
