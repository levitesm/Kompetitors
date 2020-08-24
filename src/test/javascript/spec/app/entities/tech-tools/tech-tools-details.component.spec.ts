/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TechToolsDetailComponent from '@/entities/tech-tools/tech-tools-details.vue';
import TechToolsClass from '@/entities/tech-tools/tech-tools-details.component';
import TechToolsService from '@/entities/tech-tools/tech-tools.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TechTools Management Detail Component', () => {
    let wrapper: Wrapper<TechToolsClass>;
    let comp: TechToolsClass;
    let techToolsServiceStub: SinonStubbedInstance<TechToolsService>;

    beforeEach(() => {
      techToolsServiceStub = sinon.createStubInstance<TechToolsService>(TechToolsService);

      wrapper = shallowMount<TechToolsClass>(TechToolsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { techToolsService: () => techToolsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTechTools = { id: 123 };
        techToolsServiceStub.find.resolves(foundTechTools);

        // WHEN
        comp.retrieveTechTools(123);
        await comp.$nextTick();

        // THEN
        expect(comp.techTools).toBe(foundTechTools);
      });
    });
  });
});
