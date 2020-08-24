/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EmployeeTypeDetailComponent from '@/entities/employee-type/employee-type-details.vue';
import EmployeeTypeClass from '@/entities/employee-type/employee-type-details.component';
import EmployeeTypeService from '@/entities/employee-type/employee-type.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EmployeeType Management Detail Component', () => {
    let wrapper: Wrapper<EmployeeTypeClass>;
    let comp: EmployeeTypeClass;
    let employeeTypeServiceStub: SinonStubbedInstance<EmployeeTypeService>;

    beforeEach(() => {
      employeeTypeServiceStub = sinon.createStubInstance<EmployeeTypeService>(EmployeeTypeService);

      wrapper = shallowMount<EmployeeTypeClass>(EmployeeTypeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { employeeTypeService: () => employeeTypeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmployeeType = { id: 123 };
        employeeTypeServiceStub.find.resolves(foundEmployeeType);

        // WHEN
        comp.retrieveEmployeeType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.employeeType).toBe(foundEmployeeType);
      });
    });
  });
});
