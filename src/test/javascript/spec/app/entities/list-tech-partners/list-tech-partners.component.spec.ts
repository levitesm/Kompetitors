/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListTechPartnersComponent from '@/entities/list-tech-partners/list-tech-partners.vue';
import ListTechPartnersClass from '@/entities/list-tech-partners/list-tech-partners.component';
import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('ListTechPartners Management Component', () => {
    let wrapper: Wrapper<ListTechPartnersClass>;
    let comp: ListTechPartnersClass;
    let listTechPartnersServiceStub: SinonStubbedInstance<ListTechPartnersService>;

    beforeEach(() => {
      listTechPartnersServiceStub = sinon.createStubInstance<ListTechPartnersService>(ListTechPartnersService);
      listTechPartnersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListTechPartnersClass>(ListTechPartnersComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          listTechPartnersService: () => listTechPartnersServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listTechPartnersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListTechPartnerss();
      await comp.$nextTick();

      // THEN
      expect(listTechPartnersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listTechPartners[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listTechPartnersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeListTechPartners();
      await comp.$nextTick();

      // THEN
      expect(listTechPartnersServiceStub.delete.called).toBeTruthy();
      expect(listTechPartnersServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
