/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListOwnershipsUpdateComponent from '@/entities/list-ownerships/list-ownerships-update.vue';
import ListOwnershipsClass from '@/entities/list-ownerships/list-ownerships-update.component';
import ListOwnershipsService from '@/entities/list-ownerships/list-ownerships.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListOwnerships Management Update Component', () => {
    let wrapper: Wrapper<ListOwnershipsClass>;
    let comp: ListOwnershipsClass;
    let listOwnershipsServiceStub: SinonStubbedInstance<ListOwnershipsService>;

    beforeEach(() => {
      listOwnershipsServiceStub = sinon.createStubInstance<ListOwnershipsService>(ListOwnershipsService);

      wrapper = shallowMount<ListOwnershipsClass>(ListOwnershipsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listOwnershipsService: () => listOwnershipsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listOwnerships = entity;
        listOwnershipsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listOwnershipsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listOwnerships = entity;
        listOwnershipsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listOwnershipsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
