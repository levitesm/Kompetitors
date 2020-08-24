/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeeSalariesUpdateComponent from '@/entities/employee-salaries/employee-salaries-update.vue';
import EmployeeSalariesClass from '@/entities/employee-salaries/employee-salaries-update.component';
import EmployeeSalariesService from '@/entities/employee-salaries/employee-salaries.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('EmployeeSalaries Management Update Component', () => {
    let wrapper: Wrapper<EmployeeSalariesClass>;
    let comp: EmployeeSalariesClass;
    let employeeSalariesServiceStub: SinonStubbedInstance<EmployeeSalariesService>;

    beforeEach(() => {
      employeeSalariesServiceStub = sinon.createStubInstance<EmployeeSalariesService>(EmployeeSalariesService);

      wrapper = shallowMount<EmployeeSalariesClass>(EmployeeSalariesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          employeeSalariesService: () => employeeSalariesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.employeeSalaries = entity;
        employeeSalariesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(employeeSalariesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.employeeSalaries = entity;
        employeeSalariesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(employeeSalariesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
