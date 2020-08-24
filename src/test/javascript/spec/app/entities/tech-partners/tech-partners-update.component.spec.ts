/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechPartnersUpdateComponent from '@/entities/tech-partners/tech-partners-update.vue';
import TechPartnersClass from '@/entities/tech-partners/tech-partners-update.component';
import TechPartnersService from '@/entities/tech-partners/tech-partners.service';

import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TechPartners Management Update Component', () => {
    let wrapper: Wrapper<TechPartnersClass>;
    let comp: TechPartnersClass;
    let techPartnersServiceStub: SinonStubbedInstance<TechPartnersService>;

    beforeEach(() => {
      techPartnersServiceStub = sinon.createStubInstance<TechPartnersService>(TechPartnersService);

      wrapper = shallowMount<TechPartnersClass>(TechPartnersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          techPartnersService: () => techPartnersServiceStub,

          listTechPartnersService: () => new ListTechPartnersService(),

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.techPartners = entity;
        techPartnersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techPartnersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.techPartners = entity;
        techPartnersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(techPartnersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
