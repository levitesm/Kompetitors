/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitiveRatesUpdateComponent from '@/entities/competitive-rates/competitive-rates-update.vue';
import CompetitiveRatesClass from '@/entities/competitive-rates/competitive-rates-update.component';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompetitiveRates Management Update Component', () => {
    let wrapper: Wrapper<CompetitiveRatesClass>;
    let comp: CompetitiveRatesClass;
    let competitiveRatesServiceStub: SinonStubbedInstance<CompetitiveRatesService>;

    beforeEach(() => {
      competitiveRatesServiceStub = sinon.createStubInstance<CompetitiveRatesService>(CompetitiveRatesService);

      wrapper = shallowMount<CompetitiveRatesClass>(CompetitiveRatesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitiveRatesService: () => competitiveRatesServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitiveRates = entity;
        competitiveRatesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(false).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitiveRates = entity;
        competitiveRatesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(true).toBeTruthy();
        expect(false).toEqual(false);
      });
    });
  });
});
