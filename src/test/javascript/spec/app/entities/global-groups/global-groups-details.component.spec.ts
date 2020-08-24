/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GlobalGroupsDetailComponent from '@/entities/global-groups/global-groups-details.vue';
import GlobalGroupsClass from '@/entities/global-groups/global-groups-details.component';
import GlobalGroupsService from '@/entities/global-groups/global-groups.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GlobalGroups Management Detail Component', () => {
    let wrapper: Wrapper<GlobalGroupsClass>;
    let comp: GlobalGroupsClass;
    let globalGroupsServiceStub: SinonStubbedInstance<GlobalGroupsService>;

    beforeEach(() => {
      globalGroupsServiceStub = sinon.createStubInstance<GlobalGroupsService>(GlobalGroupsService);

      wrapper = shallowMount<GlobalGroupsClass>(GlobalGroupsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { globalGroupsService: () => globalGroupsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGlobalGroups = { id: 123 };
        globalGroupsServiceStub.find.resolves(foundGlobalGroups);

        // WHEN
        comp.retrieveGlobalGroups(123);
        await comp.$nextTick();

        // THEN
        expect(comp.globalGroups).toBe(foundGlobalGroups);
      });
    });
  });
});
