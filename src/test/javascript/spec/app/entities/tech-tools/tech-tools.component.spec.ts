/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechToolsComponent from '@/entities/tech-tools/tech-tools.vue';
import TechToolsClass from '@/entities/tech-tools/tech-tools.component';
import TechToolsService from '@/entities/tech-tools/tech-tools.service';

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
  describe('TechTools Management Component', () => {
    let wrapper: Wrapper<TechToolsClass>;
    let comp: TechToolsClass;
    let techToolsServiceStub: SinonStubbedInstance<TechToolsService>;

    beforeEach(() => {
      techToolsServiceStub = sinon.createStubInstance<TechToolsService>(TechToolsService);
      techToolsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TechToolsClass>(TechToolsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          techToolsService: () => techToolsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      techToolsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTechToolss();
      await comp.$nextTick();

      // THEN
      expect(techToolsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.techTools[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      techToolsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTechTools();
      await comp.$nextTick();

      // THEN
      expect(techToolsServiceStub.delete.called).toBeTruthy();
      expect(techToolsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
