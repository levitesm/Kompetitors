/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PrInfoDetailComponent from '@/entities/pr-info/pr-info-details.vue';
import PrInfoClass from '@/entities/pr-info/pr-info-details.component';
import PrInfoService from '@/entities/pr-info/pr-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PrInfo Management Detail Component', () => {
    let wrapper: Wrapper<PrInfoClass>;
    let comp: PrInfoClass;
    let prInfoServiceStub: SinonStubbedInstance<PrInfoService>;

    beforeEach(() => {
      prInfoServiceStub = sinon.createStubInstance<PrInfoService>(PrInfoService);

      wrapper = shallowMount<PrInfoClass>(PrInfoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { prInfoService: () => prInfoServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrInfo = { id: 123 };
        prInfoServiceStub.find.resolves(foundPrInfo);

        // WHEN
        comp.retrievePrInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.prInfo).toBe(foundPrInfo);
      });
    });
  });
});
