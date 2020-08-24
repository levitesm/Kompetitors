import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListTools } from '@/shared/model/list-tools.model';
import ListToolsService from './list-tools.service';

@Component
export default class ListToolsDetails extends Vue {
  @Inject('listToolsService') private listToolsService: () => ListToolsService;
  public listTools: IListTools = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listToolsId) {
        vm.retrieveListTools(to.params.listToolsId);
      }
    });
  }

  public retrieveListTools(listToolsId) {
    this.listToolsService()
      .find(listToolsId)
      .then(res => {
        this.listTools = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
