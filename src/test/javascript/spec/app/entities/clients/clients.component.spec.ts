/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ClientsComponent from '@/entities/clients/clients.vue';
import ClientsClass from '@/entities/clients/clients.component';
import ClientsService from '@/entities/clients/clients.service';

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
  describe('Clients Management Component', () => {
    let wrapper: Wrapper<ClientsClass>;
    let comp: ClientsClass;
    let clientsServiceStub: SinonStubbedInstance<ClientsService>;

    beforeEach(() => {
      clientsServiceStub = sinon.createStubInstance<ClientsService>(ClientsService);
      clientsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ClientsClass>(ClientsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          clientsService: () => clientsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      clientsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllClientss();
      await comp.$nextTick();

      // THEN
      expect(clientsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.clients[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      clientsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeClients();
      await comp.$nextTick();

      // THEN
      expect(clientsServiceStub.delete.called).toBeTruthy();
      expect(clientsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
