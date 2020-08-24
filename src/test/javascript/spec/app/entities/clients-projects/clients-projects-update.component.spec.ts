/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ClientsProjectsUpdateComponent from '@/entities/clients-projects/clients-projects-update.vue';
import ClientsProjectsClass from '@/entities/clients-projects/clients-projects-update.component';
import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';

import ListClientsProjectTypesService from '@/entities/list-clients-project-types/list-clients-project-types.service';

import ClientsService from '@/entities/clients/clients.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ClientsProjects Management Update Component', () => {
    let wrapper: Wrapper<ClientsProjectsClass>;
    let comp: ClientsProjectsClass;
    let clientsProjectsServiceStub: SinonStubbedInstance<ClientsProjectsService>;

    beforeEach(() => {
      clientsProjectsServiceStub = sinon.createStubInstance<ClientsProjectsService>(ClientsProjectsService);

      wrapper = shallowMount<ClientsProjectsClass>(ClientsProjectsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          clientsProjectsService: () => clientsProjectsServiceStub,

          listClientsProjectTypesService: () => new ListClientsProjectTypesService(),

          clientsService: () => new ClientsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.clientsProjects = entity;
        clientsProjectsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientsProjectsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.clientsProjects = entity;
        clientsProjectsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clientsProjectsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
