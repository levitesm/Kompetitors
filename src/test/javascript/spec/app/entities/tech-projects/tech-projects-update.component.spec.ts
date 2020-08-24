/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechProjectsUpdateComponent from '@/entities/tech-projects/tech-projects-update.vue';
import TechProjectsClass from '@/entities/tech-projects/tech-projects-update.component';
import TechProjectsService from '@/entities/tech-projects/tech-projects.service';

import ListProjectTypesService from '@/entities/list-project-types/list-project-types.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TechProjects Management Update Component', () => {
    let wrapper: Wrapper<TechProjectsClass>;
    let comp: TechProjectsClass;
    let techProjectsServiceStub: SinonStubbedInstance<TechProjectsService>;

    beforeEach(() => {
      techProjectsServiceStub = sinon.createStubInstance<TechProjectsService>(TechProjectsService);

      wrapper = shallowMount<TechProjectsClass>(TechProjectsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          techProjectsService: () => techProjectsServiceStub,

          listProjectTypesService: () => new ListProjectTypesService(),

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.techProjects = entity;
        techProjectsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techProjectsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.techProjects = entity;
        techProjectsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techProjectsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
