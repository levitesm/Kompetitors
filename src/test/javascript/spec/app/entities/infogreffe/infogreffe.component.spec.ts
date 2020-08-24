/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import InfogreffeComponent from '@/entities/infogreffe/infogreffe.vue';
import InfogreffeClass from '@/entities/infogreffe/infogreffe.component';
import InfogreffeService from '@/entities/infogreffe/infogreffe.service';

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
  describe('Infogreffe Management Component', () => {
    let wrapper: Wrapper<InfogreffeClass>;
    let comp: InfogreffeClass;
    let infogreffeServiceStub: SinonStubbedInstance<InfogreffeService>;

    beforeEach(() => {
      infogreffeServiceStub = sinon.createStubInstance<InfogreffeService>(InfogreffeService);
      infogreffeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<InfogreffeClass>(InfogreffeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          infogreffeService: () => infogreffeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      infogreffeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllInfogreffes();
      await comp.$nextTick();

      // THEN
      expect(infogreffeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.infogreffes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      infogreffeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeInfogreffe();
      await comp.$nextTick();

      // THEN
      expect(infogreffeServiceStub.delete.called).toBeTruthy();
      expect(infogreffeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
