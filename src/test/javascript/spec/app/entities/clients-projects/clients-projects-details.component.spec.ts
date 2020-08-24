/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ClientsProjectsDetailComponent from '@/entities/clients-projects/clients-projects-details.vue';
import ClientsProjectsClass from '@/entities/clients-projects/clients-projects-details.component';
import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ClientsProjects Management Detail Component', () => {
    let wrapper: Wrapper<ClientsProjectsClass>;
    let comp: ClientsProjectsClass;
    let clientsProjectsServiceStub: SinonStubbedInstance<ClientsProjectsService>;

    beforeEach(() => {
      clientsProjectsServiceStub = sinon.createStubInstance<ClientsProjectsService>(ClientsProjectsService);

      wrapper = shallowMount<ClientsProjectsClass>(ClientsProjectsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { clientsProjectsService: () => clientsProjectsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundClientsProjects = { id: 123 };
        clientsProjectsServiceStub.find.resolves(foundClientsProjects);

        // WHEN
        comp.retrieveClientsProjects(123);
        await comp.$nextTick();

        // THEN
        expect(comp.clientsProjects).toBe(foundClientsProjects);
      });
    });
  });
});
