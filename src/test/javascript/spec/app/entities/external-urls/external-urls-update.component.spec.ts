/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ExternalUrlsUpdateComponent from '@/entities/external-urls/external-urls-update.vue';
import ExternalUrlsClass from '@/entities/external-urls/external-urls-update.component';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ExternalUrls Management Update Component', () => {
    let wrapper: Wrapper<ExternalUrlsClass>;
    let comp: ExternalUrlsClass;
    let externalUrlsServiceStub: SinonStubbedInstance<ExternalUrlsService>;

    beforeEach(() => {
      externalUrlsServiceStub = sinon.createStubInstance<ExternalUrlsService>(ExternalUrlsService);

      wrapper = shallowMount<ExternalUrlsClass>(ExternalUrlsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          externalUrlsService: () => externalUrlsServiceStub,

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.externalUrls = entity;
        externalUrlsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(externalUrlsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.externalUrls = entity;
        externalUrlsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(externalUrlsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
