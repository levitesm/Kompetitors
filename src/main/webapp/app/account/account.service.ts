import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';
import TranslationService from '@/locale/translation.service';

export default class AccountService {
  constructor(private store: Store<any>, private translationService: TranslationService, private cookie: any, private router: VueRouter) {
    this.init();
  }

  public init(): void {
    const token = this.cookie.get('JSESSIONID') || this.cookie.get('XSRF-TOKEN');
    if (!this.store.getters.account && !this.store.getters.logon && token) {
      this.retrieveAccount();
    }
    this.retrieveProfiles();
  }

  public retrieveProfiles(): void {
    axios.get('management/info').then(res => {
      if (res.data && res.data.activeProfiles) {
        this.store.commit('setRibbonOnProfiles', res.data['display-ribbon-on-profiles']);
        this.store.commit('setActiveProfiles', res.data['activeProfiles']);
      }
    });
  }

  public async retrieveAccount(): Promise<void> {
    this.store.commit('authenticate');
    return await axios
      .get('api/account')
      .then(response => {
        const account = response.data;
        if (account) {
          this.store.commit('authenticated', account);
          if (this.store.getters.currentLanguage !== account.langKey) {
            this.store.commit('currentLanguage', account.langKey);
          }
          if (sessionStorage.getItem('requested-url')) {
            this.router.replace(sessionStorage.getItem('requested-url'));
            sessionStorage.removeItem('requested-url');
          }
          this.store.commit('setAccessKeys', account['accessKeys'] ? account['accessKeys'] : []);
        } else {
          this.store.commit('logout');
          this.router.push('/');
          sessionStorage.removeItem('requested-url');
        }
      })
      .catch(() => {
        this.store.commit('logout');
        this.router.push('/');
      });
  }

  public hasAnyAuthority(authorities: any): boolean {
    if (typeof authorities === 'string') {
      authorities = [authorities];
    }
    if (!this.authenticated || !this.userAuthorities) {
      return false;
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.userAuthorities.includes(authorities[i])) {
        return true;
      }
    }

    return false;
  }

  public get authenticated(): boolean {
    return this.store.getters.authenticated;
  }

  public get userAuthorities(): any {
    return this.store.getters.account.authorities;
  }
}
