/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListActivitiesComponent from '@/entities/list-activities/list-activities.vue';
import ListActivitiesClass from '@/entities/list-activities/list-activities.component';
import ListActivitiesService from '@/entities/list-activities/list-activities.service';

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
  describe('ListActivities Management Component', () => {
    let wrapper: Wrapper<ListActivitiesClass>;
    let comp: ListActivitiesClass;
    let listActivitiesServiceStub: SinonStubbedInstance<ListActivitiesService>;

    beforeEach(() => {
      listActivitiesServiceStub = sinon.createStubInstance<ListActivitiesService>(ListActivitiesService);
      listActivitiesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListActivitiesClass>(ListActivitiesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listActivitiesService: () => listActivitiesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listActivitiesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListActivitiess();
      await comp.$nextTick();

      // THEN
      expect(listActivitiesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listActivities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listActivitiesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListActivities();
      await comp.$nextTick();

      // THEN
      expect(listActivitiesServiceStub.delete.called).toBeTruthy();
      expect(listActivitiesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
