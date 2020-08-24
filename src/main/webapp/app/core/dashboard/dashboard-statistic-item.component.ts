import { Component, Prop, Vue } from 'vue-property-decorator';
import { DashboardStatisticItem } from '@/shared/model/dashboard-statistic-item.model';

@Component
export default class DashboardStatisticItemComponent extends Vue {
  @Prop() public statisticItem: DashboardStatisticItem;
  @Prop() public isActive: boolean;

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

  public selectStat(statisticItem: DashboardStatisticItem) {
    this.$emit('selectStat', statisticItem);
  }
}
