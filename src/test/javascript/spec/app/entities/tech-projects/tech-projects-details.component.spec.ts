/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TechProjectsDetailComponent from '@/entities/tech-projects/tech-projects-details.vue';
import TechProjectsClass from '@/entities/tech-projects/tech-projects-details.component';
import TechProjectsService from '@/entities/tech-projects/tech-projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TechProjects Management Detail Component', () => {
    let wrapper: Wrapper<TechProjectsClass>;
    let comp: TechProjectsClass;
    let techProjectsServiceStub: SinonStubbedInstance<TechProjectsService>;

    beforeEach(() => {
      techProjectsServiceStub = sinon.createStubInstance<TechProjectsService>(TechProjectsService);

      wrapper = shallowMount<TechProjectsClass>(TechProjectsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { techProjectsService: () => techProjectsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTechProjects = { id: 123 };
        techProjectsServiceStub.find.resolves(foundTechProjects);

        // WHEN
        comp.retrieveTechProjects(123);
        await comp.$nextTick();

        // THEN
        expect(comp.techProjects).toBe(foundTechProjects);
      });
    });
  });
});
