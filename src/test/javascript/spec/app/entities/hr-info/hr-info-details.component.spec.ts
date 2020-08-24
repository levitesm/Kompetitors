/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import HrInfoDetailComponent from '@/entities/hr-info/hr-info-details.vue';
import HrInfoClass from '@/entities/hr-info/hr-info-details.component';
import HrInfoService from '@/entities/hr-info/hr-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('HrInfo Management Detail Component', () => {
    let wrapper: Wrapper<HrInfoClass>;
    let comp: HrInfoClass;
    let hrInfoServiceStub: SinonStubbedInstance<HrInfoService>;

    beforeEach(() => {
      hrInfoServiceStub = sinon.createStubInstance<HrInfoService>(HrInfoService);

      wrapper = shallowMount<HrInfoClass>(HrInfoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { hrInfoService: () => hrInfoServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHrInfo = { id: 123 };
        hrInfoServiceStub.find.resolves(foundHrInfo);

        // WHEN
        comp.retrieveHrInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.hrInfo).toBe(foundHrInfo);
      });
    });
  });
});
