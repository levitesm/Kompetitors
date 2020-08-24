import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRegionList, RegionList } from '@/shared/model/region-list.model';
import RegionListService from './region-list.service';

const validations: any = {
  regionList: {
    region: {},
    country: {}
  }
};

@Component({
  validations
})
export default class RegionListUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('regionListService') private regionListService: () => RegionListService;
  public regionList: IRegionList = new RegionList();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionListId) {
        vm.retrieveRegionList(to.params.regionListId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.regionList.id) {
      this.regionListService()
        .update(this.regionList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.regionList.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.regionListService()
        .create(this.regionList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.regionList.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRegionList(regionListId): void {
    this.regionListService()
      .find(regionListId)
      .then(res => {
        this.regionList = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
