/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListTechPartnersUpdateComponent from '@/entities/list-tech-partners/list-tech-partners-update.vue';
import ListTechPartnersClass from '@/entities/list-tech-partners/list-tech-partners-update.component';
import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ListTechPartners Management Update Component', () => {
    let wrapper: Wrapper<ListTechPartnersClass>;
    let comp: ListTechPartnersClass;
    let listTechPartnersServiceStub: SinonStubbedInstance<ListTechPartnersService>;

    beforeEach(() => {
      listTechPartnersServiceStub = sinon.createStubInstance<ListTechPartnersService>(ListTechPartnersService);

      wrapper = shallowMount<ListTechPartnersClass>(ListTechPartnersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listTechPartnersService: () => listTechPartnersServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.listTechPartners = entity;
        listTechPartnersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listTechPartnersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.listTechPartners = entity;
        listTechPartnersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listTechPartnersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
