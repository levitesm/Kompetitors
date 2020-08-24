/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PeopleComponent from '@/entities/people/people.vue';
import PeopleClass from '@/entities/people/people.component';
import PeopleService from '@/entities/people/people.service';

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
  describe('People Management Component', () => {
    let wrapper: Wrapper<PeopleClass>;
    let comp: PeopleClass;
    let peopleServiceStub: SinonStubbedInstance<PeopleService>;

    beforeEach(() => {
      peopleServiceStub = sinon.createStubInstance<PeopleService>(PeopleService);
      peopleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PeopleClass>(PeopleComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          peopleService: () => peopleServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      peopleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPeoples();
      await comp.$nextTick();

      // THEN
      expect(peopleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.people[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      peopleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePeople();
      await comp.$nextTick();

      // THEN
      expect(peopleServiceStub.delete.called).toBeTruthy();
      expect(peopleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
