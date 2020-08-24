import { Component, Prop, Vue } from 'vue-property-decorator';
import { DashboardUnit } from '@/shared/model/dashboard-unit.model';
import { DashboardStatisticItem } from '@/shared/model/dashboard-statistic-item.model';

@Component
export default class DashboardCardComponent extends Vue {
  @Prop() public unit: DashboardUnit;
  @Prop() public selectedStat: DashboardStatisticItem;
  @Prop() public dev: number;
  @Prop() public checkFlag: boolean;

  public get currentStat(): DashboardStatisticItem {
    return this.selectedStat ? this.unit.statistics.find(stat => stat.name === this.selectedStat.name) : new DashboardStatisticItem();
  }

  public getLogo(): string {
    return this.unit && this.unit.groupLogo
      ? 'data:' + this.unit.groupLogoContentType + '";base64,' + this.unit.groupLogo
      : '../../content/images/logo2.svg';
  }

  public displayValue(value: number): string {
    if (value === null) {
      return 'NA';
    }
    let result = value ? value.toString() : '0';
    if (value > 999999999) {
      result = (value / 1000000000).toFixed(0);
    } else if (value > 999999) {
      result = (value / 1000000).toFixed(0);
    } else if (value > 9999) {
      result = (value / 1000).toFixed();
    } else if (Math.round(value) !== value) {
      result = value.toFixed(2);
    }
    return result;
  }

  public displayUnit(value: number): string {
    let result = '';
    if (value > 999999999) {
      result = 'B';
    } else if (value > 999999) {
      result = 'M';
    } else if (value > 9999) {
      result = 'K';
    }
    return result;
  }

  public showCompetitor(id: number): void {
    this.$router.push({
      name: 'Competitor',
      params: {
        id: id.toString()
      }
    });
  }
}
