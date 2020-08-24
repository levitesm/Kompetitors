import { createLocalVue, mount, Wrapper } from '@vue/test-utils';
import * as config from '@/shared/config/config';
import TimeTool from '@/tools/TimeTool';
import { Store } from 'vuex-mock-store';
import Vue from 'vue';
import EmployeesMixin from '@/shared/mixins/employees-mixin';

// Disables warning for the template not being rendered. There is actually no template, we're only testing the mixin features.
Vue.config.silent = true;

const localVue = createLocalVue();
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);

const INFOGREFFE_2017 = '218';
const INFOGREFFE_2016 = '200';
const MANUALLY_ENTERED_2015 = '185';
const MANUALLY_ENTERED_2014 = '153';
const COMPETITOR_ID = 1111;
const YEAR_2017 = 2017;
const YEAR_2016 = 2016;
const YEAR_2015 = 2015;
const YEAR_2014 = 2014;
const YEAR_2013 = 2013;

describe('EmployeesMixin', () => {
  let wrapper: Wrapper<EmployeesMixin>;
  let mockTimeTool: TimeTool;
  let mockWorkForce;
  let mockStore: Store;
  let options;

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
    options = {
      i18n,
      localVue,
      methods: {
        workForce: mockWorkForce
      },
      provide: {
        timeTool: () => mockTimeTool
      },
      mocks: {
        $store: mockStore
      }
    };
    wrapper = mount<EmployeesMixin>(EmployeesMixin, options);
  });

  it('should set the current Year', function() {
    expect(wrapper.vm.currentYear).toBe(2018);
  });

  describe('getWorkforceForYear', function() {
    it('should return infogreffe info if available', function() {
      expect(wrapper.vm.getWorkforceForYear(YEAR_2017)).toBe(INFOGREFFE_2017);
      expect(wrapper.vm.getWorkforceForYear(YEAR_2016)).toBe(INFOGREFFE_2016);
    });

    it('should return manually added info if infogreffe is not available', function() {
      expect(wrapper.vm.getWorkforceForYear(YEAR_2015)).toBe(MANUALLY_ENTERED_2015);
      expect(wrapper.vm.getWorkforceForYear(YEAR_2014)).toBe(MANUALLY_ENTERED_2014);
    });

    it('should return manually added info if infogreffe is not available', function() {
      expect(wrapper.vm.getWorkforceForYear(YEAR_2015)).toBe(MANUALLY_ENTERED_2015);
      expect(wrapper.vm.getWorkforceForYear(YEAR_2014)).toBe(MANUALLY_ENTERED_2014);
    });

    it('should return 0 if no data is available', function() {
      expect(wrapper.vm.getWorkforceForYear(YEAR_2013)).toBe(0);
    });
  });

  describe('getLatestYearWithWorkforce', function() {
    it('should return the latest year with workforce data either in infogreffe or manually added within 5 years', function() {
      expect(wrapper.vm.getLatestYearWithWorkforce()).toBe(YEAR_2017);
    });

    it('should not return more than 5 years earlier than today', function() {
      wrapper = mount<EmployeesMixin>(EmployeesMixin, {
        ...options,
        methods: {
          workForce: () => 0
        },
        mocks: {
          $store: new Store({
            getters: {
              workforces: {}
            }
          })
        }
      });
      expect(wrapper.vm.getLatestYearWithWorkforce()).toBe(YEAR_2013);
    });
  });

  describe('getLatestWorkforces', function() {
    it('should return workforces for the last 5 years', function() {
      expect(wrapper.vm.getLatestWorkforces()).toStrictEqual([
        {
          year: YEAR_2017,
          employeeNumber: INFOGREFFE_2017
        },
        {
          year: YEAR_2016,
          employeeNumber: INFOGREFFE_2016
        },
        {
          year: YEAR_2015,
          employeeNumber: MANUALLY_ENTERED_2015
        },
        {
          year: YEAR_2014,
          employeeNumber: MANUALLY_ENTERED_2014
        },
        {
          year: YEAR_2013,
          employeeNumber: 0
        }
      ]);
    });
  });

  describe('getLatestAvailableWorkforce', function() {
    it('should return the workforce for the latest year with workforce', function() {
      const spyLatestYear = jest.spyOn(wrapper.vm, 'getLatestYearWithWorkforce');
      const spyWorkforceForYear = jest.spyOn(wrapper.vm, 'getWorkforceForYear');

      const latestAvailableWorkforce = wrapper.vm.getLatestAvailableWorkforce();

      expect(latestAvailableWorkforce).toBe(INFOGREFFE_2017);
      expect(spyLatestYear).toHaveBeenCalledTimes(1);
      expect(spyLatestYear).toReturnWith(YEAR_2017);
      expect(spyWorkforceForYear).toBeCalledWith(YEAR_2017);
    });
  });

  describe('getLatestWorkforceGap', function() {
    it('should return the difference between the latest available workforce and the workforce for the year prior to this one', function() {
      expect(wrapper.vm.getLatestWorkforceGap()).toBe(Number(INFOGREFFE_2017) - Number(INFOGREFFE_2016));
    });

    it('should return 0 if the latest available workforce is 0', function() {
      wrapper = mount<EmployeesMixin>(EmployeesMixin, {
        ...options,
        methods: {
          workForce: () => 0
        },
        mocks: {
          $store: new Store({
            getters: {
              workforces: {}
            }
          })
        }
      });
      expect(wrapper.vm.getLatestWorkforceGap()).toBe(0);
    });

    it('should return 0 if the workforce for the year prior to the latest available workforce is 0', function() {
      wrapper = mount<EmployeesMixin>(EmployeesMixin, {
        ...options,
        methods: {
          workForce: (year: number) => {
            switch (year) {
              case 2017:
                return INFOGREFFE_2017;
              default:
                return 0;
            }
          }
        },
        mocks: {
          $store: new Store({
            getters: {
              workforces: {}
            }
          })
        }
      });
      expect(wrapper.vm.getLatestWorkforceGap()).toBe(0);
    });
  });

  describe('hasInfogreffeForYear', function() {
    it('should return true if it has infogreffe for given year', function() {
      expect(wrapper.vm.hasInfogreffeForYear(YEAR_2017)).toBe(true);
    });

    it('should return false if it does not have infogreffe for given year', function() {
      expect(wrapper.vm.hasInfogreffeForYear(YEAR_2015)).toBe(false);
      expect(wrapper.vm.hasInfogreffeForYear(YEAR_2013)).toBe(false);
    });
  });
});
