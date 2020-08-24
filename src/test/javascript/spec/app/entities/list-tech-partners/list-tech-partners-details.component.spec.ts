/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListTechPartnersDetailComponent from '@/entities/list-tech-partners/list-tech-partners-details.vue';
import ListTechPartnersClass from '@/entities/list-tech-partners/list-tech-partners-details.component';
import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListTechPartners Management Detail Component', () => {
    let wrapper: Wrapper<ListTechPartnersClass>;
    let comp: ListTechPartnersClass;
    let listTechPartnersServiceStub: SinonStubbedInstance<ListTechPartnersService>;

    beforeEach(() => {
      listTechPartnersServiceStub = sinon.createStubInstance<ListTechPartnersService>(ListTechPartnersService);

      wrapper = shallowMount<ListTechPartnersClass>(ListTechPartnersDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listTechPartnersService: () => listTechPartnersServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListTechPartners = { id: 123 };
        listTechPartnersServiceStub.find.resolves(foundListTechPartners);

        // WHEN
        comp.retrieveListTechPartners(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listTechPartners).toBe(foundListTechPartners);
      });
    });
  });
});
