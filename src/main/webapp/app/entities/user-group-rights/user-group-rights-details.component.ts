import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserGroupRights } from '@/shared/model/user-group-rights.model';
import UserGroupRightsService from './user-group-rights.service';

@Component
export default class UserGroupRightsDetails extends Vue {
  @Inject('userGroupRightsService') private userGroupRightsService: () => UserGroupRightsService;
  public userGroupRights: IUserGroupRights = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userGroupRightsId) {
        vm.retrieveUserGroupRights(to.params.userGroupRightsId);
      }
    });
  }

  public retrieveUserGroupRights(userGroupRightsId) {
    this.userGroupRightsService()
      .find(userGroupRightsId)
      .then(res => {
        this.userGroupRights = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
