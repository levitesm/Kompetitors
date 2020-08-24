/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LegalDetailComponent from '@/entities/legal/legal-details.vue';
import LegalClass from '@/entities/legal/legal-details.component';
import LegalService from '@/entities/legal/legal.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Legal Management Detail Component', () => {
    let wrapper: Wrapper<LegalClass>;
    let comp: LegalClass;
    let legalServiceStub: SinonStubbedInstance<LegalService>;

    beforeEach(() => {
      legalServiceStub = sinon.createStubInstance<LegalService>(LegalService);

      wrapper = shallowMount<LegalClass>(LegalDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { legalService: () => legalServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLegal = { id: 123 };
        legalServiceStub.find.resolves(foundLegal);

        // WHEN
        comp.retrieveLegal(123);
        await comp.$nextTick();

        // THEN
        expect(comp.legal).toBe(foundLegal);
      });
    });
  });
});
