/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TechCompetanciesDetailComponent from '@/entities/tech-competancies/tech-competancies-details.vue';
import TechCompetanciesClass from '@/entities/tech-competancies/tech-competancies-details.component';
import TechCompetanciesService from '@/entities/tech-competancies/tech-competancies.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TechCompetancies Management Detail Component', () => {
    let wrapper: Wrapper<TechCompetanciesClass>;
    let comp: TechCompetanciesClass;
    let techCompetanciesServiceStub: SinonStubbedInstance<TechCompetanciesService>;

    beforeEach(() => {
      techCompetanciesServiceStub = sinon.createStubInstance<TechCompetanciesService>(TechCompetanciesService);

      wrapper = shallowMount<TechCompetanciesClass>(TechCompetanciesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { techCompetanciesService: () => techCompetanciesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTechCompetancies = { id: 123 };
        techCompetanciesServiceStub.find.resolves(foundTechCompetancies);

        // WHEN
        comp.retrieveTechCompetancies(123);
        await comp.$nextTick();

        // THEN
        expect(comp.techCompetancies).toBe(foundTechCompetancies);
      });
    });
  });
});
