/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCityCountriesUpdateComponent from '@/entities/list-city-countries/list-city-countries-update.vue';
import ListCityCountriesClass from '@/entities/list-city-countries/list-city-countries-update.component';
import ListCityCountriesService from '@/entities/list-city-countries/list-city-countries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListCityCountries Management Update Component', () => {
    let wrapper: Wrapper<ListCityCountriesClass>;
    let comp: ListCityCountriesClass;
    let listCityCountriesServiceStub: SinonStubbedInstance<ListCityCountriesService>;

    beforeEach(() => {
      listCityCountriesServiceStub = sinon.createStubInstance<ListCityCountriesService>(ListCityCountriesService);

      wrapper = shallowMount<ListCityCountriesClass>(ListCityCountriesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listCityCountriesService: () => listCityCountriesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listCityCountries = entity;
        listCityCountriesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCityCountriesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listCityCountries = entity;
        listCityCountriesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCityCountriesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
