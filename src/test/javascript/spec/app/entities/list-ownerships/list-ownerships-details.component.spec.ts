/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListOwnershipsDetailComponent from '@/entities/list-ownerships/list-ownerships-details.vue';
import ListOwnershipsClass from '@/entities/list-ownerships/list-ownerships-details.component';
import ListOwnershipsService from '@/entities/list-ownerships/list-ownerships.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ListOwnerships Management Detail Component', () => {
    let wrapper: Wrapper<ListOwnershipsClass>;
    let comp: ListOwnershipsClass;
    let listOwnershipsServiceStub: SinonStubbedInstance<ListOwnershipsService>;

    beforeEach(() => {
      listOwnershipsServiceStub = sinon.createStubInstance<ListOwnershipsService>(ListOwnershipsService);

      wrapper = shallowMount<ListOwnershipsClass>(ListOwnershipsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listOwnershipsService: () => listOwnershipsServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListOwnerships = { id: 123 };
        listOwnershipsServiceStub.find.resolves(foundListOwnerships);

        // WHEN
        comp.retrieveListOwnerships(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listOwnerships).toBe(foundListOwnerships);
      });
    });
  });
});
