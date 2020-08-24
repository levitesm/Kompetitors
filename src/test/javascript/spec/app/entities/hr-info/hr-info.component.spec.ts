/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import HrInfoComponent from '@/entities/hr-info/hr-info.vue';
import HrInfoClass from '@/entities/hr-info/hr-info.component';
import HrInfoService from '@/entities/hr-info/hr-info.service';

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
  describe('HrInfo Management Component', () => {
    let wrapper: Wrapper<HrInfoClass>;
    let comp: HrInfoClass;
    let hrInfoServiceStub: SinonStubbedInstance<HrInfoService>;

    beforeEach(() => {
      hrInfoServiceStub = sinon.createStubInstance<HrInfoService>(HrInfoService);
      hrInfoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HrInfoClass>(HrInfoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          hrInfoService: () => hrInfoServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      hrInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllHrInfos();
      await comp.$nextTick();

      // THEN
      expect(hrInfoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.hrInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      hrInfoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeHrInfo();
      await comp.$nextTick();

      // THEN
      expect(hrInfoServiceStub.delete.called).toBeTruthy();
      expect(hrInfoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
