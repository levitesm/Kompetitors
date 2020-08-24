/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LegalComponent from '@/entities/legal/legal.vue';
import LegalClass from '@/entities/legal/legal.component';
import LegalService from '@/entities/legal/legal.service';

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
  describe('Legal Management Component', () => {
    let wrapper: Wrapper<LegalClass>;
    let comp: LegalClass;
    let legalServiceStub: SinonStubbedInstance<LegalService>;

    beforeEach(() => {
      legalServiceStub = sinon.createStubInstance<LegalService>(LegalService);
      legalServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LegalClass>(LegalComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          legalService: () => legalServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      legalServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLegals();
      await comp.$nextTick();

      // THEN
      expect(legalServiceStub.retrieve.called).toBeTruthy();
      expect(comp.legals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      legalServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLegal();
      await comp.$nextTick();

      // THEN
      expect(legalServiceStub.delete.called).toBeTruthy();
      expect(legalServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
