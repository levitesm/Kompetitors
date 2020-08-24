import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import Security from '@/core/security/security.vue';
import SecurityClass from '@/core/security/security.component';
import * as config from '@/shared/config/config';

const localVue = createLocalVue();
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
localVue.component('router-link', {});

describe('Security', () => {
  let security: SecurityClass;
  let wrapper: Wrapper<SecurityClass>;

  beforeEach(() => {
    wrapper = shallowMount<SecurityClass>(SecurityClass, {
      i18n
    });
    security = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });
});
