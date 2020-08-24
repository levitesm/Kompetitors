/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RegionListDetailComponent from '@/entities/region-list/region-list-details.vue';
import RegionListClass from '@/entities/region-list/region-list-details.component';
import RegionListService from '@/entities/region-list/region-list.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RegionList Management Detail Component', () => {
    let wrapper: Wrapper<RegionListClass>;
    let comp: RegionListClass;
    let regionListServiceStub: SinonStubbedInstance<RegionListService>;

    beforeEach(() => {
      regionListServiceStub = sinon.createStubInstance<RegionListService>(RegionListService);

      wrapper = shallowMount<RegionListClass>(RegionListDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { regionListService: () => regionListServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegionList = { id: 123 };
        regionListServiceStub.find.resolves(foundRegionList);

        // WHEN
        comp.retrieveRegionList(123);
        await comp.$nextTick();

        // THEN
        expect(comp.regionList).toBe(foundRegionList);
      });
    });
  });
});
