/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DashboardFinanceComponent from '@/entities/dashboard-finance/dashboard-finance.vue';
import DashboardFinanceClass from '@/entities/dashboard-finance/dashboard-finance.component';
import DashboardFinanceService from '@/entities/dashboard-finance/dashboard-finance.service';

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
  describe('DashboardFinance Management Component', () => {
    let wrapper: Wrapper<DashboardFinanceClass>;
    let comp: DashboardFinanceClass;
    let dashboardFinanceServiceStub: SinonStubbedInstance<DashboardFinanceService>;

    beforeEach(() => {
      dashboardFinanceServiceStub = sinon.createStubInstance<DashboardFinanceService>(DashboardFinanceService);
      dashboardFinanceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DashboardFinanceClass>(DashboardFinanceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          dashboardFinanceService: () => dashboardFinanceServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dashboardFinanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDashboardFinances();
      await comp.$nextTick();

      // THEN
      expect(dashboardFinanceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dashboardFinances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dashboardFinanceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDashboardFinance();
      await comp.$nextTick();

      // THEN
      expect(dashboardFinanceServiceStub.delete.called).toBeTruthy();
      expect(dashboardFinanceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
