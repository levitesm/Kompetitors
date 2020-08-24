/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListClientsProjectTypesUpdateComponent from '@/entities/list-clients-project-types/list-clients-project-types-update.vue';
import ListClientsProjectTypesClass from '@/entities/list-clients-project-types/list-clients-project-types-update.component';
import ListClientsProjectTypesService from '@/entities/list-clients-project-types/list-clients-project-types.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListClientsProjectTypes Management Update Component', () => {
    let wrapper: Wrapper<ListClientsProjectTypesClass>;
    let comp: ListClientsProjectTypesClass;
    let listClientsProjectTypesServiceStub: SinonStubbedInstance<ListClientsProjectTypesService>;

    beforeEach(() => {
      listClientsProjectTypesServiceStub = sinon.createStubInstance<ListClientsProjectTypesService>(ListClientsProjectTypesService);

      wrapper = shallowMount<ListClientsProjectTypesClass>(ListClientsProjectTypesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listClientsProjectTypesService: () => listClientsProjectTypesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listClientsProjectTypes = entity;
        listClientsProjectTypesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listClientsProjectTypesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listClientsProjectTypes = entity;
        listClientsProjectTypesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listClientsProjectTypesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
