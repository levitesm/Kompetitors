/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DashboardFinanceDetailComponent from '@/entities/dashboard-finance/dashboard-finance-details.vue';
import DashboardFinanceClass from '@/entities/dashboard-finance/dashboard-finance-details.component';
import DashboardFinanceService from '@/entities/dashboard-finance/dashboard-finance.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DashboardFinance Management Detail Component', () => {
    let wrapper: Wrapper<DashboardFinanceClass>;
    let comp: DashboardFinanceClass;
    let dashboardFinanceServiceStub: SinonStubbedInstance<DashboardFinanceService>;

    beforeEach(() => {
      dashboardFinanceServiceStub = sinon.createStubInstance<DashboardFinanceService>(DashboardFinanceService);

      wrapper = shallowMount<DashboardFinanceClass>(DashboardFinanceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { dashboardFinanceService: () => dashboardFinanceServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDashboardFinance = { id: 123 };
        dashboardFinanceServiceStub.find.resolves(foundDashboardFinance);

        // WHEN
        comp.retrieveDashboardFinance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dashboardFinance).toBe(foundDashboardFinance);
      });
    });
  });
});
