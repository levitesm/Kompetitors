import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import { mapGetters } from 'vuex';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';
import { ExternalUrls } from '@/shared/model/external-urls.model';
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
export default class EditPr extends Vue {
  public externalUrls!: ExternalUrls;

  @Inject('externalUrlsService') private externalUrlsService: () => ExternalUrlsService;
  public savingError = null;

  private urlsModel: ExternalUrls = new ExternalUrls();

  public resetForm() {
    this.savingError = null;
    this.urlsModel = { ...this.externalUrls };
  }

  public async saveChanges(): Promise<void> {
    try {
      if (this.urlsModel.id) {
        this.$store.commit('setExternalUrls', await this.externalUrlsService().update(this.urlsModel));
      } else {
        this.$store.commit('setExternalUrls', await this.externalUrlsService().create(this.urlsModel));
      }
      this.savingError = false;
    } catch (err) {
      this.savingError = err;
      return;
    }
    this.$root.$emit('bv::hide::modal', 'edit-pr-modal');
    this.$root.$emit('update_flags');
  }
}
