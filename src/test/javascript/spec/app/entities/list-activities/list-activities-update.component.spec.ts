/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListActivitiesUpdateComponent from '@/entities/list-activities/list-activities-update.vue';
import ListActivitiesClass from '@/entities/list-activities/list-activities-update.component';
import ListActivitiesService from '@/entities/list-activities/list-activities.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListActivities Management Update Component', () => {
    let wrapper: Wrapper<ListActivitiesClass>;
    let comp: ListActivitiesClass;
    let listActivitiesServiceStub: SinonStubbedInstance<ListActivitiesService>;

    beforeEach(() => {
      listActivitiesServiceStub = sinon.createStubInstance<ListActivitiesService>(ListActivitiesService);

      wrapper = shallowMount<ListActivitiesClass>(ListActivitiesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listActivitiesService: () => listActivitiesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listActivities = entity;
        listActivitiesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listActivitiesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listActivities = entity;
        listActivitiesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listActivitiesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
