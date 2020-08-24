/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LegalUpdateComponent from '@/entities/legal/legal-update.vue';
import LegalClass from '@/entities/legal/legal-update.component';
import LegalService from '@/entities/legal/legal.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

import ListOwnershipsService from '@/entities/list-ownerships/list-ownerships.service';

import ListActivitiesService from '@/entities/list-activities/list-activities.service';

import ListPricingsService from '@/entities/list-pricings/list-pricings.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Legal Management Update Component', () => {
    let wrapper: Wrapper<LegalClass>;
    let comp: LegalClass;
    let legalServiceStub: SinonStubbedInstance<LegalService>;

    beforeEach(() => {
      legalServiceStub = sinon.createStubInstance<LegalService>(LegalService);

      wrapper = shallowMount<LegalClass>(LegalUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          legalService: () => legalServiceStub,

          competitorsService: () => new CompetitorsService(),

          listOwnershipsService: () => new ListOwnershipsService(),

          listActivitiesService: () => new ListActivitiesService(),

          listPricingsService: () => new ListPricingsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.legal = entity;
        legalServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(legalServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.legal = entity;
        legalServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(legalServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
