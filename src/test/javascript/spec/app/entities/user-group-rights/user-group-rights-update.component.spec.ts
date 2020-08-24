/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserGroupRightsUpdateComponent from '@/entities/user-group-rights/user-group-rights-update.vue';
import UserGroupRightsClass from '@/entities/user-group-rights/user-group-rights-update.component';
import UserGroupRightsService from '@/entities/user-group-rights/user-group-rights.service';

import AccessKeyService from '@/entities/access-key/access-key.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('UserGroupRights Management Update Component', () => {
    let wrapper: Wrapper<UserGroupRightsClass>;
    let comp: UserGroupRightsClass;
    let userGroupRightsServiceStub: SinonStubbedInstance<UserGroupRightsService>;

    beforeEach(() => {
      userGroupRightsServiceStub = sinon.createStubInstance<UserGroupRightsService>(UserGroupRightsService);

      wrapper = shallowMount<UserGroupRightsClass>(UserGroupRightsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          userGroupRightsService: () => userGroupRightsServiceStub,

          accessKeyService: () => new AccessKeyService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.userGroupRights = entity;
        userGroupRightsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userGroupRightsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userGroupRights = entity;
        userGroupRightsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userGroupRightsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
