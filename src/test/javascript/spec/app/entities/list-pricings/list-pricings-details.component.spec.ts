/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListPricingsDetailComponent from '@/entities/list-pricings/list-pricings-details.vue';
import ListPricingsClass from '@/entities/list-pricings/list-pricings-details.component';
import ListPricingsService from '@/entities/list-pricings/list-pricings.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListPricings Management Detail Component', () => {
    let wrapper: Wrapper<ListPricingsClass>;
    let comp: ListPricingsClass;
    let listPricingsServiceStub: SinonStubbedInstance<ListPricingsService>;

    beforeEach(() => {
      listPricingsServiceStub = sinon.createStubInstance<ListPricingsService>(ListPricingsService);

      wrapper = shallowMount<ListPricingsClass>(ListPricingsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listPricingsService: () => listPricingsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListPricings = { id: 123 };
        listPricingsServiceStub.find.resolves(foundListPricings);

        // WHEN
        comp.retrieveListPricings(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listPricings).toBe(foundListPricings);
      });
    });
  });
});
