/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListIndustriesUpdateComponent from '@/entities/list-industries/list-industries-update.vue';
import ListIndustriesClass from '@/entities/list-industries/list-industries-update.component';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListIndustries Management Update Component', () => {
    let wrapper: Wrapper<ListIndustriesClass>;
    let comp: ListIndustriesClass;
    let listIndustriesServiceStub: SinonStubbedInstance<ListIndustriesService>;

    beforeEach(() => {
      listIndustriesServiceStub = sinon.createStubInstance<ListIndustriesService>(ListIndustriesService);

      wrapper = shallowMount<ListIndustriesClass>(ListIndustriesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listIndustriesService: () => listIndustriesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listIndustries = entity;
        listIndustriesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listIndustriesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listIndustries = entity;
        listIndustriesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listIndustriesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
