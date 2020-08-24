/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import InfogreffeDetailComponent from '@/entities/infogreffe/infogreffe-details.vue';
import InfogreffeClass from '@/entities/infogreffe/infogreffe-details.component';
import InfogreffeService from '@/entities/infogreffe/infogreffe.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Infogreffe Management Detail Component', () => {
    let wrapper: Wrapper<InfogreffeClass>;
    let comp: InfogreffeClass;
    let infogreffeServiceStub: SinonStubbedInstance<InfogreffeService>;

    beforeEach(() => {
      infogreffeServiceStub = sinon.createStubInstance<InfogreffeService>(InfogreffeService);

      wrapper = shallowMount<InfogreffeClass>(InfogreffeDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { infogreffeService: () => infogreffeServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundInfogreffe = { id: 123 };
        infogreffeServiceStub.find.resolves(foundInfogreffe);

        // WHEN
        comp.retrieveInfogreffe(123);
        await comp.$nextTick();

        // THEN
        expect(comp.infogreffe).toBe(foundInfogreffe);
      });
    });
  });
});
