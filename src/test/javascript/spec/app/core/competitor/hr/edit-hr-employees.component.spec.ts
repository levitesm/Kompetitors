import { createLocalVue, mount, shallowMount, Wrapper } from '@vue/test-utils';
import * as config from '@/shared/config/config';
import EditHrEmployees from '@/core/competitor/hr/edit-hr-employees.vue';
import TimeTool from '@/tools/TimeTool';
import { Store } from 'vuex-mock-store';
import WorkforceService from '@/entities/workforce/workforce.service';

import BootstrapVue, { BFormInput } from 'bootstrap-vue';
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { Competitors } from '@/shared/model/competitors.model';

Vue.use(BootstrapVue);

const localVue = createLocalVue();
localVue.component('font-awesome-icon', FontAwesomeIcon);
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);

const INFOGREFFE_2017 = '218';
const INFOGREFFE_2016 = '200';
const MANUALLY_ENTERED_2015 = '185';
const MANUALLY_ENTERED_2014 = '153';
const MANUALLY_ENTERED_2013 = '122';
const COMPETITOR_ID = 1111;
const YEAR_2015 = '2015';
const YEAR_2014 = '2014';
const YEAR_2013 = '2013';

describe('EditHrEmployees', () => {
  let wrapper: Wrapper<EditHrEmployees>;
  let mockTimeTool: TimeTool;
  let mockWorkForce;
  let mockStore: Store;
  let mockWorkforceService: WorkforceService;

  beforeEach(() => {
    mockTimeTool = {
      getFullYear: () => 2018
    };
    mockWorkForce = (year: number) => {
      switch (year) {
        case 2017:
          return INFOGREFFE_2017;
        case 2016:
          return INFOGREFFE_2016;
        default:
          return 0;
      }
    };
    mockWorkforceService = {
      delete: jest.fn(),
      find: jest.fn(),
      findByCompetitorId: jest.fn(),
      retrieve: jest.fn(),
      update: jest.fn(),
      create: jest.fn()
    };
    mockStore = new Store({
      getters: {
        competitor: {
          id: COMPETITOR_ID
        },
        workforces: {
          [YEAR_2014]: {
            id: 0,
            year: YEAR_2014,
            employeeNumber: MANUALLY_ENTERED_2014,
            competitor: COMPETITOR_ID
          },
          [YEAR_2015]: {
            id: 1,
            year: YEAR_2015,
            employeeNumber: MANUALLY_ENTERED_2015,
            competitor: COMPETITOR_ID
          }
        }
      }
    });
    wrapper = mount<EditHrEmployees>(EditHrEmployees, {
      i18n,
      localVue,
      methods: {
        workForce: mockWorkForce
      },
      provide: {
        timeTool: () => mockTimeTool,
        workforceService: () => mockWorkforceService
      },
      mocks: {
        $store: mockStore
      }
    });
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  it('should display last 5 years labels', () => {
    const allYearLabels = wrapper.findAll('.year-label');
    const currentMockYear = mockTimeTool.getFullYear();
    [0, 1, 2, 3, 4].forEach(index => {
      const yearLabel = allYearLabels.at(index).text();
      const expectedYearLabel = (currentMockYear - index - 1).toString() + ' :';
      expect(yearLabel).toBe(expectedYearLabel);
    });
  });

  it('should display last 5 years employee number TextFields', () => {
    const allEmployeeNumberTextFields = wrapper.findAll(BFormInput);
    expect(allEmployeeNumberTextFields.length).toBe(5);
  });

  describe('a textfield', () => {
    it('should be disabled if infogreffe data for this year is found', () => {
      const allEmployeeNumberTextFields = wrapper.findAll(BFormInput);
      [0, 1].forEach(index => {
        expect(allEmployeeNumberTextFields.at(index).is('[disabled]')).toBe(true);
      });
    });

    it('should not be disabled if infogreffe data for this year is not found', () => {
      const allEmployeeNumberTextFields = wrapper.findAll(BFormInput);
      [2, 3, 4].forEach(index => {
        expect(allEmployeeNumberTextFields.at(index).is('[disabled]')).toBe(false);
      });
    });

    it('should contain infogreffe data if found for this year', () => {
      expect((wrapper as any).vm.lastYearNumber).toBe(INFOGREFFE_2017);
      expect((wrapper as any).vm.secondToLastYearNumber).toBe(INFOGREFFE_2016);
    });

    it('should contain manually entered data if no infogreffe found for this year', () => {
      expect((wrapper as any).vm.thirdToLastYearNumber).toBe(MANUALLY_ENTERED_2015);
      expect((wrapper as any).vm.fourthToLastYearNumber).toBe(MANUALLY_ENTERED_2014);
    });

    it('should be 0 if no data at all exist for this year', () => {
      expect((wrapper as any).vm.fifthToLastYearNumber).toBe(0);
    });
  });

  describe('on save', () => {
    beforeEach(() => {
      const input = wrapper.findAll(BFormInput).at(4);
      input.setValue(MANUALLY_ENTERED_2013);
    });

    it('should call the update method in the service to update existing workforces', async () => {
      const updateSpy = jest.spyOn(mockWorkforceService, 'update');
      const competitor = new Competitors();
      competitor.id = COMPETITOR_ID;

      await (wrapper as any).vm.saveEmployeesNumber();

      expect(updateSpy).toHaveBeenCalledTimes(2);
      expect(updateSpy).nthCalledWith(1, { id: 1, employeeNumber: MANUALLY_ENTERED_2015, year: new Date(YEAR_2015), competitor });
      expect(updateSpy).nthCalledWith(2, { id: 0, employeeNumber: MANUALLY_ENTERED_2014, year: new Date(YEAR_2014), competitor });
    });

    it('should call the create method in the service to create new workforces', async () => {
      const createSpy = jest.spyOn(mockWorkforceService, 'create');
      const competitor = new Competitors();
      competitor.id = COMPETITOR_ID;

      await (wrapper as any).vm.saveEmployeesNumber();

      expect(createSpy).toHaveBeenCalledTimes(1);
      expect(createSpy).nthCalledWith(1, { employeeNumber: MANUALLY_ENTERED_2013, year: new Date(YEAR_2013), competitor });
    });

    it('should call the findByCompetitorId method in the service to update local workforces', async () => {
      const findByCompetitorIdSpy = jest.spyOn(mockWorkforceService, 'findByCompetitorId');

      await (wrapper as any).vm.saveEmployeesNumber();

      expect(findByCompetitorIdSpy).toHaveBeenCalledTimes(1);
    });

    it('should set new workforces in the store', async () => {
      await (wrapper as any).vm.saveEmployeesNumber();

      expect(mockStore.commit).toHaveBeenCalledTimes(1);
    });
  });
});
