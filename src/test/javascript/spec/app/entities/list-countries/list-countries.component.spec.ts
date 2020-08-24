/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCountriesComponent from '@/entities/list-countries/list-countries.vue';
import ListCountriesClass from '@/entities/list-countries/list-countries.component';
import ListCountriesService from '@/entities/list-countries/list-countries.service';

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
  describe('ListCountries Management Component', () => {
    let wrapper: Wrapper<ListCountriesClass>;
    let comp: ListCountriesClass;
    let listCountriesServiceStub: SinonStubbedInstance<ListCountriesService>;

    beforeEach(() => {
      listCountriesServiceStub = sinon.createStubInstance<ListCountriesService>(ListCountriesService);
      listCountriesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListCountriesClass>(ListCountriesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listCountriesService: () => listCountriesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listCountriesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListCountriess();
      await comp.$nextTick();

      // THEN
      expect(listCountriesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listCountries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listCountriesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListCountries();
      await comp.$nextTick();

      // THEN
      expect(listCountriesServiceStub.delete.called).toBeTruthy();
      expect(listCountriesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
