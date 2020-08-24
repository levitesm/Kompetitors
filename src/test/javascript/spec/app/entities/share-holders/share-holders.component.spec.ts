/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ShareHoldersComponent from '@/entities/share-holders/share-holders.vue';
import ShareHoldersClass from '@/entities/share-holders/share-holders.component';
import ShareHoldersService from '@/entities/share-holders/share-holders.service';

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
  describe('ShareHolders Management Component', () => {
    let wrapper: Wrapper<ShareHoldersClass>;
    let comp: ShareHoldersClass;
    let shareHoldersServiceStub: SinonStubbedInstance<ShareHoldersService>;

    beforeEach(() => {
      shareHoldersServiceStub = sinon.createStubInstance<ShareHoldersService>(ShareHoldersService);
      shareHoldersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ShareHoldersClass>(ShareHoldersComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          shareHoldersService: () => shareHoldersServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      shareHoldersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllShareHolderss();
      await comp.$nextTick();

      // THEN
      expect(shareHoldersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.shareHolders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      shareHoldersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeShareHolders();
      await comp.$nextTick();

      // THEN
      expect(shareHoldersServiceStub.delete.called).toBeTruthy();
      expect(shareHoldersServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
