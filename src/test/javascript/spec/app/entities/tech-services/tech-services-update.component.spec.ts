/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechServicesUpdateComponent from '@/entities/tech-services/tech-services-update.vue';
import TechServicesClass from '@/entities/tech-services/tech-services-update.component';
import TechServicesService from '@/entities/tech-services/tech-services.service';

import ListServicesService from '@/entities/list-services/list-services.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TechServices Management Update Component', () => {
    let wrapper: Wrapper<TechServicesClass>;
    let comp: TechServicesClass;
    let techServicesServiceStub: SinonStubbedInstance<TechServicesService>;

    beforeEach(() => {
      techServicesServiceStub = sinon.createStubInstance<TechServicesService>(TechServicesService);

      wrapper = shallowMount<TechServicesClass>(TechServicesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          techServicesService: () => techServicesServiceStub,

          listServicesService: () => new ListServicesService(),

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.techServices = entity;
        techServicesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techServicesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.techServices = entity;
        techServicesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techServicesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
