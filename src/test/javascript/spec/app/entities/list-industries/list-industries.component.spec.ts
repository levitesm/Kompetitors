/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListIndustriesComponent from '@/entities/list-industries/list-industries.vue';
import ListIndustriesClass from '@/entities/list-industries/list-industries.component';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';

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
  describe('ListIndustries Management Component', () => {
    let wrapper: Wrapper<ListIndustriesClass>;
    let comp: ListIndustriesClass;
    let listIndustriesServiceStub: SinonStubbedInstance<ListIndustriesService>;

    beforeEach(() => {
      listIndustriesServiceStub = sinon.createStubInstance<ListIndustriesService>(ListIndustriesService);
      listIndustriesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListIndustriesClass>(ListIndustriesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listIndustriesService: () => listIndustriesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listIndustriesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListIndustriess();
      await comp.$nextTick();

      // THEN
      expect(listIndustriesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listIndustries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listIndustriesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListIndustries();
      await comp.$nextTick();

      // THEN
      expect(listIndustriesServiceStub.delete.called).toBeTruthy();
      expect(listIndustriesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
