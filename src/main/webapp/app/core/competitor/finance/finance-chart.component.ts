import { Mixins, Vue, Watch } from 'vue-property-decorator';
import Component from 'vue-class-component';
import LineChart from './LineChart';
import { IAnnualAccount } from '@/shared/model/annual-account.model';
import { mapGetters } from 'vuex';
import ChartJsPluginDataLabels from 'chartjs-plugin-datalabels';
import EmployeesMixin from '@/shared/mixins/employees-mixin';

@Component({
  components: { LineChart, ChartJsPluginDataLabels },
  computed: {
    ...mapGetters({
      annualAccounts: 'annualAccounts'
    })
  }
})
export default class FinanceChart extends Mixins(EmployeesMixin) {
  public annualAccounts!: IAnnualAccount[];
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
          id: 'gross',
          position: 'left',
          ticks: {
            callback: this.formatValue
          }
        },
        {
          id: 'workforce',
          position: 'right'
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
          if (context.datasetIndex === 1) {
            return '';
          }
          if (context.dataIndex === 0) {
            return '';
          }
          const prev = context.dataset.data[context.dataIndex - 1];
          if (prev && prev > 0) {
            const percent = ((value - prev) * 100) / prev;
            return (percent > 0 ? '+' : '') + percent.toFixed(0) + ' %';
          } else {
            return '';
          }
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
    this.fillData();
  }
  @Watch('annualAccountsData')
  onPropertyChanged() {
    this.fillData();
  }
  get annualAccountsData(): IAnnualAccount[] {
    return this.annualAccounts.filter(acc => acc.code === 'N40' || acc.code === 'CFL');
  }

  fillData(): void {
    this.dataCollection = {
      labels: this.chartLabels(),
      datasets: [
        {
          label: 'Gross Sales',
          yAxisID: 'gross',
          fill: false,
          borderWidth: 4,
          backgroundColor: '#ff0000',
          borderColor: '#ff0000',
          pointBackgroundColor: '#ffffff',
          pointBorderColor: '#ff0000',
          pointRadius: 5,
          type: 'line',
          data: this.chartAnnualAcc()
        },
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
  formatValue(value: number, index: number, values: any): string {
    if (value >= 1000000000) {
      return (value / 1000000000).toFixed(2) + ' B';
    } else if (value >= 1000000) {
      return (value / 1000000).toFixed(1) + ' M';
    } else if (value >= 1000) {
      return (value / 1000).toFixed(0) + ' K';
    }
    return value.toLocaleString('fr-FR');
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

  chartAnnualAcc() {
    return this.chartLabels().map(label => {
      for (let i = 0; i < this.annualAccountsData.length; i++) {
        if (this.annualAccountsData[i].year === label) {
          return this.annualAccountsData[i].value;
        }
      }
      return null;
    });
  }
}
