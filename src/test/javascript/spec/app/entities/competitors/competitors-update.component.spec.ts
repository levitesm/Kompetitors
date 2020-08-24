/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitorsUpdateComponent from '@/entities/competitors/competitors-update.vue';
import CompetitorsClass from '@/entities/competitors/competitors-update.component';
import CompetitorsService from '@/entities/competitors/competitors.service';

import DialogsService from '@/entities/dialogs/dialogs.service';

import FinanceService from '@/entities/finance/finance.service';

import OfficesService from '@/entities/offices/offices.service';

import PeopleService from '@/entities/people/people.service';

import PrInfoService from '@/entities/pr-info/pr-info.service';

import ListCountriesService from '@/entities/list-countries/list-countries.service';

import SocieteMainService from '@/entities/societe-main/societe-main.service';

import GlobalGroupsService from '@/entities/global-groups/global-groups.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Competitors Management Update Component', () => {
    let wrapper: Wrapper<CompetitorsClass>;
    let comp: CompetitorsClass;
    let competitorsServiceStub: SinonStubbedInstance<CompetitorsService>;

    beforeEach(() => {
      competitorsServiceStub = sinon.createStubInstance<CompetitorsService>(CompetitorsService);

      wrapper = shallowMount<CompetitorsClass>(CompetitorsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          competitorsService: () => competitorsServiceStub,

          dialogsService: () => new DialogsService(),

          financeService: () => new FinanceService(),

          officesService: () => new OfficesService(),

          peopleService: () => new PeopleService(),

          prInfoService: () => new PrInfoService(),

          listCountriesService: () => new ListCountriesService(),

          societeMainService: () => new SocieteMainService(),

          globalGroupsService: () => new GlobalGroupsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.competitors = entity;
        competitorsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitorsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.competitors = entity;
        competitorsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitorsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
