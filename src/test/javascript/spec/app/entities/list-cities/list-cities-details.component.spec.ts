/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListCitiesDetailComponent from '@/entities/list-cities/list-cities-details.vue';
import ListCitiesClass from '@/entities/list-cities/list-cities-details.component';
import ListCitiesService from '@/entities/list-cities/list-cities.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListCities Management Detail Component', () => {
    let wrapper: Wrapper<ListCitiesClass>;
    let comp: ListCitiesClass;
    let listCitiesServiceStub: SinonStubbedInstance<ListCitiesService>;

    beforeEach(() => {
      listCitiesServiceStub = sinon.createStubInstance<ListCitiesService>(ListCitiesService);

      wrapper = shallowMount<ListCitiesClass>(ListCitiesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listCitiesService: () => listCitiesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListCities = { id: 123 };
        listCitiesServiceStub.find.resolves(foundListCities);

        // WHEN
        comp.retrieveListCities(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listCities).toBe(foundListCities);
      });
    });
  });
});
