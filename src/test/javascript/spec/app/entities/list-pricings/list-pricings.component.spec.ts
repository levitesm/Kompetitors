/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListPricingsComponent from '@/entities/list-pricings/list-pricings.vue';
import ListPricingsClass from '@/entities/list-pricings/list-pricings.component';
import ListPricingsService from '@/entities/list-pricings/list-pricings.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('ListPricings Management Component', () => {
    let wrapper: Wrapper<ListPricingsClass>;
    let comp: ListPricingsClass;
    let listPricingsServiceStub: SinonStubbedInstance<ListPricingsService>;

    beforeEach(() => {
      listPricingsServiceStub = sinon.createStubInstance<ListPricingsService>(ListPricingsService);
      listPricingsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListPricingsClass>(ListPricingsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listPricingsService: () => listPricingsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listPricingsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListPricingss();
      await comp.$nextTick();

      // THEN
      expect(listPricingsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listPricings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listPricingsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListPricings();
      await comp.$nextTick();

      // THEN
      expect(listPricingsServiceStub.delete.called).toBeTruthy();
      expect(listPricingsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
