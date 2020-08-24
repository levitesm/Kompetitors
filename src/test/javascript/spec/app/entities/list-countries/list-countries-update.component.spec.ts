/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCountriesUpdateComponent from '@/entities/list-countries/list-countries-update.vue';
import ListCountriesClass from '@/entities/list-countries/list-countries-update.component';
import ListCountriesService from '@/entities/list-countries/list-countries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListCountries Management Update Component', () => {
    let wrapper: Wrapper<ListCountriesClass>;
    let comp: ListCountriesClass;
    let listCountriesServiceStub: SinonStubbedInstance<ListCountriesService>;

    beforeEach(() => {
      listCountriesServiceStub = sinon.createStubInstance<ListCountriesService>(ListCountriesService);

      wrapper = shallowMount<ListCountriesClass>(ListCountriesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listCountriesService: () => listCountriesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listCountries = entity;
        listCountriesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCountriesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listCountries = entity;
        listCountriesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCountriesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
