/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TechPartnersComponent from '@/entities/tech-partners/tech-partners.vue';
import TechPartnersClass from '@/entities/tech-partners/tech-partners.component';
import TechPartnersService from '@/entities/tech-partners/tech-partners.service';

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
  describe('TechPartners Management Component', () => {
    let wrapper: Wrapper<TechPartnersClass>;
    let comp: TechPartnersClass;
    let techPartnersServiceStub: SinonStubbedInstance<TechPartnersService>;

    beforeEach(() => {
      techPartnersServiceStub = sinon.createStubInstance<TechPartnersService>(TechPartnersService);
      techPartnersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TechPartnersClass>(TechPartnersComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          techPartnersService: () => techPartnersServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      techPartnersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTechPartnerss();
      await comp.$nextTick();

      // THEN
      expect(techPartnersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.techPartners[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      techPartnersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTechPartners();
      await comp.$nextTick();

      // THEN
      expect(techPartnersServiceStub.delete.called).toBeTruthy();
      expect(techPartnersServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
