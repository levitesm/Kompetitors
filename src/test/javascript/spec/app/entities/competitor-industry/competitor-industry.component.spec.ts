/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitorIndustryComponent from '@/entities/competitor-industry/competitor-industry.vue';
import CompetitorIndustryClass from '@/entities/competitor-industry/competitor-industry.component';
import CompetitorIndustryService from '@/entities/competitor-industry/competitor-industry.service';

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
  describe('CompetitorIndustry Management Component', () => {
    let wrapper: Wrapper<CompetitorIndustryClass>;
    let comp: CompetitorIndustryClass;
    let competitorIndustryServiceStub: SinonStubbedInstance<CompetitorIndustryService>;

    beforeEach(() => {
      competitorIndustryServiceStub = sinon.createStubInstance<CompetitorIndustryService>(CompetitorIndustryService);
      competitorIndustryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitorIndustryClass>(CompetitorIndustryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitorIndustryService: () => competitorIndustryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitorIndustryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitorIndustrys();
      await comp.$nextTick();

      // THEN
      expect(competitorIndustryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitorIndustries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      competitorIndustryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitorIndustry();
      await comp.$nextTick();

      // THEN
      expect(competitorIndustryServiceStub.delete.called).toBeTruthy();
      expect(competitorIndustryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
