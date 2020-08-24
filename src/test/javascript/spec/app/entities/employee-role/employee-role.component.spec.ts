/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeeRoleComponent from '@/entities/employee-role/employee-role.vue';
import EmployeeRoleClass from '@/entities/employee-role/employee-role.component';
import EmployeeRoleService from '@/entities/employee-role/employee-role.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('EmployeeRole Management Component', () => {
    let wrapper: Wrapper<EmployeeRoleClass>;
    let comp: EmployeeRoleClass;
    let employeeRoleServiceStub: SinonStubbedInstance<EmployeeRoleService>;

    beforeEach(() => {
      employeeRoleServiceStub = sinon.createStubInstance<EmployeeRoleService>(EmployeeRoleService);
      employeeRoleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmployeeRoleClass>(EmployeeRoleComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          employeeRoleService: () => employeeRoleServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      employeeRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmployeeRoles();
      await comp.$nextTick();

      // THEN
      expect(employeeRoleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.employeeRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      employeeRoleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEmployeeRole();
      await comp.$nextTick();

      // THEN
      expect(employeeRoleServiceStub.delete.called).toBeTruthy();
      expect(employeeRoleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
