/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListCompetanciesDetailComponent from '@/entities/list-competancies/list-competancies-details.vue';
import ListCompetanciesClass from '@/entities/list-competancies/list-competancies-details.component';
import ListCompetanciesService from '@/entities/list-competancies/list-competancies.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListCompetancies Management Detail Component', () => {
    let wrapper: Wrapper<ListCompetanciesClass>;
    let comp: ListCompetanciesClass;
    let listCompetanciesServiceStub: SinonStubbedInstance<ListCompetanciesService>;

    beforeEach(() => {
      listCompetanciesServiceStub = sinon.createStubInstance<ListCompetanciesService>(ListCompetanciesService);

      wrapper = shallowMount<ListCompetanciesClass>(ListCompetanciesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listCompetanciesService: () => listCompetanciesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListCompetancies = { id: 123 };
        listCompetanciesServiceStub.find.resolves(foundListCompetancies);

        // WHEN
        comp.retrieveListCompetancies(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listCompetancies).toBe(foundListCompetancies);
      });
    });
  });
});
