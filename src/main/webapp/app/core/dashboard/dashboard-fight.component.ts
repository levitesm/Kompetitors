import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import { DashboardUnit } from '@/shared/model/dashboard-unit.model';

@Component
export default class DashboardFightComponent extends Vue {
  @Prop() public currentStatistics: DashboardUnit[];

  public selectedFirst = this.selectReference();
  public selectedSecond = null;

  @Watch('currentStatistics') watchStats() {
    this.selectedFirst = this.refind({ ...this.selectedFirst });
    this.selectedSecond = this.refind({ ...this.selectedSecond });
  }

  public getLogo(unit: DashboardUnit): string {
    return unit && unit.groupLogo ? 'data:' + unit.groupLogoContentType + '";base64,' + unit.groupLogo : '../../content/images/logo2.svg';
  }

  public displayValue(value: number): string {
    let result = value ? value.toString() : '0';
    if (value > 999999999) {
      result = (value / 1000000000).toFixed(0);
    } else if (value > 999999) {
      result = (value / 1000000).toFixed(0);
    } else if (value > 9999) {
      result = (value / 1000).toFixed();
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

  private selectReference(): DashboardUnit {
    for (let i = 0; i < this.currentStatistics.length; i++) {
      if (this.currentStatistics[i].competitorId === Number(this.$route.params.id)) {
        return this.currentStatistics[i];
      }
    }
    return null;
  }

  public showCompetitor(id: number): void {
    this.$router.push({
      name: 'Competitor',
      params: {
        id: id.toString()
      }
    });
  }

  private refind(item: DashboardUnit): DashboardUnit {
    for (let i = 0; i < this.currentStatistics.length; i++) {
      if (this.currentStatistics[i].competitorId === item.competitorId) {
        return this.currentStatistics[i];
      }
    }
    return null;
  }
}
