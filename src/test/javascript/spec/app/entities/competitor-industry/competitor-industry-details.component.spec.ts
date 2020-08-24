/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitorIndustryDetailComponent from '@/entities/competitor-industry/competitor-industry-details.vue';
import CompetitorIndustryClass from '@/entities/competitor-industry/competitor-industry-details.component';
import CompetitorIndustryService from '@/entities/competitor-industry/competitor-industry.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitorIndustry Management Detail Component', () => {
    let wrapper: Wrapper<CompetitorIndustryClass>;
    let comp: CompetitorIndustryClass;
    let competitorIndustryServiceStub: SinonStubbedInstance<CompetitorIndustryService>;

    beforeEach(() => {
      competitorIndustryServiceStub = sinon.createStubInstance<CompetitorIndustryService>(CompetitorIndustryService);

      wrapper = shallowMount<CompetitorIndustryClass>(CompetitorIndustryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitorIndustryService: () => competitorIndustryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitorIndustry = { id: 123 };
        competitorIndustryServiceStub.find.resolves(foundCompetitorIndustry);

        // WHEN
        comp.retrieveCompetitorIndustry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitorIndustry).toBe(foundCompetitorIndustry);
      });
    });
  });
});
