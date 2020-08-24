/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RepresentativesComponent from '@/entities/representatives/representatives.vue';
import RepresentativesClass from '@/entities/representatives/representatives.component';
import RepresentativesService from '@/entities/representatives/representatives.service';

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
  describe('Representatives Management Component', () => {
    let wrapper: Wrapper<RepresentativesClass>;
    let comp: RepresentativesClass;
    let representativesServiceStub: SinonStubbedInstance<RepresentativesService>;

    beforeEach(() => {
      representativesServiceStub = sinon.createStubInstance<RepresentativesService>(RepresentativesService);
      representativesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RepresentativesClass>(RepresentativesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          representativesService: () => representativesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      representativesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRepresentativess();
      await comp.$nextTick();

      // THEN
      expect(representativesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.representatives[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      representativesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRepresentatives();
      await comp.$nextTick();

      // THEN
      expect(representativesServiceStub.delete.called).toBeTruthy();
      expect(representativesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
