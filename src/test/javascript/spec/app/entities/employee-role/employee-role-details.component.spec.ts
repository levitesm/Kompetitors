/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EmployeeRoleDetailComponent from '@/entities/employee-role/employee-role-details.vue';
import EmployeeRoleClass from '@/entities/employee-role/employee-role-details.component';
import EmployeeRoleService from '@/entities/employee-role/employee-role.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EmployeeRole Management Detail Component', () => {
    let wrapper: Wrapper<EmployeeRoleClass>;
    let comp: EmployeeRoleClass;
    let employeeRoleServiceStub: SinonStubbedInstance<EmployeeRoleService>;

    beforeEach(() => {
      employeeRoleServiceStub = sinon.createStubInstance<EmployeeRoleService>(EmployeeRoleService);

      wrapper = shallowMount<EmployeeRoleClass>(EmployeeRoleDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { employeeRoleService: () => employeeRoleServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmployeeRole = { id: 123 };
        employeeRoleServiceStub.find.resolves(foundEmployeeRole);

        // WHEN
        comp.retrieveEmployeeRole(123);
        await comp.$nextTick();

        // THEN
        expect(comp.employeeRole).toBe(foundEmployeeRole);
      });
    });
  });
});
