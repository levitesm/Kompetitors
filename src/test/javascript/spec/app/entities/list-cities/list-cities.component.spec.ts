/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCitiesComponent from '@/entities/list-cities/list-cities.vue';
import ListCitiesClass from '@/entities/list-cities/list-cities.component';
import ListCitiesService from '@/entities/list-cities/list-cities.service';

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
  describe('ListCities Management Component', () => {
    let wrapper: Wrapper<ListCitiesClass>;
    let comp: ListCitiesClass;
    let listCitiesServiceStub: SinonStubbedInstance<ListCitiesService>;

    beforeEach(() => {
      listCitiesServiceStub = sinon.createStubInstance<ListCitiesService>(ListCitiesService);
      listCitiesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListCitiesClass>(ListCitiesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listCitiesService: () => listCitiesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listCitiesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListCitiess();
      await comp.$nextTick();

      // THEN
      expect(listCitiesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listCities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listCitiesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListCities();
      await comp.$nextTick();

      // THEN
      expect(listCitiesServiceStub.delete.called).toBeTruthy();
      expect(listCitiesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
