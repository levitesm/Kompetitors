/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ExternalUrlsComponent from '@/entities/external-urls/external-urls.vue';
import ExternalUrlsClass from '@/entities/external-urls/external-urls.component';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';

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
  describe('ExternalUrls Management Component', () => {
    let wrapper: Wrapper<ExternalUrlsClass>;
    let comp: ExternalUrlsClass;
    let externalUrlsServiceStub: SinonStubbedInstance<ExternalUrlsService>;

    beforeEach(() => {
      externalUrlsServiceStub = sinon.createStubInstance<ExternalUrlsService>(ExternalUrlsService);
      externalUrlsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ExternalUrlsClass>(ExternalUrlsComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          externalUrlsService: () => externalUrlsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      externalUrlsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllExternalUrlss();
      await comp.$nextTick();

      // THEN
      expect(externalUrlsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.externalUrls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      externalUrlsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(externalUrlsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.externalUrls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      externalUrlsServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(externalUrlsServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      externalUrlsServiceStub.retrieve.reset();
      externalUrlsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(externalUrlsServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.externalUrls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      externalUrlsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeExternalUrls();
      await comp.$nextTick();

      // THEN
      expect(externalUrlsServiceStub.delete.called).toBeTruthy();
      expect(externalUrlsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
