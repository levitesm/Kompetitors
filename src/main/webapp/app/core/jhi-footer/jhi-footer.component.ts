import { Component, Inject, Vue } from 'vue-property-decorator';
import TranslationService from '@/locale/translation.service';

@Component
export default class JhiFooter extends Vue {
  @Inject('translationService')
  private translationService: () => TranslationService;

  public onLanguageChange(value: string): void {
    this.translationService().refreshTranslation(value);
  }

  public termsPage(): void {
    this.$router.push('/terms');
  }

  public privacyPage(): void {
    this.$router.push('/privacy');
  }

  public securityPage(): void {
    this.$router.push('/security');
  }

  public helpPage(): void {
    this.$router.push('/help');
  }
}
