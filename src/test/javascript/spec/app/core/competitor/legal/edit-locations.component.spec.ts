import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import * as config from '@/shared/config/config';
import EditLocations from '@/core/competitor/legal/edit-locations.vue';
import { Store } from 'vuex-mock-store';

import BootstrapVue from 'bootstrap-vue';
import Vue from 'vue';
import OfficesServices from '@/entities/offices/offices.service';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

Vue.use(BootstrapVue);

const localVue = createLocalVue();
localVue.component('font-awesome-icon', FontAwesomeIcon);
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);

// create the Store mock
const store = new Store({
  getters: {
    competitorsInGroup: [
      {
        name: 'Competitor 1 mock',
        offices: [
          {
            id: 1,
            mainOffice: true,
            name: 'Competitor 1 office 1 mock',
            post: 'Competitor 1 post 1 mock',
            cityAsText: 'Competitor 1 city as text 1 mock ',
            clients: []
          }
        ]
      },
      {
        name: 'Competitor 2 mock',
        offices: [
          {
            id: 2,
            mainOffice: true,
            name: 'Competitor 2 office 1 mock',
            post: 'Competitor 2 post 1 mock',
            cityAsText: 'Competitor 2 city as text 1 mock',
            clients: []
          },
          {
            id: 3,
            mainOffice: false,
            name: 'Competitor 2 office 2 mock',
            post: 'Competitor 2 post 2 mock',
            cityAsText: 'Competitor 2 city as text 2 mock',
            clients: []
          },
          {
            id: 4,
            mainOffice: false,
            name: 'Competitor 2 office 3 mock',
            post: 'Competitor 2 post 3 mock',
            cityAsText: 'Competitor 2 city as text 3 mock',
            clients: [{}, {}]
          }
        ]
      }
    ]
  }
});

describe('EditLocations', () => {
  let wrapper: Wrapper<EditLocations>;
  let officesService: OfficesServices;

  beforeEach(() => {
    officesService = {
      create: jest.fn(),
      find: jest.fn(),
      retrieve: jest.fn(),
      update: jest.fn(),
      findOffices: jest.fn(),
      delete: () => new Promise((resolve, _) => resolve({ status: 204 }))
    };
    wrapper = shallowMount<EditLocations>(EditLocations, {
      i18n,
      localVue,
      methods: {
        hasAccess: () => true
      },
      provide: {
        officesService: () => officesService
      },
      mocks: {
        $store: store
      }
    });
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  describe('when user does not have access to DELETE_OFFICE', () => {
    const offices = [[0, 0], [1, 0], [1, 1], [1, 2]];

    beforeEach(() => {
      wrapper.setMethods({ hasAccess: () => false });
    });

    it('should not display any icon-close', () => {
      expect(wrapper.find('.icon-close').exists()).toBe(false);
    });

    describe.each(offices)('with any office', (competitor, office) => {
      describe('should not be able to add any office to the to-be-deleted list', () => {
        test(`competitor ${competitor}, office ${office}`, async () => {
          const mockOffice = store.getters.competitorsInGroup[competitor].offices[office];
          (wrapper.vm as any).addOfficeToBeDeleted(mockOffice);
          expect(wrapper.findAll('.icon-undo').length).toBe(0);
        });
      });
      describe('should not be able to delete any office', () => {
        test(`competitor ${competitor}, office ${office}`, async () => {
          const mockOffice = store.getters.competitorsInGroup[competitor].offices[office];
          const isOfficeDeleted = await (wrapper.vm as any).deleteOffice(mockOffice);
          expect(isOfficeDeleted).toBeFalsy();
        });
      });
    });
  });

  describe('when user has access to DELETE_OFFICE', () => {
    const mainOffices = [[0, 0], [1, 0]];

    const otherOfficesWithClients = [[1, 2]];

    const otherOfficesWithNoClient = [[1, 1]];

    beforeEach(() => {
      wrapper.setMethods({ hasAccess: () => true });
    });

    describe.each(mainOffices)('with a main office', (competitor, office) => {
      describe('should not be able to delete', () => {
        test(`competitor ${competitor}, office ${office}`, async () => {
          const mockOffice = store.getters.competitorsInGroup[competitor].offices[office];
          const isOfficeDeleted = await (wrapper.vm as any).deleteOffice(mockOffice);
          expect(isOfficeDeleted).toBeFalsy();
        });
      });
      describe('should not be able to add to the to-be-deleted list', () => {
        test(`competitor ${competitor}, office ${office}`, () => {
          const mockOffice = store.getters.competitorsInGroup[competitor].offices[office];
          (wrapper.vm as any).addOfficeToBeDeleted(mockOffice);
          expect(wrapper.findAll('.icon-undo').length).toBe(0);
        });
      });
    });

    describe.each(otherOfficesWithClients)('with an office that has clients', (competitor, office) => {
      const mockOffice = store.getters.competitorsInGroup[competitor].offices[office];
      describe('should not be able to delete', () => {
        test(`competitor ${competitor}, office ${office}`, async () => {
          const isOfficeDeleted = await (wrapper.vm as any).deleteOffice(mockOffice);
          expect(isOfficeDeleted).toBeFalsy();
        });
      });
      describe('should not be able to add to the to-be-deleted list', () => {
        test(`competitor ${competitor}, office ${office}`, async () => {
          (wrapper.vm as any).addOfficeToBeDeleted(mockOffice);
          expect(wrapper.findAll('.icon-undo').length).toBe(0);
        });
      });
    });

    describe.each(otherOfficesWithNoClient)('with an office that has no client ', (competitor, office) => {
      const mockOffice = store.getters.competitorsInGroup[competitor].offices[office];
      describe.each(otherOfficesWithNoClient)('should be able to add other offices to the to-be-deleted list', () => {
        let undoIconAmount = 0;
        test(`competitor ${competitor}, office ${office}`, async () => {
          (wrapper.vm as any).addOfficeToBeDeleted(mockOffice);
          undoIconAmount++;
          expect(wrapper.findAll('.icon-undo').length).toBe(undoIconAmount);
        });
      });
      describe.each(otherOfficesWithNoClient)('should be able to delete other offices', () => {
        test(`competitor ${competitor}, office ${office}`, async () => {
          const spy = jest.spyOn(officesService, 'delete');
          const isOfficeDeleted = await (wrapper.vm as any).deleteOffice(mockOffice);
          expect(isOfficeDeleted).toBeTruthy();
          expect(spy).toHaveBeenCalledWith(mockOffice.id);
        });
      });
    });

    it('should not display icon-close for main offices', () => {
      expect(wrapper.findAll('.icon-close').length).toBe(2);

      const offices = wrapper.findAll('.office');
      expect(offices.at(0).contains('.icon-close')).toBeFalsy();
      expect(offices.at(1).contains('.icon-close')).toBeFalsy();
    });

    it('should display icon-close for other offices', () => {
      expect(wrapper.findAll('.icon-close').length).toBe(2);

      const offices = wrapper.findAll('.office');
      expect(offices.at(2).contains('.icon-close')).toBeTruthy();
      expect(offices.at(3).contains('.icon-close')).toBeTruthy();
    });

    it('should display icon-disabled for other offices with clients', () => {
      expect(wrapper.findAll('.icon-disabled').length).toBe(1);

      const offices = wrapper.findAll('.office');
      expect(offices.at(3).contains('.icon-disabled')).toBeTruthy();
    });
  });
});
