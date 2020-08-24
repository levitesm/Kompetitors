/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeesTypologyUpdateComponent from '@/entities/employees-typology/employees-typology-update.vue';
import EmployeesTypologyClass from '@/entities/employees-typology/employees-typology-update.component';
import EmployeesTypologyService from '@/entities/employees-typology/employees-typology.service';

import EmployeeTypeService from '@/entities/employee-type/employee-type.service';

import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('EmployeesTypology Management Update Component', () => {
    let wrapper: Wrapper<EmployeesTypologyClass>;
    let comp: EmployeesTypologyClass;
    let employeesTypologyServiceStub: SinonStubbedInstance<EmployeesTypologyService>;

    beforeEach(() => {
      employeesTypologyServiceStub = sinon.createStubInstance<EmployeesTypologyService>(EmployeesTypologyService);

      wrapper = shallowMount<EmployeesTypologyClass>(EmployeesTypologyUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          employeesTypologyService: () => employeesTypologyServiceStub,

          employeeTypeService: () => new EmployeeTypeService(),

          competitorsService: () => new CompetitorsService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.employeesTypology = entity;
        employeesTypologyServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(employeesTypologyServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.employeesTypology = entity;
        employeesTypologyServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(employeesTypologyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
