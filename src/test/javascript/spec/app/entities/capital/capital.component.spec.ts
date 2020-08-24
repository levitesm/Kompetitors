/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CapitalComponent from '@/entities/capital/capital.vue';
import CapitalClass from '@/entities/capital/capital.component';
import CapitalService from '@/entities/capital/capital.service';

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
  describe('Capital Management Component', () => {
    let wrapper: Wrapper<CapitalClass>;
    let comp: CapitalClass;
    let capitalServiceStub: SinonStubbedInstance<CapitalService>;

    beforeEach(() => {
      capitalServiceStub = sinon.createStubInstance<CapitalService>(CapitalService);
      capitalServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CapitalClass>(CapitalComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          capitalService: () => capitalServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      capitalServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCapitals();
      await comp.$nextTick();

      // THEN
      expect(capitalServiceStub.retrieve.called).toBeTruthy();
      expect(comp.capitals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      capitalServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCapital();
      await comp.$nextTick();

      // THEN
      expect(capitalServiceStub.delete.called).toBeTruthy();
      expect(capitalServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
