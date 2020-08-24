/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListToolsDetailComponent from '@/entities/list-tools/list-tools-details.vue';
import ListToolsClass from '@/entities/list-tools/list-tools-details.component';
import ListToolsService from '@/entities/list-tools/list-tools.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListTools Management Detail Component', () => {
    let wrapper: Wrapper<ListToolsClass>;
    let comp: ListToolsClass;
    let listToolsServiceStub: SinonStubbedInstance<ListToolsService>;

    beforeEach(() => {
      listToolsServiceStub = sinon.createStubInstance<ListToolsService>(ListToolsService);

      wrapper = shallowMount<ListToolsClass>(ListToolsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listToolsService: () => listToolsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListTools = { id: 123 };
        listToolsServiceStub.find.resolves(foundListTools);

        // WHEN
        comp.retrieveListTools(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listTools).toBe(foundListTools);
      });
    });
  });
});
