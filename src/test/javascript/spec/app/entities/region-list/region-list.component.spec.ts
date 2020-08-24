/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RegionListComponent from '@/entities/region-list/region-list.vue';
import RegionListClass from '@/entities/region-list/region-list.component';
import RegionListService from '@/entities/region-list/region-list.service';

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
  describe('RegionList Management Component', () => {
    let wrapper: Wrapper<RegionListClass>;
    let comp: RegionListClass;
    let regionListServiceStub: SinonStubbedInstance<RegionListService>;

    beforeEach(() => {
      regionListServiceStub = sinon.createStubInstance<RegionListService>(RegionListService);
      regionListServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RegionListClass>(RegionListComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          regionListService: () => regionListServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      regionListServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRegionLists();
      await comp.$nextTick();

      // THEN
      expect(regionListServiceStub.retrieve.called).toBeTruthy();
      expect(comp.regionLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      regionListServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRegionList();
      await comp.$nextTick();

      // THEN
      expect(regionListServiceStub.delete.called).toBeTruthy();
      expect(regionListServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
