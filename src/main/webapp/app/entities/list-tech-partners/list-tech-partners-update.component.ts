import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IListTechPartners, ListTechPartners } from '@/shared/model/list-tech-partners.model';
import ListTechPartnersService from './list-tech-partners.service';

const validations: any = {
  listTechPartners: {
    value: {
      required,
      minLength: minLength(3)
    },
    image: {}
  }
};

@Component({
  validations
})
export default class ListTechPartnersUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listTechPartnersService') private listTechPartnersService: () => ListTechPartnersService;
  public listTechPartners: IListTechPartners = new ListTechPartners();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listTechPartnersId) {
        vm.retrieveListTechPartners(to.params.listTechPartnersId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.listTechPartners.id) {
      this.listTechPartnersService()
        .update(this.listTechPartners)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listTechPartners.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listTechPartnersService()
        .create(this.listTechPartners)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.listTechPartners.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveListTechPartners(listTechPartnersId): void {
    this.listTechPartnersService()
      .find(listTechPartnersId)
      .then(res => {
        this.listTechPartners = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.listTechPartners && field && fieldContentType) {
      if (this.listTechPartners.hasOwnProperty(field)) {
        this.listTechPartners[field] = null;
      }
      if (this.listTechPartners.hasOwnProperty(fieldContentType)) {
        this.listTechPartners[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {}
}
