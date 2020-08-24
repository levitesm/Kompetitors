/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ClientsDetailComponent from '@/entities/clients/clients-details.vue';
import ClientsClass from '@/entities/clients/clients-details.component';
import ClientsService from '@/entities/clients/clients.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Clients Management Detail Component', () => {
    let wrapper: Wrapper<ClientsClass>;
    let comp: ClientsClass;
    let clientsServiceStub: SinonStubbedInstance<ClientsService>;

    beforeEach(() => {
      clientsServiceStub = sinon.createStubInstance<ClientsService>(ClientsService);

      wrapper = shallowMount<ClientsClass>(ClientsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { clientsService: () => clientsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundClients = { id: 123 };
        clientsServiceStub.find.resolves(foundClients);

        // WHEN
        comp.retrieveClients(123);
        await comp.$nextTick();

        // THEN
        expect(comp.clients).toBe(foundClients);
      });
    });
  });
});
