/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DialogsComponent from '@/entities/dialogs/dialogs.vue';
import DialogsClass from '@/entities/dialogs/dialogs.component';
import DialogsService from '@/entities/dialogs/dialogs.service';

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
  describe('Dialogs Management Component', () => {
    let wrapper: Wrapper<DialogsClass>;
    let comp: DialogsClass;
    let dialogsServiceStub: SinonStubbedInstance<DialogsService>;

    beforeEach(() => {
      dialogsServiceStub = sinon.createStubInstance<DialogsService>(DialogsService);
      dialogsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DialogsClass>(DialogsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          dialogsService: () => dialogsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dialogsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDialogss();
      await comp.$nextTick();

      // THEN
      expect(dialogsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dialogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dialogsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDialogs();
      await comp.$nextTick();

      // THEN
      expect(dialogsServiceStub.delete.called).toBeTruthy();
      expect(dialogsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
