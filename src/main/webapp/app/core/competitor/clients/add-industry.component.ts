import { Vue, Inject, Prop, Watch } from 'vue-property-decorator';
import Component, { mixins } from 'vue-class-component';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';
import { ListIndustries } from '@/shared/model/list-industries.model';
import JhiDataUtils from '@/shared/data/data-utils.service';

@Component
export default class AddIndustry extends mixins(JhiDataUtils) {
  @Inject('listIndustriesService') public listIndustriesService: () => ListIndustriesService;

  public model: ListIndustries = new ListIndustries();
  public savingError = null;

  resetModel() {
    this.model = new ListIndustries();
  }

  async save() {
    if (!this.validate()) {
      return;
    }
    try {
      const response = await this.listIndustriesService().create(this.model);
      this.$store.commit('setDictionary', { data: response, add: true });
      this.resetModel();
      this.$root.$emit('bv::hide::modal', 'add-industry');
      this.$root.$emit('update_flags');
    } catch (err) {
      this.savingError = err;
    }
  }

  validate(): boolean {
    if (this.model.value === undefined || this.model.value.length < 3) {
      this.savingError = 'Name length should be 3 or more!';
      return false;
    }
    this.savingError = null;
    return true;
  }
}
