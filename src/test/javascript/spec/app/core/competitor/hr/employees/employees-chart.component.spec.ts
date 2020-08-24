import { createLocalVue, mount, shallowMount, Wrapper } from '@vue/test-utils';
import * as config from '@/shared/config/config';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import EmployeesChart from '@/core/competitor/hr/employees/employees-chart.component';
import TimeTool from '@/tools/TimeTool';
import Vue from 'vue';
import { Store } from 'vuex-mock-store';

// Disables warning for the template not being rendered. We're only testing the chart features, not the template itself.
Vue.config.silent = true;

const localVue = createLocalVue();
localVue.component('font-awesome-icon', FontAwesomeIcon);
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

describe('EmployeesChart', () => {
  let wrapper: Wrapper<EmployeesChart>;
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
    wrapper = mount<EmployeesChart>(EmployeesChart, options);
  });

  describe('chart labels', function() {
    it('should be the last 5 years starting by last year in asc order', function() {
      expect(wrapper.vm.chartLabels()).toStrictEqual([YEAR_2013, YEAR_2014, YEAR_2015, YEAR_2016, YEAR_2017]);
    });
  });

  describe('chart workforces', function() {
    it('should be the workforces for the last 5 years starting by last year in asc order', function() {
      expect(wrapper.vm.chartWorkforces()).toStrictEqual([
        0,
        MANUALLY_ENTERED_2014,
        MANUALLY_ENTERED_2015,
        INFOGREFFE_2016,
        INFOGREFFE_2017
      ]);
    });
  });
});
