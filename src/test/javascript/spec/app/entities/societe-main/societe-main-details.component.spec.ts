/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SocieteMainDetailComponent from '@/entities/societe-main/societe-main-details.vue';
import SocieteMainClass from '@/entities/societe-main/societe-main-details.component';
import SocieteMainService from '@/entities/societe-main/societe-main.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SocieteMain Management Detail Component', () => {
    let wrapper: Wrapper<SocieteMainClass>;
    let comp: SocieteMainClass;
    let societeMainServiceStub: SinonStubbedInstance<SocieteMainService>;

    beforeEach(() => {
      societeMainServiceStub = sinon.createStubInstance<SocieteMainService>(SocieteMainService);

      wrapper = shallowMount<SocieteMainClass>(SocieteMainDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { societeMainService: () => societeMainServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSocieteMain = { id: 123 };
        societeMainServiceStub.find.resolves(foundSocieteMain);

        // WHEN
        comp.retrieveSocieteMain(123);
        await comp.$nextTick();

        // THEN
        expect(comp.societeMain).toBe(foundSocieteMain);
      });
    });
  });
});
