/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ShareHoldersUpdateComponent from '@/entities/share-holders/share-holders-update.vue';
import ShareHoldersClass from '@/entities/share-holders/share-holders-update.component';
import ShareHoldersService from '@/entities/share-holders/share-holders.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ShareHolders Management Update Component', () => {
    let wrapper: Wrapper<ShareHoldersClass>;
    let comp: ShareHoldersClass;
    let shareHoldersServiceStub: SinonStubbedInstance<ShareHoldersService>;

    beforeEach(() => {
      shareHoldersServiceStub = sinon.createStubInstance<ShareHoldersService>(ShareHoldersService);

      wrapper = shallowMount<ShareHoldersClass>(ShareHoldersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          shareHoldersService: () => shareHoldersServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.shareHolders = entity;
        shareHoldersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(shareHoldersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.shareHolders = entity;
        shareHoldersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(shareHoldersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
