import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AccessKeyService from '../access-key/access-key.service';
import { IAccessKey } from '@/shared/model/access-key.model';

import AlertService from '@/shared/alert/alert.service';
import { IUserGroupRights, UserGroupRights } from '@/shared/model/user-group-rights.model';
import UserGroupRightsService from './user-group-rights.service';

const validations: any = {
  userGroupRights: {
    userGroupName: {
      required
    }
  }
};

@Component({
  validations
})
export default class UserGroupRightsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userGroupRightsService') private userGroupRightsService: () => UserGroupRightsService;
  public userGroupRights: IUserGroupRights = new UserGroupRights();

  @Inject('accessKeyService') private accessKeyService: () => AccessKeyService;

  public accessKeys: IAccessKey[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userGroupRightsId) {
        vm.retrieveUserGroupRights(to.params.userGroupRightsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.userGroupRights.id) {
      this.userGroupRightsService()
        .update(this.userGroupRights)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.userGroupRights.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.userGroupRightsService()
        .create(this.userGroupRights)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.userGroupRights.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUserGroupRights(userGroupRightsId): void {
    this.userGroupRightsService()
      .find(userGroupRightsId)
      .then(res => {
        this.userGroupRights = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.accessKeyService()
      .retrieve()
      .then(res => {
        this.accessKeys = res.data;
      });
  }
}
