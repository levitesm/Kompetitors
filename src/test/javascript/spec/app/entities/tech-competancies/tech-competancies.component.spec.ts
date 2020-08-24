/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechCompetanciesComponent from '@/entities/tech-competancies/tech-competancies.vue';
import TechCompetanciesClass from '@/entities/tech-competancies/tech-competancies.component';
import TechCompetanciesService from '@/entities/tech-competancies/tech-competancies.service';

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
  describe('TechCompetancies Management Component', () => {
    let wrapper: Wrapper<TechCompetanciesClass>;
    let comp: TechCompetanciesClass;
    let techCompetanciesServiceStub: SinonStubbedInstance<TechCompetanciesService>;

    beforeEach(() => {
      techCompetanciesServiceStub = sinon.createStubInstance<TechCompetanciesService>(TechCompetanciesService);
      techCompetanciesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TechCompetanciesClass>(TechCompetanciesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          techCompetanciesService: () => techCompetanciesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      techCompetanciesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTechCompetanciess();
      await comp.$nextTick();

      // THEN
      expect(techCompetanciesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.techCompetancies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      techCompetanciesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTechCompetancies();
      await comp.$nextTick();

      // THEN
      expect(techCompetanciesServiceStub.delete.called).toBeTruthy();
      expect(techCompetanciesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
