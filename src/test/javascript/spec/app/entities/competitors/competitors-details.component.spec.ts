/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitorsDetailComponent from '@/entities/competitors/competitors-details.vue';
import CompetitorsClass from '@/entities/competitors/competitors-details.component';
import CompetitorsService from '@/entities/competitors/competitors.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Competitors Management Detail Component', () => {
    let wrapper: Wrapper<CompetitorsClass>;
    let comp: CompetitorsClass;
    let competitorsServiceStub: SinonStubbedInstance<CompetitorsService>;

    beforeEach(() => {
      competitorsServiceStub = sinon.createStubInstance<CompetitorsService>(CompetitorsService);

      wrapper = shallowMount<CompetitorsClass>(CompetitorsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitorsService: () => competitorsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitors = { id: 123 };
        competitorsServiceStub.find.resolves(foundCompetitors);

        // WHEN
        comp.retrieveCompetitors(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitors).toBe(foundCompetitors);
      });
    });
  });
});
