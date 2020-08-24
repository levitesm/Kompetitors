/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RepresentativesUpdateComponent from '@/entities/representatives/representatives-update.vue';
import RepresentativesClass from '@/entities/representatives/representatives-update.component';
import RepresentativesService from '@/entities/representatives/representatives.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Representatives Management Update Component', () => {
    let wrapper: Wrapper<RepresentativesClass>;
    let comp: RepresentativesClass;
    let representativesServiceStub: SinonStubbedInstance<RepresentativesService>;

    beforeEach(() => {
      representativesServiceStub = sinon.createStubInstance<RepresentativesService>(RepresentativesService);

      wrapper = shallowMount<RepresentativesClass>(RepresentativesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          representativesService: () => representativesServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.representatives = entity;
        representativesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(representativesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.representatives = entity;
        representativesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(representativesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
