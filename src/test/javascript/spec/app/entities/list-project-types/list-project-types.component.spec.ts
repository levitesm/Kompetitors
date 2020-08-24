/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListProjectTypesComponent from '@/entities/list-project-types/list-project-types.vue';
import ListProjectTypesClass from '@/entities/list-project-types/list-project-types.component';
import ListProjectTypesService from '@/entities/list-project-types/list-project-types.service';

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
  describe('ListProjectTypes Management Component', () => {
    let wrapper: Wrapper<ListProjectTypesClass>;
    let comp: ListProjectTypesClass;
    let listProjectTypesServiceStub: SinonStubbedInstance<ListProjectTypesService>;

    beforeEach(() => {
      listProjectTypesServiceStub = sinon.createStubInstance<ListProjectTypesService>(ListProjectTypesService);
      listProjectTypesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListProjectTypesClass>(ListProjectTypesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listProjectTypesService: () => listProjectTypesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listProjectTypesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListProjectTypess();
      await comp.$nextTick();

      // THEN
      expect(listProjectTypesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listProjectTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listProjectTypesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListProjectTypes();
      await comp.$nextTick();

      // THEN
      expect(listProjectTypesServiceStub.delete.called).toBeTruthy();
      expect(listProjectTypesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
