/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitorIndustryUpdateComponent from '@/entities/competitor-industry/competitor-industry-update.vue';
import CompetitorIndustryClass from '@/entities/competitor-industry/competitor-industry-update.component';
import CompetitorIndustryService from '@/entities/competitor-industry/competitor-industry.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

import ListIndustriesService from '@/entities/list-industries/list-industries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitorIndustry Management Update Component', () => {
    let wrapper: Wrapper<CompetitorIndustryClass>;
    let comp: CompetitorIndustryClass;
    let competitorIndustryServiceStub: SinonStubbedInstance<CompetitorIndustryService>;

    beforeEach(() => {
      competitorIndustryServiceStub = sinon.createStubInstance<CompetitorIndustryService>(CompetitorIndustryService);

      wrapper = shallowMount<CompetitorIndustryClass>(CompetitorIndustryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitorIndustryService: () => competitorIndustryServiceStub,

          competitorsService: () => new CompetitorsService(),

          listIndustriesService: () => new ListIndustriesService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitorIndustry = entity;
        competitorIndustryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitorIndustryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitorIndustry = entity;
        competitorIndustryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitorIndustryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
