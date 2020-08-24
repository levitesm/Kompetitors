/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListCompetanciesComponent from '@/entities/list-competancies/list-competancies.vue';
import ListCompetanciesClass from '@/entities/list-competancies/list-competancies.component';
import ListCompetanciesService from '@/entities/list-competancies/list-competancies.service';

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
  describe('ListCompetancies Management Component', () => {
    let wrapper: Wrapper<ListCompetanciesClass>;
    let comp: ListCompetanciesClass;
    let listCompetanciesServiceStub: SinonStubbedInstance<ListCompetanciesService>;

    beforeEach(() => {
      listCompetanciesServiceStub = sinon.createStubInstance<ListCompetanciesService>(ListCompetanciesService);
      listCompetanciesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListCompetanciesClass>(ListCompetanciesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listCompetanciesService: () => listCompetanciesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listCompetanciesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListCompetanciess();
      await comp.$nextTick();

      // THEN
      expect(listCompetanciesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listCompetancies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listCompetanciesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListCompetancies();
      await comp.$nextTick();

      // THEN
      expect(listCompetanciesServiceStub.delete.called).toBeTruthy();
      expect(listCompetanciesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
