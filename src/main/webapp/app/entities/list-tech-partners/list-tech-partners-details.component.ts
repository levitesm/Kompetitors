import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IListTechPartners } from '@/shared/model/list-tech-partners.model';
import ListTechPartnersService from './list-tech-partners.service';

@Component
export default class ListTechPartnersDetails extends mixins(JhiDataUtils) {
  @Inject('listTechPartnersService') private listTechPartnersService: () => ListTechPartnersService;
  public listTechPartners: IListTechPartners = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listTechPartnersId) {
        vm.retrieveListTechPartners(to.params.listTechPartnersId);
      }
    });
  }

  public retrieveListTechPartners(listTechPartnersId) {
    this.listTechPartnersService()
      .find(listTechPartnersId)
      .then(res => {
        this.listTechPartners = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
