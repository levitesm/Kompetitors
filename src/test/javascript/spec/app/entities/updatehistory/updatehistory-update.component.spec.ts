/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UpdatehistoryUpdateComponent from '@/entities/updatehistory/updatehistory-update.vue';
import UpdatehistoryClass from '@/entities/updatehistory/updatehistory-update.component';
import UpdatehistoryService from '@/entities/updatehistory/updatehistory.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Updatehistory Management Update Component', () => {
    let wrapper: Wrapper<UpdatehistoryClass>;
    let comp: UpdatehistoryClass;
    let updatehistoryServiceStub: SinonStubbedInstance<UpdatehistoryService>;

    beforeEach(() => {
      updatehistoryServiceStub = sinon.createStubInstance<UpdatehistoryService>(UpdatehistoryService);

      wrapper = shallowMount<UpdatehistoryClass>(UpdatehistoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          updatehistoryService: () => updatehistoryServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.updatehistory = entity;
        updatehistoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(updatehistoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.updatehistory = entity;
        updatehistoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(updatehistoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
