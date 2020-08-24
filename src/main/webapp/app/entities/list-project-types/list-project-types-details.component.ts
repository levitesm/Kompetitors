import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListProjectTypes } from '@/shared/model/list-project-types.model';
import ListProjectTypesService from './list-project-types.service';

@Component
export default class ListProjectTypesDetails extends Vue {
  @Inject('listProjectTypesService') private listProjectTypesService: () => ListProjectTypesService;
  public listProjectTypes: IListProjectTypes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listProjectTypesId) {
        vm.retrieveListProjectTypes(to.params.listProjectTypesId);
      }
    });
  }

  public retrieveListProjectTypes(listProjectTypesId) {
    this.listProjectTypesService()
      .find(listProjectTypesId)
      .then(res => {
        this.listProjectTypes = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
