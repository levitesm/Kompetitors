/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ShareHoldersDetailComponent from '@/entities/share-holders/share-holders-details.vue';
import ShareHoldersClass from '@/entities/share-holders/share-holders-details.component';
import ShareHoldersService from '@/entities/share-holders/share-holders.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ShareHolders Management Detail Component', () => {
    let wrapper: Wrapper<ShareHoldersClass>;
    let comp: ShareHoldersClass;
    let shareHoldersServiceStub: SinonStubbedInstance<ShareHoldersService>;

    beforeEach(() => {
      shareHoldersServiceStub = sinon.createStubInstance<ShareHoldersService>(ShareHoldersService);

      wrapper = shallowMount<ShareHoldersClass>(ShareHoldersDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { shareHoldersService: () => shareHoldersServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundShareHolders = { id: 123 };
        shareHoldersServiceStub.find.resolves(foundShareHolders);

        // WHEN
        comp.retrieveShareHolders(123);
        await comp.$nextTick();

        // THEN
        expect(comp.shareHolders).toBe(foundShareHolders);
      });
    });
  });
});
