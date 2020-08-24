/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCompetanciesUpdateComponent from '@/entities/list-competancies/list-competancies-update.vue';
import ListCompetanciesClass from '@/entities/list-competancies/list-competancies-update.component';
import ListCompetanciesService from '@/entities/list-competancies/list-competancies.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListCompetancies Management Update Component', () => {
    let wrapper: Wrapper<ListCompetanciesClass>;
    let comp: ListCompetanciesClass;
    let listCompetanciesServiceStub: SinonStubbedInstance<ListCompetanciesService>;

    beforeEach(() => {
      listCompetanciesServiceStub = sinon.createStubInstance<ListCompetanciesService>(ListCompetanciesService);

      wrapper = shallowMount<ListCompetanciesClass>(ListCompetanciesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listCompetanciesService: () => listCompetanciesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listCompetancies = entity;
        listCompetanciesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCompetanciesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listCompetancies = entity;
        listCompetanciesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listCompetanciesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
