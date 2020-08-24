/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechToolsUpdateComponent from '@/entities/tech-tools/tech-tools-update.vue';
import TechToolsClass from '@/entities/tech-tools/tech-tools-update.component';
import TechToolsService from '@/entities/tech-tools/tech-tools.service';

import ListToolsService from '@/entities/list-tools/list-tools.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TechTools Management Update Component', () => {
    let wrapper: Wrapper<TechToolsClass>;
    let comp: TechToolsClass;
    let techToolsServiceStub: SinonStubbedInstance<TechToolsService>;

    beforeEach(() => {
      techToolsServiceStub = sinon.createStubInstance<TechToolsService>(TechToolsService);

      wrapper = shallowMount<TechToolsClass>(TechToolsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          techToolsService: () => techToolsServiceStub,

          listToolsService: () => new ListToolsService(),

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.techTools = entity;
        techToolsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techToolsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.techTools = entity;
        techToolsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techToolsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
