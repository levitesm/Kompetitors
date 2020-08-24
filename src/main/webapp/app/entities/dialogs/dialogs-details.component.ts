import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDialogs } from '@/shared/model/dialogs.model';
import DialogsService from './dialogs.service';

@Component
export default class DialogsDetails extends Vue {
  @Inject('dialogsService') private dialogsService: () => DialogsService;
  public dialogs: IDialogs = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dialogsId) {
        vm.retrieveDialogs(to.params.dialogsId);
      }
    });
  }

  public retrieveDialogs(dialogsId) {
    this.dialogsService()
      .find(dialogsId)
      .then(res => {
        this.dialogs = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
