/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitorsComponent from '@/entities/competitors/competitors.vue';
import CompetitorsClass from '@/entities/competitors/competitors.component';
import CompetitorsService from '@/entities/competitors/competitors.service';

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
  describe('Competitors Management Component', () => {
    let wrapper: Wrapper<CompetitorsClass>;
    let comp: CompetitorsClass;
    let competitorsServiceStub: SinonStubbedInstance<CompetitorsService>;

    beforeEach(() => {
      competitorsServiceStub = sinon.createStubInstance<CompetitorsService>(CompetitorsService);
      competitorsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitorsClass>(CompetitorsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitorsService: () => competitorsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitorsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitorss();
      await comp.$nextTick();

      // THEN
      expect(competitorsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      competitorsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitors();
      await comp.$nextTick();

      // THEN
      expect(competitorsServiceStub.delete.called).toBeTruthy();
      expect(competitorsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
