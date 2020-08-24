/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FinanceUpdateComponent from '@/entities/finance/finance-update.vue';
import FinanceClass from '@/entities/finance/finance-update.component';
import FinanceService from '@/entities/finance/finance.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Finance Management Update Component', () => {
    let wrapper: Wrapper<FinanceClass>;
    let comp: FinanceClass;
    let financeServiceStub: SinonStubbedInstance<FinanceService>;

    beforeEach(() => {
      financeServiceStub = sinon.createStubInstance<FinanceService>(FinanceService);

      wrapper = shallowMount<FinanceClass>(FinanceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          financeService: () => financeServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.finance = entity;
        financeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(financeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.finance = entity;
        financeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(financeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
