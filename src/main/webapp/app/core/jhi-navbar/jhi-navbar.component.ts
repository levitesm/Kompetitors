import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
import JhiSearch from '@/core/jhi-navbar/jhi-search.vue';
import CompetitorsService from '@/entities/competitors/competitors.service';
import { mapGetters } from 'vuex';
import { IAnnualAccount } from '@/shared/model/annual-account.model';

@Component({
  components: {
    'jhi-search': JhiSearch
  },
  computed: {
    ...mapGetters({
      referenceCompetitor: 'referenceCompetitor'
    })
  }
})
export default class JhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('competitorsService') private competitorsService: () => CompetitorsService;
  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  public referenceCompetitor: IAnnualAccount;

  data() {
    return {
      showSearch: true
    };
  }

  created() {
    this.translationService().retrieveTranslation();
    this.competitorsService()
      .getReferenceCompetitor()
      .then(response => {
        this.$store.commit('setReferenceCompetitor', response);
      });
  }

  get referenceCompetitorPath(): string {
    return this.referenceCompetitor ? `/competitor/${this.referenceCompetitor.id}` : undefined;
  }

  get referenceCompetitorDashboard(): string {
    return this.referenceCompetitor ? `/dashboard/${this.referenceCompetitor.id}` : undefined;
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public logout(): void {
    this.loginService()
      .logout()
      .then(response => {
        this.$store.commit('logout');
        this.$router.push('/');
        const data = response.data;
        let logoutUrl = data.logoutUrl;
        // if Keycloak, uri has protocol/openid-connect/token
        if (logoutUrl.indexOf('/protocol') > -1) {
          logoutUrl = logoutUrl + '?redirect_uri=' + window.location.origin;
        } else {
          // Okta
          logoutUrl = logoutUrl + '?id_token_hint=' + data.idToken + '&post_logout_redirect_uri=' + window.location.origin;
        }
        window.location.href = logoutUrl;
      });
  }

  public openLogin(): void {
    this.loginService().login();
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    return this.accountService().hasAnyAuthority(authorities);
  }

  public get swaggerEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('swagger') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public addCompetitor(): void {
    this.$store.commit('setCreateGroup', true);
    this.$root.$emit('bv::show::modal', 'add-competitor');
  }
  mounted() {
    this.$root.$on('close-search', () => (this.$data.showSearch = false));
  }

  public hideSearch() {
    this.$root.$emit('clear_hide_search');
  }
}
