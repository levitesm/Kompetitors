/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RegionZipListComponent from '@/entities/region-zip-list/region-zip-list.vue';
import RegionZipListClass from '@/entities/region-zip-list/region-zip-list.component';
import RegionZipListService from '@/entities/region-zip-list/region-zip-list.service';

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
  describe('RegionZipList Management Component', () => {
    let wrapper: Wrapper<RegionZipListClass>;
    let comp: RegionZipListClass;
    let regionZipListServiceStub: SinonStubbedInstance<RegionZipListService>;

    beforeEach(() => {
      regionZipListServiceStub = sinon.createStubInstance<RegionZipListService>(RegionZipListService);
      regionZipListServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RegionZipListClass>(RegionZipListComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          regionZipListService: () => regionZipListServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      regionZipListServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRegionZipLists();
      await comp.$nextTick();

      // THEN
      expect(regionZipListServiceStub.retrieve.called).toBeTruthy();
      expect(comp.regionZipLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      regionZipListServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRegionZipList();
      await comp.$nextTick();

      // THEN
      expect(regionZipListServiceStub.delete.called).toBeTruthy();
      expect(regionZipListServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
