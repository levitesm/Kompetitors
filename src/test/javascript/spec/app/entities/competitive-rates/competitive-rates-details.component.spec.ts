/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitiveRatesDetailComponent from '@/entities/competitive-rates/competitive-rates-details.vue';
import CompetitiveRatesClass from '@/entities/competitive-rates/competitive-rates-details.component';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitiveRates Management Detail Component', () => {
    let wrapper: Wrapper<CompetitiveRatesClass>;
    let comp: CompetitiveRatesClass;
    let competitiveRatesServiceStub: SinonStubbedInstance<CompetitiveRatesService>;

    beforeEach(() => {
      competitiveRatesServiceStub = sinon.createStubInstance<CompetitiveRatesService>(CompetitiveRatesService);

      wrapper = shallowMount<CompetitiveRatesClass>(CompetitiveRatesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitiveRatesService: () => competitiveRatesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitiveRates = { id: 123 };
        competitiveRatesServiceStub.find.resolves(foundCompetitiveRates);

        // WHEN
        comp.retrieveCompetitiveRates(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitiveRates).toBe(foundCompetitiveRates);
      });
    });
  });
});
