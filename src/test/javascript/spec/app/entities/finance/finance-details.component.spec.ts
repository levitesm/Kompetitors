/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FinanceDetailComponent from '@/entities/finance/finance-details.vue';
import FinanceClass from '@/entities/finance/finance-details.component';
import FinanceService from '@/entities/finance/finance.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Finance Management Detail Component', () => {
    let wrapper: Wrapper<FinanceClass>;
    let comp: FinanceClass;
    let financeServiceStub: SinonStubbedInstance<FinanceService>;

    beforeEach(() => {
      financeServiceStub = sinon.createStubInstance<FinanceService>(FinanceService);

      wrapper = shallowMount<FinanceClass>(FinanceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { financeService: () => financeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFinance = { id: 123 };
        financeServiceStub.find.resolves(foundFinance);

        // WHEN
        comp.retrieveFinance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.finance).toBe(foundFinance);
      });
    });
  });
});
