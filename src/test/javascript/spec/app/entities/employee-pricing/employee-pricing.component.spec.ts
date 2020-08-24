/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeePricingComponent from '@/entities/employee-pricing/employee-pricing.vue';
import EmployeePricingClass from '@/entities/employee-pricing/employee-pricing.component';
import EmployeePricingService from '@/entities/employee-pricing/employee-pricing.service';

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
  describe('EmployeePricing Management Component', () => {
    let wrapper: Wrapper<EmployeePricingClass>;
    let comp: EmployeePricingClass;
    let employeePricingServiceStub: SinonStubbedInstance<EmployeePricingService>;

    beforeEach(() => {
      employeePricingServiceStub = sinon.createStubInstance<EmployeePricingService>(EmployeePricingService);
      employeePricingServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmployeePricingClass>(EmployeePricingComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          employeePricingService: () => employeePricingServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      employeePricingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmployeePricings();
      await comp.$nextTick();

      // THEN
      expect(employeePricingServiceStub.retrieve.called).toBeTruthy();
      expect(comp.employeePricings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      employeePricingServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEmployeePricing();
      await comp.$nextTick();

      // THEN
      expect(employeePricingServiceStub.delete.called).toBeTruthy();
      expect(employeePricingServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
