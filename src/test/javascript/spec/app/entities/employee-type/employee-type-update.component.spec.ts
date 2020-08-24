/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeeTypeUpdateComponent from '@/entities/employee-type/employee-type-update.vue';
import EmployeeTypeClass from '@/entities/employee-type/employee-type-update.component';
import EmployeeTypeService from '@/entities/employee-type/employee-type.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('EmployeeType Management Update Component', () => {
    let wrapper: Wrapper<EmployeeTypeClass>;
    let comp: EmployeeTypeClass;
    let employeeTypeServiceStub: SinonStubbedInstance<EmployeeTypeService>;

    beforeEach(() => {
      employeeTypeServiceStub = sinon.createStubInstance<EmployeeTypeService>(EmployeeTypeService);

      wrapper = shallowMount<EmployeeTypeClass>(EmployeeTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          employeeTypeService: () => employeeTypeServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.employeeType = entity;
        employeeTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(employeeTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.employeeType = entity;
        employeeTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(employeeTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
