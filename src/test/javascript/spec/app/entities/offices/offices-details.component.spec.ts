/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OfficesDetailComponent from '@/entities/offices/offices-details.vue';
import OfficesClass from '@/entities/offices/offices-details.component';
import OfficesService from '@/entities/offices/offices.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Offices Management Detail Component', () => {
    let wrapper: Wrapper<OfficesClass>;
    let comp: OfficesClass;
    let officesServiceStub: SinonStubbedInstance<OfficesService>;

    beforeEach(() => {
      officesServiceStub = sinon.createStubInstance<OfficesService>(OfficesService);

      wrapper = shallowMount<OfficesClass>(OfficesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { officesService: () => officesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOffices = { id: 123 };
        officesServiceStub.find.resolves(foundOffices);

        // WHEN
        comp.retrieveOffices(123);
        await comp.$nextTick();

        // THEN
        expect(comp.offices).toBe(foundOffices);
      });
    });
  });
});
