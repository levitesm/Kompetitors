/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeeSalariesComponent from '@/entities/employee-salaries/employee-salaries.vue';
import EmployeeSalariesClass from '@/entities/employee-salaries/employee-salaries.component';
import EmployeeSalariesService from '@/entities/employee-salaries/employee-salaries.service';

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
  describe('EmployeeSalaries Management Component', () => {
    let wrapper: Wrapper<EmployeeSalariesClass>;
    let comp: EmployeeSalariesClass;
    let employeeSalariesServiceStub: SinonStubbedInstance<EmployeeSalariesService>;

    beforeEach(() => {
      employeeSalariesServiceStub = sinon.createStubInstance<EmployeeSalariesService>(EmployeeSalariesService);
      employeeSalariesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmployeeSalariesClass>(EmployeeSalariesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          employeeSalariesService: () => employeeSalariesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      employeeSalariesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmployeeSalariess();
      await comp.$nextTick();

      // THEN
      expect(employeeSalariesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.employeeSalaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      employeeSalariesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEmployeeSalaries();
      await comp.$nextTick();

      // THEN
      expect(employeeSalariesServiceStub.delete.called).toBeTruthy();
      expect(employeeSalariesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
