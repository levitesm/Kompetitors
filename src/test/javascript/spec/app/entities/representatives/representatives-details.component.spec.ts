/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RepresentativesDetailComponent from '@/entities/representatives/representatives-details.vue';
import RepresentativesClass from '@/entities/representatives/representatives-details.component';
import RepresentativesService from '@/entities/representatives/representatives.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Representatives Management Detail Component', () => {
    let wrapper: Wrapper<RepresentativesClass>;
    let comp: RepresentativesClass;
    let representativesServiceStub: SinonStubbedInstance<RepresentativesService>;

    beforeEach(() => {
      representativesServiceStub = sinon.createStubInstance<RepresentativesService>(RepresentativesService);

      wrapper = shallowMount<RepresentativesClass>(RepresentativesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { representativesService: () => representativesServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRepresentatives = { id: 123 };
        representativesServiceStub.find.resolves(foundRepresentatives);

        // WHEN
        comp.retrieveRepresentatives(123);
        await comp.$nextTick();

        // THEN
        expect(comp.representatives).toBe(foundRepresentatives);
      });
    });
  });
});
