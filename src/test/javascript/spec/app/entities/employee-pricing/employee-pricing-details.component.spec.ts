/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EmployeePricingDetailComponent from '@/entities/employee-pricing/employee-pricing-details.vue';
import EmployeePricingClass from '@/entities/employee-pricing/employee-pricing-details.component';
import EmployeePricingService from '@/entities/employee-pricing/employee-pricing.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EmployeePricing Management Detail Component', () => {
    let wrapper: Wrapper<EmployeePricingClass>;
    let comp: EmployeePricingClass;
    let employeePricingServiceStub: SinonStubbedInstance<EmployeePricingService>;

    beforeEach(() => {
      employeePricingServiceStub = sinon.createStubInstance<EmployeePricingService>(EmployeePricingService);

      wrapper = shallowMount<EmployeePricingClass>(EmployeePricingDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { employeePricingService: () => employeePricingServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmployeePricing = { id: 123 };
        employeePricingServiceStub.find.resolves(foundEmployeePricing);

        // WHEN
        comp.retrieveEmployeePricing(123);
        await comp.$nextTick();

        // THEN
        expect(comp.employeePricing).toBe(foundEmployeePricing);
      });
    });
  });
});
