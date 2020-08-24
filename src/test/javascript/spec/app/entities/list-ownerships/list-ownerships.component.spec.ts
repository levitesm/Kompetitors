/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListOwnershipsComponent from '@/entities/list-ownerships/list-ownerships.vue';
import ListOwnershipsClass from '@/entities/list-ownerships/list-ownerships.component';
import ListOwnershipsService from '@/entities/list-ownerships/list-ownerships.service';

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
  describe('ListOwnerships Management Component', () => {
    let wrapper: Wrapper<ListOwnershipsClass>;
    let comp: ListOwnershipsClass;
    let listOwnershipsServiceStub: SinonStubbedInstance<ListOwnershipsService>;

    beforeEach(() => {
      listOwnershipsServiceStub = sinon.createStubInstance<ListOwnershipsService>(ListOwnershipsService);
      listOwnershipsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListOwnershipsClass>(ListOwnershipsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listOwnershipsService: () => listOwnershipsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listOwnershipsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListOwnershipss();
      await comp.$nextTick();

      // THEN
      expect(listOwnershipsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listOwnerships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listOwnershipsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListOwnerships();
      await comp.$nextTick();

      // THEN
      expect(listOwnershipsServiceStub.delete.called).toBeTruthy();
      expect(listOwnershipsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
