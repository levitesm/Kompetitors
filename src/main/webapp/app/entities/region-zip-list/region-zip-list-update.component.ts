import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRegionZipList, RegionZipList } from '@/shared/model/region-zip-list.model';
import RegionZipListService from './region-zip-list.service';

const validations: any = {
  regionZipList: {
    region: {},
    zip: {}
  }
};

@Component({
  validations
})
export default class RegionZipListUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('regionZipListService') private regionZipListService: () => RegionZipListService;
  public regionZipList: IRegionZipList = new RegionZipList();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionZipListId) {
        vm.retrieveRegionZipList(to.params.regionZipListId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.regionZipList.id) {
      this.regionZipListService()
        .update(this.regionZipList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.regionZipList.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.regionZipListService()
        .create(this.regionZipList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.regionZipList.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRegionZipList(regionZipListId): void {
    this.regionZipListService()
      .find(regionZipListId)
      .then(res => {
        this.regionZipList = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
