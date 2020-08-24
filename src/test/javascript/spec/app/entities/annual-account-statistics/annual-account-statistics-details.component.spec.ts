/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AnnualAccountStatisticsDetailComponent from '@/entities/annual-account-statistics/annual-account-statistics-details.vue';
import AnnualAccountStatisticsClass from '@/entities/annual-account-statistics/annual-account-statistics-details.component';
import AnnualAccountStatisticsService from '@/entities/annual-account-statistics/annual-account-statistics.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnnualAccountStatistics Management Detail Component', () => {
    let wrapper: Wrapper<AnnualAccountStatisticsClass>;
    let comp: AnnualAccountStatisticsClass;
    let annualAccountStatisticsServiceStub: SinonStubbedInstance<AnnualAccountStatisticsService>;

    beforeEach(() => {
      annualAccountStatisticsServiceStub = sinon.createStubInstance<AnnualAccountStatisticsService>(AnnualAccountStatisticsService);

      wrapper = shallowMount<AnnualAccountStatisticsClass>(AnnualAccountStatisticsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { annualAccountStatisticsService: () => annualAccountStatisticsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnnualAccountStatistics = { id: 123 };
        annualAccountStatisticsServiceStub.find.resolves(foundAnnualAccountStatistics);

        // WHEN
        comp.retrieveAnnualAccountStatistics(123);
        await comp.$nextTick();

        // THEN
        expect(comp.annualAccountStatistics).toBe(foundAnnualAccountStatistics);
      });
    });
  });
});
