/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListClientsProjectTypesDetailComponent from '@/entities/list-clients-project-types/list-clients-project-types-details.vue';
import ListClientsProjectTypesClass from '@/entities/list-clients-project-types/list-clients-project-types-details.component';
import ListClientsProjectTypesService from '@/entities/list-clients-project-types/list-clients-project-types.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListClientsProjectTypes Management Detail Component', () => {
    let wrapper: Wrapper<ListClientsProjectTypesClass>;
    let comp: ListClientsProjectTypesClass;
    let listClientsProjectTypesServiceStub: SinonStubbedInstance<ListClientsProjectTypesService>;

    beforeEach(() => {
      listClientsProjectTypesServiceStub = sinon.createStubInstance<ListClientsProjectTypesService>(ListClientsProjectTypesService);

      wrapper = shallowMount<ListClientsProjectTypesClass>(ListClientsProjectTypesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listClientsProjectTypesService: () => listClientsProjectTypesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListClientsProjectTypes = { id: 123 };
        listClientsProjectTypesServiceStub.find.resolves(foundListClientsProjectTypes);

        // WHEN
        comp.retrieveListClientsProjectTypes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listClientsProjectTypes).toBe(foundListClientsProjectTypes);
      });
    });
  });
});
