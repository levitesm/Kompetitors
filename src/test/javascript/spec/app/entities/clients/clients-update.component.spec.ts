/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ClientsUpdateComponent from '@/entities/clients/clients-update.vue';
import ClientsClass from '@/entities/clients/clients-update.component';
import ClientsService from '@/entities/clients/clients.service';

import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';

import ListIndustriesService from '@/entities/list-industries/list-industries.service';

import OfficesService from '@/entities/offices/offices.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Clients Management Update Component', () => {
    let wrapper: Wrapper<ClientsClass>;
    let comp: ClientsClass;
    let clientsServiceStub: SinonStubbedInstance<ClientsService>;

    beforeEach(() => {
      clientsServiceStub = sinon.createStubInstance<ClientsService>(ClientsService);

      wrapper = shallowMount<ClientsClass>(ClientsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          clientsService: () => clientsServiceStub,

          clientsProjectsService: () => new ClientsProjectsService(),

          listIndustriesService: () => new ListIndustriesService(),

          officesService: () => new OfficesService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.clients = entity;
        clientsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.clients = entity;
        clientsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
