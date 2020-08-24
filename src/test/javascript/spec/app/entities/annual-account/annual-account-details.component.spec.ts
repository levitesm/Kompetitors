/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AnnualAccountDetailComponent from '@/entities/annual-account/annual-account-details.vue';
import AnnualAccountClass from '@/entities/annual-account/annual-account-details.component';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnnualAccount Management Detail Component', () => {
    let wrapper: Wrapper<AnnualAccountClass>;
    let comp: AnnualAccountClass;
    let annualAccountServiceStub: SinonStubbedInstance<AnnualAccountService>;

    beforeEach(() => {
      annualAccountServiceStub = sinon.createStubInstance<AnnualAccountService>(AnnualAccountService);

      wrapper = shallowMount<AnnualAccountClass>(AnnualAccountDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { annualAccountService: () => annualAccountServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnnualAccount = { id: 123 };
        annualAccountServiceStub.find.resolves(foundAnnualAccount);

        // WHEN
        comp.retrieveAnnualAccount(123);
        await comp.$nextTick();

        // THEN
        expect(comp.annualAccount).toBe(foundAnnualAccount);
      });
    });
  });
});
