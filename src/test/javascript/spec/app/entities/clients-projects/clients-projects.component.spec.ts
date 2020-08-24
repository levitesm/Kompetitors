/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ClientsProjectsComponent from '@/entities/clients-projects/clients-projects.vue';
import ClientsProjectsClass from '@/entities/clients-projects/clients-projects.component';
import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';

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
  describe('ClientsProjects Management Component', () => {
    let wrapper: Wrapper<ClientsProjectsClass>;
    let comp: ClientsProjectsClass;
    let clientsProjectsServiceStub: SinonStubbedInstance<ClientsProjectsService>;

    beforeEach(() => {
      clientsProjectsServiceStub = sinon.createStubInstance<ClientsProjectsService>(ClientsProjectsService);
      clientsProjectsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ClientsProjectsClass>(ClientsProjectsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          clientsProjectsService: () => clientsProjectsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      clientsProjectsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllClientsProjectss();
      await comp.$nextTick();

      // THEN
      expect(clientsProjectsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.clientsProjects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      clientsProjectsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeClientsProjects();
      await comp.$nextTick();

      // THEN
      expect(clientsProjectsServiceStub.delete.called).toBeTruthy();
      expect(clientsProjectsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
