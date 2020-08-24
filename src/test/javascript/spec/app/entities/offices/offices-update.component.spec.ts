/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import OfficesUpdateComponent from '@/entities/offices/offices-update.vue';
import OfficesClass from '@/entities/offices/offices-update.component';
import OfficesService from '@/entities/offices/offices.service';

import ClientsService from '@/entities/clients/clients.service';

import TechCompetanciesService from '@/entities/tech-competancies/tech-competancies.service';

import TechPartnersService from '@/entities/tech-partners/tech-partners.service';

import TechProjectsService from '@/entities/tech-projects/tech-projects.service';

import TechServicesService from '@/entities/tech-services/tech-services.service';

import TechToolsService from '@/entities/tech-tools/tech-tools.service';

import ListCitiesService from '@/entities/list-cities/list-cities.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Offices Management Update Component', () => {
    let wrapper: Wrapper<OfficesClass>;
    let comp: OfficesClass;
    let officesServiceStub: SinonStubbedInstance<OfficesService>;

    beforeEach(() => {
      officesServiceStub = sinon.createStubInstance<OfficesService>(OfficesService);

      wrapper = shallowMount<OfficesClass>(OfficesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          officesService: () => officesServiceStub,

          clientsService: () => new ClientsService(),

          techCompetanciesService: () => new TechCompetanciesService(),

          techPartnersService: () => new TechPartnersService(),

          techProjectsService: () => new TechProjectsService(),

          techServicesService: () => new TechServicesService(),

          techToolsService: () => new TechToolsService(),

          listCitiesService: () => new ListCitiesService(),

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.offices = entity;
        officesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.offices = entity;
        officesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(officesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
