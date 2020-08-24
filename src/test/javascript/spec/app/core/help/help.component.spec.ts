import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import Help from '@/core/help/help.vue';
import HelpClass from '@/core/home/home.component';
import * as config from '@/shared/config/config';

const localVue = createLocalVue();
config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const i18n = config.initI18N(localVue);
localVue.component('router-link', {});

describe('Help', () => {
  let help: HelpClass;
  let wrapper: Wrapper<HelpClass>;
  const loginService = { login: jest.fn(), logout: jest.fn() };

  beforeEach(() => {
    wrapper = shallowMount<HelpClass>(HelpClass, {
      i18n,
      store,
      localVue,
      provide: {
        loginService: () => loginService
      }
    });
    help = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  it('should not have user data set', () => {
    expect(help.authenticated).toBeFalsy();
    expect(help.username).toBe('');
  });

  it('should have user data set after authentication', () => {
    store.commit('authenticated', { login: 'test' });

    expect(help.authenticated).toBeTruthy();
    expect(help.username).toBe('test');
  });

  it('should use login service', () => {
    help.openLogin();

    expect(loginService.login).toHaveBeenCalled();
  });
});
