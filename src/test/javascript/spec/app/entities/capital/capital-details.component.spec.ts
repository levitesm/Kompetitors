/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CapitalDetailComponent from '@/entities/capital/capital-details.vue';
import CapitalClass from '@/entities/capital/capital-details.component';
import CapitalService from '@/entities/capital/capital.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Capital Management Detail Component', () => {
    let wrapper: Wrapper<CapitalClass>;
    let comp: CapitalClass;
    let capitalServiceStub: SinonStubbedInstance<CapitalService>;

    beforeEach(() => {
      capitalServiceStub = sinon.createStubInstance<CapitalService>(CapitalService);

      wrapper = shallowMount<CapitalClass>(CapitalDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { capitalService: () => capitalServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCapital = { id: 123 };
        capitalServiceStub.find.resolves(foundCapital);

        // WHEN
        comp.retrieveCapital(123);
        await comp.$nextTick();

        // THEN
        expect(comp.capital).toBe(foundCapital);
      });
    });
  });
});
