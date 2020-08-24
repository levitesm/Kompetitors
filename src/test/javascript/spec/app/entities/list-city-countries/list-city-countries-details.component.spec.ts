/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListCityCountriesDetailComponent from '@/entities/list-city-countries/list-city-countries-details.vue';
import ListCityCountriesClass from '@/entities/list-city-countries/list-city-countries-details.component';
import ListCityCountriesService from '@/entities/list-city-countries/list-city-countries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListCityCountries Management Detail Component', () => {
    let wrapper: Wrapper<ListCityCountriesClass>;
    let comp: ListCityCountriesClass;
    let listCityCountriesServiceStub: SinonStubbedInstance<ListCityCountriesService>;

    beforeEach(() => {
      listCityCountriesServiceStub = sinon.createStubInstance<ListCityCountriesService>(ListCityCountriesService);

      wrapper = shallowMount<ListCityCountriesClass>(ListCityCountriesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listCityCountriesService: () => listCityCountriesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListCityCountries = { id: 123 };
        listCityCountriesServiceStub.find.resolves(foundListCityCountries);

        // WHEN
        comp.retrieveListCityCountries(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listCityCountries).toBe(foundListCityCountries);
      });
    });
  });
});
