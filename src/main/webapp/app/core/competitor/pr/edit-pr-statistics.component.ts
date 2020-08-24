import { Component, Inject, Prop, Vue } from 'vue-property-decorator';
import { PrInfo } from '@/shared/model/pr-info.model';
import CmpButton from '@/core/components/cmp-button.vue';
import PrInfoService from '@/entities/pr-info/pr-info.service';
import Question from '@/core/components/question.vue';

@Component({
  components: {
    CmpButton,
    Question
  }
})
export default class EditPrStatisticsModal extends Vue {
  @Prop() prInfo: PrInfo;
  @Inject('prInfoService') private prInfoService: () => PrInfoService;

  public prInfoModel: PrInfo = new PrInfo();
  public savingError = null;

  async saveChanges() {
    if (!this.validate()) {
      return;
    }
    try {
      const sameYear = new Date(this.prInfoModel.date).getFullYear() === new Date().getFullYear();
      if (this.prInfoModel.id && sameYear) {
        this.$store.commit('setPrInfo', await this.prInfoService().update(this.prInfoModel));
      } else {
        this.prInfoModel.id = null;
        this.prInfoModel.date = new Date();
        this.$store.commit('setPrInfo', await this.prInfoService().create(this.prInfoModel));
      }
      this.$root.$emit('update_flags');
    } catch (e) {
      this.savingError = e;
      return;
    }
    this.savingError = null;
    this.$root.$emit('bv::hide::modal', 'edit-pr-statistics-modal');
  }

  public validate(): boolean {
    if (isNaN(Number(this.prInfoModel.linkedInEngageRate))) {
      this.savingError = 'LinkedIn Engagement Rate should be positive number';
      return false;
    }
    return true;
  }

  resetForm() {
    this.savingError = null;
    this.prInfoModel = this.prInfo.id ? { ...this.prInfo } : PrInfo.emptyByCompetitorId(Number(this.$route.params.id));
  }
}
