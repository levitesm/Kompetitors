/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCityCountriesComponent from '@/entities/list-city-countries/list-city-countries.vue';
import ListCityCountriesClass from '@/entities/list-city-countries/list-city-countries.component';
import ListCityCountriesService from '@/entities/list-city-countries/list-city-countries.service';

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
  describe('ListCityCountries Management Component', () => {
    let wrapper: Wrapper<ListCityCountriesClass>;
    let comp: ListCityCountriesClass;
    let listCityCountriesServiceStub: SinonStubbedInstance<ListCityCountriesService>;

    beforeEach(() => {
      listCityCountriesServiceStub = sinon.createStubInstance<ListCityCountriesService>(ListCityCountriesService);
      listCityCountriesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListCityCountriesClass>(ListCityCountriesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listCityCountriesService: () => listCityCountriesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listCityCountriesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListCityCountriess();
      await comp.$nextTick();

      // THEN
      expect(listCityCountriesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listCityCountries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listCityCountriesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListCityCountries();
      await comp.$nextTick();

      // THEN
      expect(listCityCountriesServiceStub.delete.called).toBeTruthy();
      expect(listCityCountriesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
