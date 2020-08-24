/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UserGroupRightsDetailComponent from '@/entities/user-group-rights/user-group-rights-details.vue';
import UserGroupRightsClass from '@/entities/user-group-rights/user-group-rights-details.component';
import UserGroupRightsService from '@/entities/user-group-rights/user-group-rights.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserGroupRights Management Detail Component', () => {
    let wrapper: Wrapper<UserGroupRightsClass>;
    let comp: UserGroupRightsClass;
    let userGroupRightsServiceStub: SinonStubbedInstance<UserGroupRightsService>;

    beforeEach(() => {
      userGroupRightsServiceStub = sinon.createStubInstance<UserGroupRightsService>(UserGroupRightsService);

      wrapper = shallowMount<UserGroupRightsClass>(UserGroupRightsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { userGroupRightsService: () => userGroupRightsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserGroupRights = { id: 123 };
        userGroupRightsServiceStub.find.resolves(foundUserGroupRights);

        // WHEN
        comp.retrieveUserGroupRights(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userGroupRights).toBe(foundUserGroupRights);
      });
    });
  });
});
