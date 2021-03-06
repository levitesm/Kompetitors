/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GlobalGroupsComponent from '@/entities/global-groups/global-groups.vue';
import GlobalGroupsClass from '@/entities/global-groups/global-groups.component';
import GlobalGroupsService from '@/entities/global-groups/global-groups.service';

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
  describe('GlobalGroups Management Component', () => {
    let wrapper: Wrapper<GlobalGroupsClass>;
    let comp: GlobalGroupsClass;
    let globalGroupsServiceStub: SinonStubbedInstance<GlobalGroupsService>;

    beforeEach(() => {
      globalGroupsServiceStub = sinon.createStubInstance<GlobalGroupsService>(GlobalGroupsService);
      globalGroupsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GlobalGroupsClass>(GlobalGroupsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          globalGroupsService: () => globalGroupsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      globalGroupsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGlobalGroupss();
      await comp.$nextTick();

      // THEN
      expect(globalGroupsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.globalGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      globalGroupsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGlobalGroups();
      await comp.$nextTick();

      // THEN
      expect(globalGroupsServiceStub.delete.called).toBeTruthy();
      expect(globalGroupsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
