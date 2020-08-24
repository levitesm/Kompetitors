/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListActivitiesDetailComponent from '@/entities/list-activities/list-activities-details.vue';
import ListActivitiesClass from '@/entities/list-activities/list-activities-details.component';
import ListActivitiesService from '@/entities/list-activities/list-activities.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListActivities Management Detail Component', () => {
    let wrapper: Wrapper<ListActivitiesClass>;
    let comp: ListActivitiesClass;
    let listActivitiesServiceStub: SinonStubbedInstance<ListActivitiesService>;

    beforeEach(() => {
      listActivitiesServiceStub = sinon.createStubInstance<ListActivitiesService>(ListActivitiesService);

      wrapper = shallowMount<ListActivitiesClass>(ListActivitiesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listActivitiesService: () => listActivitiesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListActivities = { id: 123 };
        listActivitiesServiceStub.find.resolves(foundListActivities);

        // WHEN
        comp.retrieveListActivities(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listActivities).toBe(foundListActivities);
      });
    });
  });
});
