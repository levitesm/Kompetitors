/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListPricingsUpdateComponent from '@/entities/list-pricings/list-pricings-update.vue';
import ListPricingsClass from '@/entities/list-pricings/list-pricings-update.component';
import ListPricingsService from '@/entities/list-pricings/list-pricings.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListPricings Management Update Component', () => {
    let wrapper: Wrapper<ListPricingsClass>;
    let comp: ListPricingsClass;
    let listPricingsServiceStub: SinonStubbedInstance<ListPricingsService>;

    beforeEach(() => {
      listPricingsServiceStub = sinon.createStubInstance<ListPricingsService>(ListPricingsService);

      wrapper = shallowMount<ListPricingsClass>(ListPricingsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listPricingsService: () => listPricingsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listPricings = entity;
        listPricingsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listPricingsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listPricings = entity;
        listPricingsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listPricingsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
