/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListToolsComponent from '@/entities/list-tools/list-tools.vue';
import ListToolsClass from '@/entities/list-tools/list-tools.component';
import ListToolsService from '@/entities/list-tools/list-tools.service';

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
  describe('ListTools Management Component', () => {
    let wrapper: Wrapper<ListToolsClass>;
    let comp: ListToolsClass;
    let listToolsServiceStub: SinonStubbedInstance<ListToolsService>;

    beforeEach(() => {
      listToolsServiceStub = sinon.createStubInstance<ListToolsService>(ListToolsService);
      listToolsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListToolsClass>(ListToolsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listToolsService: () => listToolsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listToolsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListToolss();
      await comp.$nextTick();

      // THEN
      expect(listToolsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listTools[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listToolsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListTools();
      await comp.$nextTick();

      // THEN
      expect(listToolsServiceStub.delete.called).toBeTruthy();
      expect(listToolsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
