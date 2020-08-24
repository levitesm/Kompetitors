/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SocieteMainUpdateComponent from '@/entities/societe-main/societe-main-update.vue';
import SocieteMainClass from '@/entities/societe-main/societe-main-update.component';
import SocieteMainService from '@/entities/societe-main/societe-main.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('SocieteMain Management Update Component', () => {
    let wrapper: Wrapper<SocieteMainClass>;
    let comp: SocieteMainClass;
    let societeMainServiceStub: SinonStubbedInstance<SocieteMainService>;

    beforeEach(() => {
      societeMainServiceStub = sinon.createStubInstance<SocieteMainService>(SocieteMainService);

      wrapper = shallowMount<SocieteMainClass>(SocieteMainUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          societeMainService: () => societeMainServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.societeMain = entity;
        societeMainServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(societeMainServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.societeMain = entity;
        societeMainServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(societeMainServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
