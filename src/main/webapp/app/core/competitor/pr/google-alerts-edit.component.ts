import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import { mapGetters } from 'vuex';
import { ExternalUrls } from '@/shared/model/external-urls.model';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';
import Question from '@/core/components/question.vue';

@Component({
  computed: {
    ...mapGetters({
      externalUrls: 'externalUrls'
    })
  },
  components: {
    Question
  }
})
export default class GoogleAlertsEditModal extends Vue {
  public externalUrls!: ExternalUrls;
  @Inject('externalUrlsService') private externalUrlsService: () => ExternalUrlsService;

  public externalUrlsModel: ExternalUrls = new ExternalUrls();
  public savingError: any = null;

  public resetForm() {
    this.savingError = null;
    this.externalUrlsModel = { ...this.externalUrls };
  }

  async save() {
    try {
      if (this.externalUrlsModel.id) {
        this.$store.commit('setExternalUrls', await this.externalUrlsService().update(this.externalUrlsModel));
      } else {
        this.$store.commit('setExternalUrls', await this.externalUrlsService().create(this.externalUrlsModel));
      }
      this.savingError = false;
    } catch (err) {
      this.savingError = err;
      return;
    }
    this.$root.$emit('bv::hide::modal', 'google-alerts-edit-modal');
    this.$emit('saved');
    this.$root.$emit('update_flags');
  }
}
