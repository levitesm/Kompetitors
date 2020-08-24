import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import JhiNavbarClass from '@/core/jhi-navbar/jhi-navbar.component';
import * as config from '@/shared/config/config';
import router from '@/router';

const localVue = createLocalVue();
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-navbar', {});
localVue.component('b-navbar-nav', {});
localVue.component('b-dropdown-item', {});
localVue.component('b-collapse', {});
localVue.component('b-nav-item', {});
localVue.component('b-nav-item-dropdown', {});
localVue.component('b-navbar-toggle', {});
localVue.component('b-navbar-brand', {});
localVue.component('b-navbar-nav', {});
localVue.component('jhi-search', {});

describe('JhiNavbar', () => {
  let jhiNavbar: JhiNavbarClass;
  let wrapper: Wrapper<JhiNavbarClass>;
  const loginService = { login: jest.fn(), logout: jest.fn() };
  const accountService = { hasAnyAuthority: jest.fn() };
  const translationService = { refreshTranslation: jest.fn(), retrieveTranslation: jest.fn() };
  const competitorsService = { getReferenceCompetitor: jest.fn().mockImplementation(() => Promise.resolve({})) };

  beforeEach(() => {
    wrapper = shallowMount<JhiNavbarClass>(JhiNavbar, {
      i18n,
      store,
      router,
      localVue,
      provide: {
        loginService: () => loginService,
        translationService: () => translationService,
        accountService: () => accountService,
        competitorsService: () => competitorsService
      }
    });
    jhiNavbar = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();

    expect(translationService.retrieveTranslation).toHaveBeenCalled();
  });

  it('should not have user data set', () => {
    expect(jhiNavbar.authenticated).toBeFalsy();
    expect(jhiNavbar.swaggerEnabled).toBeFalsy();
    expect(jhiNavbar.inProduction).toBeFalsy();
  });

  it('should have user data set after authentication', () => {
    store.commit('authenticated', { login: 'test' });

    expect(jhiNavbar.authenticated).toBeTruthy();
  });

  it('should have profile info set after info retrieved', () => {
    store.commit('setActiveProfiles', ['prod', 'swagger']);

    expect(jhiNavbar.swaggerEnabled).toBeTruthy();
    expect(jhiNavbar.inProduction).toBeTruthy();
  });

  it('should use login service', () => {
    jhiNavbar.openLogin();
    expect(loginService.login).toHaveBeenCalled();
  });

  it('should use account service', () => {
    jhiNavbar.hasAnyAuthority('auth');

    expect(accountService.hasAnyAuthority).toHaveBeenCalled();
  });

  it('logout should clear credentials', () => {
    store.commit('authenticated', { login: 'test' });
    loginService.logout.mockReturnValue(Promise.resolve());
    jhiNavbar.logout();

    expect(loginService.logout).toHaveBeenCalled();
  });

  it('should determine active route', () => {
    router.push('/toto');

    expect(jhiNavbar.subIsActive('/titi')).toBeFalsy();
    expect(jhiNavbar.subIsActive('/toto')).toBeTruthy();
    expect(jhiNavbar.subIsActive(['/toto', 'toto'])).toBeTruthy();
  });
});