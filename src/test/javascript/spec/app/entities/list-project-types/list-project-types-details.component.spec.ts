/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListProjectTypesDetailComponent from '@/entities/list-project-types/list-project-types-details.vue';
import ListProjectTypesClass from '@/entities/list-project-types/list-project-types-details.component';
import ListProjectTypesService from '@/entities/list-project-types/list-project-types.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListProjectTypes Management Detail Component', () => {
    let wrapper: Wrapper<ListProjectTypesClass>;
    let comp: ListProjectTypesClass;
    let listProjectTypesServiceStub: SinonStubbedInstance<ListProjectTypesService>;

    beforeEach(() => {
      listProjectTypesServiceStub = sinon.createStubInstance<ListProjectTypesService>(ListProjectTypesService);

      wrapper = shallowMount<ListProjectTypesClass>(ListProjectTypesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listProjectTypesService: () => listProjectTypesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListProjectTypes = { id: 123 };
        listProjectTypesServiceStub.find.resolves(foundListProjectTypes);

        // WHEN
        comp.retrieveListProjectTypes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listProjectTypes).toBe(foundListProjectTypes);
      });
    });
  });
});
