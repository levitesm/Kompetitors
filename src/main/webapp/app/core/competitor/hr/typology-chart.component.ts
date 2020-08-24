import { Component, Mixins, Prop } from 'vue-property-decorator';
import { EmployeesTypology } from '@/shared/model/employees-typology.model';
import DoughnutChart from './DoughnutChart';
import ChartJsPluginDataLabels from 'chartjs-plugin-datalabels';
import LocalisationMixin from '@/locale/localisation.mixin';

@Component({
  components: { DoughnutChart, ChartJsPluginDataLabels }
})
export default class TypologyChartComponent extends Mixins(LocalisationMixin) {
  @Prop() readonly typologies: EmployeesTypology[];
  public colorPalette = [
    '#e25668',
    '#e2cf56',
    '#68e256',
    '#56e2cf',
    '#5668e2',
    '#cf56e2',
    '#e28956',
    '#aee256',
    '#56e289',
    '#56aee2',
    '#8a56e2',
    '#e256ae'
  ];
  public options: any = {
    responsive: true,
    maintainAspectRatio: false,
    legend: {
      display: false,
      labels: {
        enabled: true
      }
    },
    plugins: {
      datalabels: {
        formatter: (value, context) => this.getPercent(value),
        labels: {
          title: {
            color: 'black'
          }
        }
      }
    }
  };

  public get dataCollection(): any {
    return {
      labels: this.typologies.map(typology => this.localisationFromName(typology.employeeTypeName)),
      datasets: [
        {
          label: this.typologies.length > 0 ? this.typologies[0].year : new Date().getFullYear(),
          backgroundColor: this.palette,
          borderColor: this.palette,
          type: 'doughnut',
          data: this.typologies.map(typology => typology.value)
        }
      ]
    };
  }

  public get palette(): string[] {
    if (this.typologies.length > this.colorPalette.length) {
      const times = Math.round(this.typologies.length / this.colorPalette.length);
      return Array(times)
        .fill([...this.colorPalette])
        .reduce((a, b) => a.concat(b));
    }
    return this.colorPalette;
  }

  get legend() {
    const names = this.typologies.map(typology => typology.employeeTypeName);
    return names
      .reduce((result, name) => {
        result.push({
          name,
          color: this.palette[names.indexOf(name)]
        });
        return result;
      }, [])
      .sort((a, b) => {
        if (a.name < b.name) {
          return -1;
        }
        if (a.name > b.name) {
          return 1;
        }
        return 0;
      });
  }

  public getPercent(value: number): string {
    const sum = this.typologies.reduce((result, typology) => {
      return result + typology.value;
    }, 0);
    const percent = sum > 0 && value > 0 ? Math.round((value * 100) / sum) : 0;
    return percent > 3 ? percent + ' %' : '';
  }
}
