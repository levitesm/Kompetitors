/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListCountriesDetailComponent from '@/entities/list-countries/list-countries-details.vue';
import ListCountriesClass from '@/entities/list-countries/list-countries-details.component';
import ListCountriesService from '@/entities/list-countries/list-countries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListCountries Management Detail Component', () => {
    let wrapper: Wrapper<ListCountriesClass>;
    let comp: ListCountriesClass;
    let listCountriesServiceStub: SinonStubbedInstance<ListCountriesService>;

    beforeEach(() => {
      listCountriesServiceStub = sinon.createStubInstance<ListCountriesService>(ListCountriesService);

      wrapper = shallowMount<ListCountriesClass>(ListCountriesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listCountriesService: () => listCountriesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListCountries = { id: 123 };
        listCountriesServiceStub.find.resolves(foundListCountries);

        // WHEN
        comp.retrieveListCountries(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listCountries).toBe(foundListCountries);
      });
    });
  });
});
