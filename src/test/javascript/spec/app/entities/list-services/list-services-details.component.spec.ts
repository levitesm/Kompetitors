/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListServicesDetailComponent from '@/entities/list-services/list-services-details.vue';
import ListServicesClass from '@/entities/list-services/list-services-details.component';
import ListServicesService from '@/entities/list-services/list-services.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListServices Management Detail Component', () => {
    let wrapper: Wrapper<ListServicesClass>;
    let comp: ListServicesClass;
    let listServicesServiceStub: SinonStubbedInstance<ListServicesService>;

    beforeEach(() => {
      listServicesServiceStub = sinon.createStubInstance<ListServicesService>(ListServicesService);

      wrapper = shallowMount<ListServicesClass>(ListServicesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listServicesService: () => listServicesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListServices = { id: 123 };
        listServicesServiceStub.find.resolves(foundListServices);

        // WHEN
        comp.retrieveListServices(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listServices).toBe(foundListServices);
      });
    });
  });
});
