/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RegionZipListDetailComponent from '@/entities/region-zip-list/region-zip-list-details.vue';
import RegionZipListClass from '@/entities/region-zip-list/region-zip-list-details.component';
import RegionZipListService from '@/entities/region-zip-list/region-zip-list.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RegionZipList Management Detail Component', () => {
    let wrapper: Wrapper<RegionZipListClass>;
    let comp: RegionZipListClass;
    let regionZipListServiceStub: SinonStubbedInstance<RegionZipListService>;

    beforeEach(() => {
      regionZipListServiceStub = sinon.createStubInstance<RegionZipListService>(RegionZipListService);

      wrapper = shallowMount<RegionZipListClass>(RegionZipListDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { regionZipListService: () => regionZipListServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegionZipList = { id: 123 };
        regionZipListServiceStub.find.resolves(foundRegionZipList);

        // WHEN
        comp.retrieveRegionZipList(123);
        await comp.$nextTick();

        // THEN
        expect(comp.regionZipList).toBe(foundRegionZipList);
      });
    });
  });
});
