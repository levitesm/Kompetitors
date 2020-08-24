/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AccessKeyComponent from '@/entities/access-key/access-key.vue';
import AccessKeyClass from '@/entities/access-key/access-key.component';
import AccessKeyService from '@/entities/access-key/access-key.service';

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
  describe('AccessKey Management Component', () => {
    let wrapper: Wrapper<AccessKeyClass>;
    let comp: AccessKeyClass;
    let accessKeyServiceStub: SinonStubbedInstance<AccessKeyService>;

    beforeEach(() => {
      accessKeyServiceStub = sinon.createStubInstance<AccessKeyService>(AccessKeyService);
      accessKeyServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AccessKeyClass>(AccessKeyComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          accessKeyService: () => accessKeyServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      accessKeyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAccessKeys();
      await comp.$nextTick();

      // THEN
      expect(accessKeyServiceStub.retrieve.called).toBeTruthy();
      expect(comp.accessKeys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      accessKeyServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAccessKey();
      await comp.$nextTick();

      // THEN
      expect(accessKeyServiceStub.delete.called).toBeTruthy();
      expect(accessKeyServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
