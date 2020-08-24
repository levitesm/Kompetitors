import { Mixins, Vue, Watch } from 'vue-property-decorator';
import Component from 'vue-class-component';
import LineChart from './LineChart';
import { mapGetters } from 'vuex';
import ChartJsPluginDataLabels from 'chartjs-plugin-datalabels';
import EmployeesMixin from '@/shared/mixins/employees-mixin';

@Component({
  components: { LineChart, ChartJsPluginDataLabels },
  computed: {
    ...mapGetters({})
  }
})
export default class EmployeesChart extends Mixins(EmployeesMixin) {
  public dataCollection: any = {};

  public options: any = {
    responsive: true,
    maintainAspectRatio: false,
    legend: {
      display: false,
      labels: {
        enabled: true
      }
    },
    scales: {
      xAxes: [
        {
          offset: true,
          maxBarThickness: 80
        }
      ],
      yAxes: [
        {
          id: 'workforce',
          position: 'left'
        }
      ]
    },
    layout: {
      padding: {
        top: 25
      }
    },
    tooltips: {
      callbacks: {
        label: (tooltipItem, data) => {
          return Number(tooltipItem.value).toLocaleString('fr-FR');
        }
      }
    },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
        formatter: (value, context) => {
          const prev = context.dataset.data[context.dataIndex - 1];
          if (value === 0 && context.datasetIndex !== 1) {
            return this.$root.$t('hr-tab.employees.no-info');
          }
          if (context.datasetIndex === 1 || context.dataIndex === 0 || prev === 0) {
            return '';
          }
          const percent = ((value - prev) * 100) / prev;
          return (percent > 0 ? '+' : '') + percent.toFixed(0) + ' %';
        },
        labels: {
          title: {
            color: 'black'
          }
        }
      }
    }
  };
  mounted() {
    this.$root.$on('computeWorkforces', this.fillData);
    this.fillData();
  }
  fillData(): void {
    this.dataCollection = {
      labels: this.chartLabels(),
      datasets: [
        {
          label: 'Workforce',
          yAxisID: 'workforce',
          fill: false,
          borderWidth: 4,
          backgroundColor: '#a6a6a6',
          borderColor: '#a6a6a6',
          type: 'bar',
          data: this.chartWorkforces()
        }
      ]
    };
  }

  chartLabels() {
    let years = [];
    const currentYear = this.currentYear;
    [5, 4, 3, 2, 1].forEach(yearGap => {
      years = [...years, currentYear - yearGap];
    });
    return years;
  }

  chartWorkforces() {
    return this.chartLabels().map(this.getWorkforceForYear);
  }
}
