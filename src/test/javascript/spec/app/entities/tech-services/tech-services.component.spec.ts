/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechServicesComponent from '@/entities/tech-services/tech-services.vue';
import TechServicesClass from '@/entities/tech-services/tech-services.component';
import TechServicesService from '@/entities/tech-services/tech-services.service';

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
  describe('TechServices Management Component', () => {
    let wrapper: Wrapper<TechServicesClass>;
    let comp: TechServicesClass;
    let techServicesServiceStub: SinonStubbedInstance<TechServicesService>;

    beforeEach(() => {
      techServicesServiceStub = sinon.createStubInstance<TechServicesService>(TechServicesService);
      techServicesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TechServicesClass>(TechServicesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          techServicesService: () => techServicesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      techServicesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTechServicess();
      await comp.$nextTick();

      // THEN
      expect(techServicesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.techServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      techServicesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTechServices();
      await comp.$nextTick();

      // THEN
      expect(techServicesServiceStub.delete.called).toBeTruthy();
      expect(techServicesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
