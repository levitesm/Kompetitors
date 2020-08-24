import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';
import ListClientsProjectTypesService from './list-clients-project-types.service';

@Component
export default class ListClientsProjectTypesDetails extends Vue {
  @Inject('listClientsProjectTypesService') private listClientsProjectTypesService: () => ListClientsProjectTypesService;
  public listClientsProjectTypes: IListClientsProjectTypes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listClientsProjectTypesId) {
        vm.retrieveListClientsProjectTypes(to.params.listClientsProjectTypesId);
      }
    });
  }

  public retrieveListClientsProjectTypes(listClientsProjectTypesId) {
    this.listClientsProjectTypesService()
      .find(listClientsProjectTypesId)
      .then(res => {
        this.listClientsProjectTypes = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
