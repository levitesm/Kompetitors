/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DashboardFinanceUpdateComponent from '@/entities/dashboard-finance/dashboard-finance-update.vue';
import DashboardFinanceClass from '@/entities/dashboard-finance/dashboard-finance-update.component';
import DashboardFinanceService from '@/entities/dashboard-finance/dashboard-finance.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('DashboardFinance Management Update Component', () => {
    let wrapper: Wrapper<DashboardFinanceClass>;
    let comp: DashboardFinanceClass;
    let dashboardFinanceServiceStub: SinonStubbedInstance<DashboardFinanceService>;

    beforeEach(() => {
      dashboardFinanceServiceStub = sinon.createStubInstance<DashboardFinanceService>(DashboardFinanceService);

      wrapper = shallowMount<DashboardFinanceClass>(DashboardFinanceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          dashboardFinanceService: () => dashboardFinanceServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.dashboardFinance = entity;
        dashboardFinanceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dashboardFinanceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dashboardFinance = entity;
        dashboardFinanceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dashboardFinanceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
