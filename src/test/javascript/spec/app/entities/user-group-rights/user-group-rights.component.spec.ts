/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserGroupRightsComponent from '@/entities/user-group-rights/user-group-rights.vue';
import UserGroupRightsClass from '@/entities/user-group-rights/user-group-rights.component';
import UserGroupRightsService from '@/entities/user-group-rights/user-group-rights.service';

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
  describe('UserGroupRights Management Component', () => {
    let wrapper: Wrapper<UserGroupRightsClass>;
    let comp: UserGroupRightsClass;
    let userGroupRightsServiceStub: SinonStubbedInstance<UserGroupRightsService>;

    beforeEach(() => {
      userGroupRightsServiceStub = sinon.createStubInstance<UserGroupRightsService>(UserGroupRightsService);
      userGroupRightsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UserGroupRightsClass>(UserGroupRightsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          userGroupRightsService: () => userGroupRightsServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      userGroupRightsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUserGroupRightss();
      await comp.$nextTick();

      // THEN
      expect(userGroupRightsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userGroupRights[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      userGroupRightsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeUserGroupRights();
      await comp.$nextTick();

      // THEN
      expect(userGroupRightsServiceStub.delete.called).toBeTruthy();
      expect(userGroupRightsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
