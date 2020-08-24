/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import WorkforceDetailComponent from '@/entities/workforce/workforce-details.vue';
import WorkforceClass from '@/entities/workforce/workforce-details.component';
import WorkforceService from '@/entities/workforce/workforce.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Workforce Management Detail Component', () => {
    let wrapper: Wrapper<WorkforceClass>;
    let comp: WorkforceClass;
    let workforceServiceStub: SinonStubbedInstance<WorkforceService>;

    beforeEach(() => {
      workforceServiceStub = sinon.createStubInstance<WorkforceService>(WorkforceService);

      wrapper = shallowMount<WorkforceClass>(WorkforceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { workforceService: () => workforceServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWorkforce = { id: 123 };
        workforceServiceStub.find.resolves(foundWorkforce);

        // WHEN
        comp.retrieveWorkforce(123);
        await comp.$nextTick();

        // THEN
        expect(comp.workforce).toBe(foundWorkforce);
      });
    });
  });
});
