/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeeTypeComponent from '@/entities/employee-type/employee-type.vue';
import EmployeeTypeClass from '@/entities/employee-type/employee-type.component';
import EmployeeTypeService from '@/entities/employee-type/employee-type.service';

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
  describe('EmployeeType Management Component', () => {
    let wrapper: Wrapper<EmployeeTypeClass>;
    let comp: EmployeeTypeClass;
    let employeeTypeServiceStub: SinonStubbedInstance<EmployeeTypeService>;

    beforeEach(() => {
      employeeTypeServiceStub = sinon.createStubInstance<EmployeeTypeService>(EmployeeTypeService);
      employeeTypeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmployeeTypeClass>(EmployeeTypeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          employeeTypeService: () => employeeTypeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      employeeTypeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmployeeTypes();
      await comp.$nextTick();

      // THEN
      expect(employeeTypeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.employeeTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      employeeTypeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEmployeeType();
      await comp.$nextTick();

      // THEN
      expect(employeeTypeServiceStub.delete.called).toBeTruthy();
      expect(employeeTypeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
