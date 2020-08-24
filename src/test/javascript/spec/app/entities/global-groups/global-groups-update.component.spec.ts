/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GlobalGroupsUpdateComponent from '@/entities/global-groups/global-groups-update.vue';
import GlobalGroupsClass from '@/entities/global-groups/global-groups-update.component';
import GlobalGroupsService from '@/entities/global-groups/global-groups.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('GlobalGroups Management Update Component', () => {
    let wrapper: Wrapper<GlobalGroupsClass>;
    let comp: GlobalGroupsClass;
    let globalGroupsServiceStub: SinonStubbedInstance<GlobalGroupsService>;

    beforeEach(() => {
      globalGroupsServiceStub = sinon.createStubInstance<GlobalGroupsService>(GlobalGroupsService);

      wrapper = shallowMount<GlobalGroupsClass>(GlobalGroupsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          globalGroupsService: () => globalGroupsServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.globalGroups = entity;
        globalGroupsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(globalGroupsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.globalGroups = entity;
        globalGroupsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(globalGroupsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
