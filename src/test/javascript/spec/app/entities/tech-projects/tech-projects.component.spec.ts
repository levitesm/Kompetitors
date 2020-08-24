/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechProjectsComponent from '@/entities/tech-projects/tech-projects.vue';
import TechProjectsClass from '@/entities/tech-projects/tech-projects.component';
import TechProjectsService from '@/entities/tech-projects/tech-projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('TechProjects Management Component', () => {
    let wrapper: Wrapper<TechProjectsClass>;
    let comp: TechProjectsClass;
    let techProjectsServiceStub: SinonStubbedInstance<TechProjectsService>;

    beforeEach(() => {
      techProjectsServiceStub = sinon.createStubInstance<TechProjectsService>(TechProjectsService);
      techProjectsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TechProjectsClass>(TechProjectsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          techProjectsService: () => techProjectsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      techProjectsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTechProjectss();
      await comp.$nextTick();

      // THEN
      expect(techProjectsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.techProjects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      techProjectsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTechProjects();
      await comp.$nextTick();

      // THEN
      expect(techProjectsServiceStub.delete.called).toBeTruthy();
      expect(techProjectsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
