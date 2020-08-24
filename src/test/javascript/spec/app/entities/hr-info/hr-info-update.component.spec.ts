/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import HrInfoUpdateComponent from '@/entities/hr-info/hr-info-update.vue';
import HrInfoClass from '@/entities/hr-info/hr-info-update.component';
import HrInfoService from '@/entities/hr-info/hr-info.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('HrInfo Management Update Component', () => {
    let wrapper: Wrapper<HrInfoClass>;
    let comp: HrInfoClass;
    let hrInfoServiceStub: SinonStubbedInstance<HrInfoService>;

    beforeEach(() => {
      hrInfoServiceStub = sinon.createStubInstance<HrInfoService>(HrInfoService);

      wrapper = shallowMount<HrInfoClass>(HrInfoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          hrInfoService: () => hrInfoServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.hrInfo = entity;
        hrInfoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hrInfoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.hrInfo = entity;
        hrInfoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hrInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
