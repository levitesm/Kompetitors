/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListIndustriesDetailComponent from '@/entities/list-industries/list-industries-details.vue';
import ListIndustriesClass from '@/entities/list-industries/list-industries-details.component';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListIndustries Management Detail Component', () => {
    let wrapper: Wrapper<ListIndustriesClass>;
    let comp: ListIndustriesClass;
    let listIndustriesServiceStub: SinonStubbedInstance<ListIndustriesService>;

    beforeEach(() => {
      listIndustriesServiceStub = sinon.createStubInstance<ListIndustriesService>(ListIndustriesService);

      wrapper = shallowMount<ListIndustriesClass>(ListIndustriesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listIndustriesService: () => listIndustriesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListIndustries = { id: 123 };
        listIndustriesServiceStub.find.resolves(foundListIndustries);

        // WHEN
        comp.retrieveListIndustries(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listIndustries).toBe(foundListIndustries);
      });
    });
  });
});
