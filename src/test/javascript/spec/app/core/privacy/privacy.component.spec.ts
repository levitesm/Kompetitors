import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import Privacy from '@/core/privacy/privacy.vue';
import PrivacyClass from '@/core/privacy/privacy.component';
import * as config from '@/shared/config/config';

const localVue = createLocalVue();
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
localVue.component('router-link', {});

describe('Privacy', () => {
  let privacy: PrivacyClass;
  let wrapper: Wrapper<PrivacyClass>;

  beforeEach(() => {
    wrapper = shallowMount<PrivacyClass>(PrivacyClass, {
      i18n
    });
    privacy = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });
});
