/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UpdatehistoryComponent from '@/entities/updatehistory/updatehistory.vue';
import UpdatehistoryClass from '@/entities/updatehistory/updatehistory.component';
import UpdatehistoryService from '@/entities/updatehistory/updatehistory.service';

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
  describe('Updatehistory Management Component', () => {
    let wrapper: Wrapper<UpdatehistoryClass>;
    let comp: UpdatehistoryClass;
    let updatehistoryServiceStub: SinonStubbedInstance<UpdatehistoryService>;

    beforeEach(() => {
      updatehistoryServiceStub = sinon.createStubInstance<UpdatehistoryService>(UpdatehistoryService);
      updatehistoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UpdatehistoryClass>(UpdatehistoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          updatehistoryService: () => updatehistoryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      updatehistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUpdatehistorys();
      await comp.$nextTick();

      // THEN
      expect(updatehistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.updatehistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      updatehistoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeUpdatehistory();
      await comp.$nextTick();

      // THEN
      expect(updatehistoryServiceStub.delete.called).toBeTruthy();
      expect(updatehistoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
