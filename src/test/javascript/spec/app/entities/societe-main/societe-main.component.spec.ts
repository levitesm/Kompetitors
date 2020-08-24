/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SocieteMainComponent from '@/entities/societe-main/societe-main.vue';
import SocieteMainClass from '@/entities/societe-main/societe-main.component';
import SocieteMainService from '@/entities/societe-main/societe-main.service';

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
  describe('SocieteMain Management Component', () => {
    let wrapper: Wrapper<SocieteMainClass>;
    let comp: SocieteMainClass;
    let societeMainServiceStub: SinonStubbedInstance<SocieteMainService>;

    beforeEach(() => {
      societeMainServiceStub = sinon.createStubInstance<SocieteMainService>(SocieteMainService);
      societeMainServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SocieteMainClass>(SocieteMainComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          societeMainService: () => societeMainServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      societeMainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSocieteMains();
      await comp.$nextTick();

      // THEN
      expect(societeMainServiceStub.retrieve.called).toBeTruthy();
      expect(comp.societeMains[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      societeMainServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSocieteMain();
      await comp.$nextTick();

      // THEN
      expect(societeMainServiceStub.delete.called).toBeTruthy();
      expect(societeMainServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
