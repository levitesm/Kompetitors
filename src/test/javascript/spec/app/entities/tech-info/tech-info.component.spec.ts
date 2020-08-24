/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechInfoComponent from '@/entities/tech-info/tech-info.vue';
import TechInfoClass from '@/entities/tech-info/tech-info.component';
import TechInfoService from '@/entities/tech-info/tech-info.service';

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
  describe('TechInfo Management Component', () => {
    let wrapper: Wrapper<TechInfoClass>;
    let comp: TechInfoClass;
    let techInfoServiceStub: SinonStubbedInstance<TechInfoService>;

    beforeEach(() => {
      techInfoServiceStub = sinon.createStubInstance<TechInfoService>(TechInfoService);
      techInfoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TechInfoClass>(TechInfoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          techInfoService: () => techInfoServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      techInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTechInfos();
      await comp.$nextTick();

      // THEN
      expect(techInfoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.techInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      techInfoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTechInfo();
      await comp.$nextTick();

      // THEN
      expect(techInfoServiceStub.delete.called).toBeTruthy();
      expect(techInfoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
