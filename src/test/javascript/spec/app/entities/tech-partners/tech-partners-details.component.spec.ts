/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TechPartnersDetailComponent from '@/entities/tech-partners/tech-partners-details.vue';
import TechPartnersClass from '@/entities/tech-partners/tech-partners-details.component';
import TechPartnersService from '@/entities/tech-partners/tech-partners.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TechPartners Management Detail Component', () => {
    let wrapper: Wrapper<TechPartnersClass>;
    let comp: TechPartnersClass;
    let techPartnersServiceStub: SinonStubbedInstance<TechPartnersService>;

    beforeEach(() => {
      techPartnersServiceStub = sinon.createStubInstance<TechPartnersService>(TechPartnersService);

      wrapper = shallowMount<TechPartnersClass>(TechPartnersDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { techPartnersService: () => techPartnersServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTechPartners = { id: 123 };
        techPartnersServiceStub.find.resolves(foundTechPartners);

        // WHEN
        comp.retrieveTechPartners(123);
        await comp.$nextTick();

        // THEN
        expect(comp.techPartners).toBe(foundTechPartners);
      });
    });
  });
});
