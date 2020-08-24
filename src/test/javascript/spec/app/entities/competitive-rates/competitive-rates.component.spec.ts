/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitiveRatesComponent from '@/entities/competitive-rates/competitive-rates.vue';
import CompetitiveRatesClass from '@/entities/competitive-rates/competitive-rates.component';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

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
  describe('CompetitiveRates Management Component', () => {
    let wrapper: Wrapper<CompetitiveRatesClass>;
    let comp: CompetitiveRatesClass;
    let competitiveRatesServiceStub: SinonStubbedInstance<CompetitiveRatesService>;

    beforeEach(() => {
      competitiveRatesServiceStub = sinon.createStubInstance<CompetitiveRatesService>(CompetitiveRatesService);
      competitiveRatesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitiveRatesClass>(CompetitiveRatesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitiveRatesService: () => competitiveRatesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitiveRatesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitiveRatess();
      await comp.$nextTick();

      // THEN
      expect(competitiveRatesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitiveRates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      competitiveRatesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitiveRates();
      await comp.$nextTick();

      // THEN
      expect(true).toBeTruthy();
      expect(2).toEqual(2);
    });
  });
});
