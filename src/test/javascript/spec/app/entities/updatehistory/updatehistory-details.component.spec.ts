/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UpdatehistoryDetailComponent from '@/entities/updatehistory/updatehistory-details.vue';
import UpdatehistoryClass from '@/entities/updatehistory/updatehistory-details.component';
import UpdatehistoryService from '@/entities/updatehistory/updatehistory.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Updatehistory Management Detail Component', () => {
    let wrapper: Wrapper<UpdatehistoryClass>;
    let comp: UpdatehistoryClass;
    let updatehistoryServiceStub: SinonStubbedInstance<UpdatehistoryService>;

    beforeEach(() => {
      updatehistoryServiceStub = sinon.createStubInstance<UpdatehistoryService>(UpdatehistoryService);

      wrapper = shallowMount<UpdatehistoryClass>(UpdatehistoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { updatehistoryService: () => updatehistoryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUpdatehistory = { id: 123 };
        updatehistoryServiceStub.find.resolves(foundUpdatehistory);

        // WHEN
        comp.retrieveUpdatehistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.updatehistory).toBe(foundUpdatehistory);
      });
    });
  });
});
