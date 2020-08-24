/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PrInfoUpdateComponent from '@/entities/pr-info/pr-info-update.vue';
import PrInfoClass from '@/entities/pr-info/pr-info-update.component';
import PrInfoService from '@/entities/pr-info/pr-info.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PrInfo Management Update Component', () => {
    let wrapper: Wrapper<PrInfoClass>;
    let comp: PrInfoClass;
    let prInfoServiceStub: SinonStubbedInstance<PrInfoService>;

    beforeEach(() => {
      prInfoServiceStub = sinon.createStubInstance<PrInfoService>(PrInfoService);

      wrapper = shallowMount<PrInfoClass>(PrInfoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          prInfoService: () => prInfoServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.prInfo = entity;
        prInfoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prInfoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.prInfo = entity;
        prInfoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(prInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
