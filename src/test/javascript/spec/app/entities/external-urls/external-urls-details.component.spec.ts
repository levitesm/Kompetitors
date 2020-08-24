/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ExternalUrlsDetailComponent from '@/entities/external-urls/external-urls-details.vue';
import ExternalUrlsClass from '@/entities/external-urls/external-urls-details.component';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ExternalUrls Management Detail Component', () => {
    let wrapper: Wrapper<ExternalUrlsClass>;
    let comp: ExternalUrlsClass;
    let externalUrlsServiceStub: SinonStubbedInstance<ExternalUrlsService>;

    beforeEach(() => {
      externalUrlsServiceStub = sinon.createStubInstance<ExternalUrlsService>(ExternalUrlsService);

      wrapper = shallowMount<ExternalUrlsClass>(ExternalUrlsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { externalUrlsService: () => externalUrlsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundExternalUrls = { id: 123 };
        externalUrlsServiceStub.find.resolves(foundExternalUrls);

        // WHEN
        comp.retrieveExternalUrls(123);
        await comp.$nextTick();

        // THEN
        expect(comp.externalUrls).toBe(foundExternalUrls);
      });
    });
  });
});
