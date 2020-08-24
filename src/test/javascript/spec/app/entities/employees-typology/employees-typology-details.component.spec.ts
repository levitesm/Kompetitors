/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EmployeesTypologyDetailComponent from '@/entities/employees-typology/employees-typology-details.vue';
import EmployeesTypologyClass from '@/entities/employees-typology/employees-typology-details.component';
import EmployeesTypologyService from '@/entities/employees-typology/employees-typology.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EmployeesTypology Management Detail Component', () => {
    let wrapper: Wrapper<EmployeesTypologyClass>;
    let comp: EmployeesTypologyClass;
    let employeesTypologyServiceStub: SinonStubbedInstance<EmployeesTypologyService>;

    beforeEach(() => {
      employeesTypologyServiceStub = sinon.createStubInstance<EmployeesTypologyService>(EmployeesTypologyService);

      wrapper = shallowMount<EmployeesTypologyClass>(EmployeesTypologyDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { employeesTypologyService: () => employeesTypologyServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmployeesTypology = { id: 123 };
        employeesTypologyServiceStub.find.resolves(foundEmployeesTypology);

        // WHEN
        comp.retrieveEmployeesTypology(123);
        await comp.$nextTick();

        // THEN
        expect(comp.employeesTypology).toBe(foundEmployeesTypology);
      });
    });
  });
});
