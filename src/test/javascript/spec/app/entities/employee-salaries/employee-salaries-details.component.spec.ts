/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EmployeeSalariesDetailComponent from '@/entities/employee-salaries/employee-salaries-details.vue';
import EmployeeSalariesClass from '@/entities/employee-salaries/employee-salaries-details.component';
import EmployeeSalariesService from '@/entities/employee-salaries/employee-salaries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EmployeeSalaries Management Detail Component', () => {
    let wrapper: Wrapper<EmployeeSalariesClass>;
    let comp: EmployeeSalariesClass;
    let employeeSalariesServiceStub: SinonStubbedInstance<EmployeeSalariesService>;

    beforeEach(() => {
      employeeSalariesServiceStub = sinon.createStubInstance<EmployeeSalariesService>(EmployeeSalariesService);

      wrapper = shallowMount<EmployeeSalariesClass>(EmployeeSalariesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { employeeSalariesService: () => employeeSalariesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmployeeSalaries = { id: 123 };
        employeeSalariesServiceStub.find.resolves(foundEmployeeSalaries);

        // WHEN
        comp.retrieveEmployeeSalaries(123);
        await comp.$nextTick();

        // THEN
        expect(comp.employeeSalaries).toBe(foundEmployeeSalaries);
      });
    });
  });
});
