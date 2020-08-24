import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import Terms from '@/core/terms/terms.vue';
import TermsClass from '@/core/terms/terms.component';
import * as config from '@/shared/config/config';

const localVue = createLocalVue();
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
localVue.component('router-link', {});

describe('Privacy', () => {
  let terms: TermsClass;
  let wrapper: Wrapper<TermsClass>;

  beforeEach(() => {
    wrapper = shallowMount<TermsClass>(TermsClass, {
      i18n
    });
    terms = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });
});
