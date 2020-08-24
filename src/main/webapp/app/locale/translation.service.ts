import axios from 'axios';
import VueI18n from 'vue-i18n';
import { Store } from 'vuex';

const frenchLang = ['fr-BE', 'fr-CA', 'fr-CH', 'fr-FR', 'fr-LU', 'fr-MC'];

export default class TranslationService {
  private store: Store<{}>;
  private i18n: VueI18n;
  private cookie: any;

  constructor(store: Store<{}>, i18n: VueI18n, cookie: any) {
    this.store = store;
    this.i18n = i18n;
    this.cookie = cookie;
  }

  public retrieveTranslation(): string {
    const cookiesLanguage = this.cookie.get('kompetitors-lang');
    if (cookiesLanguage) {
      this.refreshTranslation(cookiesLanguage);
      return cookiesLanguage;
    } else {
      const language = frenchLang.includes(navigator.language) ? 'fr' : 'en';
      this.refreshTranslation(language);
      return language;
    }
  }

  public refreshTranslation(newLanguage: string) {
    const currentLanguage = newLanguage ? newLanguage : 'en';
    this.cookie.set('kompetitors-lang', currentLanguage);
    if (this.i18n && !this.i18n.messages[currentLanguage]) {
      this.i18n.setLocaleMessage(currentLanguage, {});
      axios.get('i18n/' + currentLanguage + '.json').then(res => {
        if (res.data) {
          this.i18n.setLocaleMessage(currentLanguage, res.data);
          this.i18n.locale = currentLanguage;
          this.store.commit('currentLanguage', currentLanguage);
        }
      });
    } else if (this.i18n) {
      this.i18n.locale = currentLanguage;
      this.store.commit('currentLanguage', currentLanguage);
    }
  }
}
