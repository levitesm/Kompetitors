/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AnnualAccountUpdateComponent from '@/entities/annual-account/annual-account-update.vue';
import AnnualAccountClass from '@/entities/annual-account/annual-account-update.component';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AnnualAccount Management Update Component', () => {
    let wrapper: Wrapper<AnnualAccountClass>;
    let comp: AnnualAccountClass;
    let annualAccountServiceStub: SinonStubbedInstance<AnnualAccountService>;

    beforeEach(() => {
      annualAccountServiceStub = sinon.createStubInstance<AnnualAccountService>(AnnualAccountService);

      wrapper = shallowMount<AnnualAccountClass>(AnnualAccountUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          annualAccountService: () => annualAccountServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.annualAccount = entity;
        annualAccountServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(annualAccountServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.annualAccount = entity;
        annualAccountServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(annualAccountServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
