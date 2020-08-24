/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TechServicesDetailComponent from '@/entities/tech-services/tech-services-details.vue';
import TechServicesClass from '@/entities/tech-services/tech-services-details.component';
import TechServicesService from '@/entities/tech-services/tech-services.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TechServices Management Detail Component', () => {
    let wrapper: Wrapper<TechServicesClass>;
    let comp: TechServicesClass;
    let techServicesServiceStub: SinonStubbedInstance<TechServicesService>;

    beforeEach(() => {
      techServicesServiceStub = sinon.createStubInstance<TechServicesService>(TechServicesService);

      wrapper = shallowMount<TechServicesClass>(TechServicesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { techServicesService: () => techServicesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTechServices = { id: 123 };
        techServicesServiceStub.find.resolves(foundTechServices);

        // WHEN
        comp.retrieveTechServices(123);
        await comp.$nextTick();

        // THEN
        expect(comp.techServices).toBe(foundTechServices);
      });
    });
  });
});
