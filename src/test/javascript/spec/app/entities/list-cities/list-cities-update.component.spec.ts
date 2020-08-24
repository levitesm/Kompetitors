/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCitiesUpdateComponent from '@/entities/list-cities/list-cities-update.vue';
import ListCitiesClass from '@/entities/list-cities/list-cities-update.component';
import ListCitiesService from '@/entities/list-cities/list-cities.service';

import ListCityCountriesService from '@/entities/list-city-countries/list-city-countries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListCities Management Update Component', () => {
    let wrapper: Wrapper<ListCitiesClass>;
    let comp: ListCitiesClass;
    let listCitiesServiceStub: SinonStubbedInstance<ListCitiesService>;

    beforeEach(() => {
      listCitiesServiceStub = sinon.createStubInstance<ListCitiesService>(ListCitiesService);

      wrapper = shallowMount<ListCitiesClass>(ListCitiesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listCitiesService: () => listCitiesServiceStub,

          listCityCountriesService: () => new ListCityCountriesService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listCities = entity;
        listCitiesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCitiesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listCities = entity;
        listCitiesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCitiesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
