/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TechInfoDetailComponent from '@/entities/tech-info/tech-info-details.vue';
import TechInfoClass from '@/entities/tech-info/tech-info-details.component';
import TechInfoService from '@/entities/tech-info/tech-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TechInfo Management Detail Component', () => {
    let wrapper: Wrapper<TechInfoClass>;
    let comp: TechInfoClass;
    let techInfoServiceStub: SinonStubbedInstance<TechInfoService>;

    beforeEach(() => {
      techInfoServiceStub = sinon.createStubInstance<TechInfoService>(TechInfoService);

      wrapper = shallowMount<TechInfoClass>(TechInfoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { techInfoService: () => techInfoServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTechInfo = { id: 123 };
        techInfoServiceStub.find.resolves(foundTechInfo);

        // WHEN
        comp.retrieveTechInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.techInfo).toBe(foundTechInfo);
      });
    });
  });
});
