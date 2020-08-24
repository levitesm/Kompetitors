/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RegionZipListUpdateComponent from '@/entities/region-zip-list/region-zip-list-update.vue';
import RegionZipListClass from '@/entities/region-zip-list/region-zip-list-update.component';
import RegionZipListService from '@/entities/region-zip-list/region-zip-list.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RegionZipList Management Update Component', () => {
    let wrapper: Wrapper<RegionZipListClass>;
    let comp: RegionZipListClass;
    let regionZipListServiceStub: SinonStubbedInstance<RegionZipListService>;

    beforeEach(() => {
      regionZipListServiceStub = sinon.createStubInstance<RegionZipListService>(RegionZipListService);

      wrapper = shallowMount<RegionZipListClass>(RegionZipListUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          regionZipListService: () => regionZipListServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.regionZipList = entity;
        regionZipListServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regionZipListServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.regionZipList = entity;
        regionZipListServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regionZipListServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
