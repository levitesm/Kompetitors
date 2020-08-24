/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListClientsProjectTypesComponent from '@/entities/list-clients-project-types/list-clients-project-types.vue';
import ListClientsProjectTypesClass from '@/entities/list-clients-project-types/list-clients-project-types.component';
import ListClientsProjectTypesService from '@/entities/list-clients-project-types/list-clients-project-types.service';

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
  describe('ListClientsProjectTypes Management Component', () => {
    let wrapper: Wrapper<ListClientsProjectTypesClass>;
    let comp: ListClientsProjectTypesClass;
    let listClientsProjectTypesServiceStub: SinonStubbedInstance<ListClientsProjectTypesService>;

    beforeEach(() => {
      listClientsProjectTypesServiceStub = sinon.createStubInstance<ListClientsProjectTypesService>(ListClientsProjectTypesService);
      listClientsProjectTypesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListClientsProjectTypesClass>(ListClientsProjectTypesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listClientsProjectTypesService: () => listClientsProjectTypesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listClientsProjectTypesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListClientsProjectTypess();
      await comp.$nextTick();

      // THEN
      expect(listClientsProjectTypesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listClientsProjectTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listClientsProjectTypesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListClientsProjectTypes();
      await comp.$nextTick();

      // THEN
      expect(listClientsProjectTypesServiceStub.delete.called).toBeTruthy();
      expect(listClientsProjectTypesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
