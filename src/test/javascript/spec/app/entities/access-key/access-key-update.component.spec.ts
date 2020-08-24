/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AccessKeyUpdateComponent from '@/entities/access-key/access-key-update.vue';
import AccessKeyClass from '@/entities/access-key/access-key-update.component';
import AccessKeyService from '@/entities/access-key/access-key.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AccessKey Management Update Component', () => {
    let wrapper: Wrapper<AccessKeyClass>;
    let comp: AccessKeyClass;
    let accessKeyServiceStub: SinonStubbedInstance<AccessKeyService>;

    beforeEach(() => {
      accessKeyServiceStub = sinon.createStubInstance<AccessKeyService>(AccessKeyService);

      wrapper = shallowMount<AccessKeyClass>(AccessKeyUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          accessKeyService: () => accessKeyServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.accessKey = entity;
        accessKeyServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(accessKeyServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.accessKey = entity;
        accessKeyServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(accessKeyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
