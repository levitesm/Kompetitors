/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AccessKeyDetailComponent from '@/entities/access-key/access-key-details.vue';
import AccessKeyClass from '@/entities/access-key/access-key-details.component';
import AccessKeyService from '@/entities/access-key/access-key.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AccessKey Management Detail Component', () => {
    let wrapper: Wrapper<AccessKeyClass>;
    let comp: AccessKeyClass;
    let accessKeyServiceStub: SinonStubbedInstance<AccessKeyService>;

    beforeEach(() => {
      accessKeyServiceStub = sinon.createStubInstance<AccessKeyService>(AccessKeyService);

      wrapper = shallowMount<AccessKeyClass>(AccessKeyDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { accessKeyService: () => accessKeyServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAccessKey = { id: 123 };
        accessKeyServiceStub.find.resolves(foundAccessKey);

        // WHEN
        comp.retrieveAccessKey(123);
        await comp.$nextTick();

        // THEN
        expect(comp.accessKey).toBe(foundAccessKey);
      });
    });
  });
});
