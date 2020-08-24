import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import TranslationService from '@/locale/translation.service';

@Component({
  computed: {
    lang() {
      return this.translationService().retrieveTranslation();
    }
  }
})
export default class HelpComponent extends Vue {
  @Inject('translationService')
  private translationService: () => TranslationService;
}
