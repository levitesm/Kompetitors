import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import { DashboardUnit } from '@/shared/model/dashboard-unit.model';
import { DashboardStatisticItem } from '@/shared/model/dashboard-statistic-item.model';
import { permutationTest } from 'simple-statistics';

@Component({
  computed: {}
})
export default class DashboardMapComponent extends Vue {
  @Prop() public allStatistics: DashboardUnit[];

  public selectedFirst = this.selectReference();
  public selectedSecond = this.selectReference();
  public a = -1;
  public b = 0;
  public c = 0;
  public d = 3;
  public d_temp = 1.5;
  public selectedStatistics = this.getSelectedStatistics();
  public deviations = new Map();

  copyD() {
    if (this.d_temp > 0) {
      this.d = this.d_temp;
    } else {
      alert('Deviation must be a positive number!');
    }
  }

  mounted() {
    this.calculateDevitions();
    this.selectedStatistics = this.getSelectedStatistics();
    this.recalculateAB();
  }

  @Watch('allStatistics') watchStats() {
    this.calculateDevitions();
    this.selectedFirst = this.selectReference();
    this.selectedSecond = this.selectReference();
    this.selectedStatistics = this.getSelectedStatistics();
    this.recalculateAB();
  }
  @Watch('selectedFirst') watchFirst() {
    this.selectedStatistics = this.getSelectedStatistics();
    this.recalculateAB();
  }
  @Watch('selectedSecond') watchSecond() {
    this.selectedStatistics = this.getSelectedStatistics();
    this.recalculateAB();
  }
  @Watch('d') watchD() {
    this.selectedStatistics = this.getSelectedStatistics();
    this.recalculateAB();
  }

  public getLogo(unit: DashboardUnit): string {
    return unit && unit.groupLogo ? 'data:' + unit.groupLogoContentType + '";base64,' + unit.groupLogo : '../../content/images/logo2.svg';
  }

  private selectReference(): string {
    return this.allStatistics[0].statistics[0].name;
  }

  public showCompetitor(id: number): void {
    this.$router.push({
      name: 'Competitor',
      params: {
        id: id.toString()
      }
    });
  }

  getStat(item: DashboardUnit, stat: string): number {
    for (let i = 0; i < item.statistics.length; i++) {
      if (item.statistics[i].name === stat) {
        return item.statistics[i].value;
      }
    }
    return null;
  }

  maxX() {
    let max = Number.MIN_VALUE;
    for (let i = 0; i < this.allStatistics.length; i++) {
      if (
        this.statDeviation(this.allStatistics[i], this.selectedFirst) < this.d &&
        this.getStat(this.allStatistics[i], this.selectedFirst) > max
      ) {
        max = this.getStat(this.allStatistics[i], this.selectedFirst);
      }
    }
    return max > 0 ? max : 0;
  }

  minX() {
    let min = Number.MAX_VALUE;
    for (let i = 0; i < this.allStatistics.length; i++) {
      if (
        this.statDeviation(this.allStatistics[i], this.selectedFirst) < this.d &&
        this.getStat(this.allStatistics[i], this.selectedFirst) < min
      ) {
        min = this.getStat(this.allStatistics[i], this.selectedFirst);
      }
    }
    return min < 0 ? min : 0;
  }

  maxY() {
    let max = Number.MIN_VALUE;
    for (let i = 0; i < this.allStatistics.length; i++) {
      if (
        this.statDeviation(this.allStatistics[i], this.selectedSecond) < this.d &&
        this.getStat(this.allStatistics[i], this.selectedSecond) > max
      ) {
        max = this.getStat(this.allStatistics[i], this.selectedSecond);
      }
    }
    return max > 0 ? max : 0;
  }

  minY() {
    let min = Number.MAX_VALUE;
    for (let i = 0; i < this.allStatistics.length; i++) {
      if (
        this.statDeviation(this.allStatistics[i], this.selectedSecond) < this.d &&
        this.getStat(this.allStatistics[i], this.selectedSecond) < min
      ) {
        min = this.getStat(this.allStatistics[i], this.selectedSecond);
      }
    }
    return min < 0 ? min : 0;
  }

  getX(val: number) {
    // console.log('val: ' + val + ' X: ' + (((val - this.minX()) / ((this.maxX() > this.minX())?(this.maxX() - this.minX()):2)) * 940 + 10));
    return ((val - this.minX()) / (this.maxX() > this.minX() ? this.maxX() - this.minX() : 2)) * 940 + 10;
  }

  getY(val: number): number {
    // console.log('val: ' + val + ' max: '+ this.maxY() +
    // ' min: ' + this.minY() + ' Y: ' + (950 - (((val - this.minY()) / ((this.maxY() > this.minY())?(this.maxY() - this.minY()):2)) * 940))) ;
    return 950 - ((val - this.minY()) / (this.maxY() > this.minY() ? this.maxY() - this.minY() : 2)) * 940;
  }

  func(x: number): number {
    return Number(x) * Number(this.a) + Number(this.b);
  }

  goodCompanies(): DashboardUnit[] {
    const arr = [];
    for (let i = 0; i < this.selectedStatistics.length; i++) {
      const item = this.selectedStatistics[i];
      if (
        this.getStat(item, this.selectedFirst) !== null &&
        this.getStat(item, this.selectedSecond) !== null &&
        this.getStat(item, this.selectedSecond) > this.func(this.getStat(item, this.selectedFirst)) + Number(this.c)
      ) {
        arr.push(item);
      }
    }
    return arr;
  }

  badCompanies(): DashboardUnit[] {
    const arr = [];
    for (let i = 0; i < this.selectedStatistics.length; i++) {
      const item = this.selectedStatistics[i];
      if (
        this.getStat(item, this.selectedFirst) !== null &&
        this.getStat(item, this.selectedSecond) !== null &&
        this.getStat(item, this.selectedSecond) < this.func(this.getStat(item, this.selectedFirst)) - Number(this.c)
      ) {
        arr.push(item);
      }
    }
    return arr;
  }

  neutralCompanies(): DashboardUnit[] {
    const arr = [];
    for (let i = 0; i < this.selectedStatistics.length; i++) {
      const item = this.selectedStatistics[i];
      if (
        this.getStat(item, this.selectedFirst) !== null &&
        this.getStat(item, this.selectedSecond) !== null &&
        this.getStat(item, this.selectedSecond) <= this.func(this.getStat(item, this.selectedFirst)) + Number(this.c) &&
        this.getStat(item, this.selectedSecond) >= this.func(this.getStat(item, this.selectedFirst)) - Number(this.c)
      ) {
        arr.push(item);
      }
    }
    return arr;
  }

  getSelectedStatistics(): DashboardUnit[] {
    const arr = [];
    for (let i = 0; i < this.allStatistics.length; i++) {
      const comp = this.allStatistics[i];
      if (
        this.getStat(comp, this.selectedFirst) !== null &&
        this.getStat(comp, this.selectedSecond) !== null &&
        this.statDeviation(comp, this.selectedFirst) < this.d &&
        this.statDeviation(comp, this.selectedSecond) < this.d
      ) {
        arr.push(comp);
      }
    }
    return arr;
  }

  recalculateAB() {
    this.a = -((this.maxY() - this.minY()) / (this.maxX() - this.minX()));

    this.b =
      this.average(this.selectedStatistics, this.selectedSecond) - this.average(this.selectedStatistics, this.selectedFirst) * this.a;

    this.c = (this.maxY() - this.minY()) / 10;
  }

  companiesWithData(): DashboardUnit[] {
    const arr = [];
    for (let i = 0; i < this.allStatistics.length; i++) {
      const comp = this.allStatistics[i];
      if (this.getStat(comp, this.selectedFirst) !== null && this.getStat(comp, this.selectedSecond) !== null) {
        arr.push(comp);
      }
    }
    return arr;
  }

  public calculateDevitions() {
    for (let u = 0; u < this.allStatistics.length; u++) {
      const unit = this.allStatistics[u];
      const map = new Map();
      for (let s = 0; s < unit.statistics.length; s++) {
        const selectedStat = unit.statistics[s].name;
        const statistic: DashboardStatisticItem = unit.statistics.find(item => item.name === selectedStat);
        if (statistic === null || statistic.value === null) {
          map.set(selectedStat, null);
          continue;
        }

        const array = [];

        for (let i = 0; i < this.allStatistics.length; i++) {
          const us = this.allStatistics[i];
          const ustatistic: DashboardStatisticItem = us.statistics.find(item => item.name === selectedStat);
          if (ustatistic !== null && ustatistic.value !== null) {
            array.push(ustatistic.value);
          }
        }

        if (array.length < 4) {
          map.set(selectedStat, null);
          continue;
        }

        let values, q1, q3, iqr;

        values = array.slice().sort((a, b) => a - b); // copy array fast and sort
        // console.log(values);

        q1 = this.median(values.slice(0, Math.floor(values.length / 2)));
        q3 = this.median(values.slice(Math.ceil(values.length / 2), values.length));

        iqr = q3 - q1;

        // console.log('q1: ' + q1 + '; q3: ' + q3 + '; iqr: ' + iqr + '; value: ' + statistic.value);

        let ans = 0.0;
        if (statistic.value > q3) {
          ans = (statistic.value - q3) / iqr;
        }
        if (statistic.value < q1) {
          ans = (q1 - statistic.value) / iqr;
        }
        map.set(selectedStat, ans);
      }
      this.deviations.set(unit, map);
    }
  }

  public statDeviation(unit: DashboardUnit, selectedStat): number {
    return this.deviations.get(unit) ? this.deviations.get(unit).get(selectedStat) : null;
  }

  median(array: any[]): number {
    if (array.length % 2 === 0) {
      return (array[array.length / 2 - 1] + array[array.length / 2]) / 2;
    } else {
      return array[Math.floor(array.length / 2)];
    }
  }

  average(array: DashboardUnit[], stat: string): number {
    let sum = 0;
    let num = 0;
    for (let i = 0; i < array.length; i++) {
      if (this.getStat(array[i], stat) !== null && this.statDeviation(array[i], stat) < this.d) {
        sum += this.getStat(array[i], stat);
        num++;
      }
    }
    if (num === 0) {
      return null;
    }
    return sum / num;
  }

  test(stat: string): number {
    const bad = [];
    const good = [];
    for (let i = 0; i < this.badCompanies().length; i++) {
      if (this.getStat(this.badCompanies()[i], stat) !== null && this.statDeviation(this.badCompanies()[i], stat) < this.d) {
        bad.push(this.getStat(this.badCompanies()[i], stat));
      }
    }
    for (let i = 0; i < this.goodCompanies().length; i++) {
      if (this.getStat(this.goodCompanies()[i], stat) !== null && this.statDeviation(this.goodCompanies()[i], stat) < this.d) {
        good.push(this.getStat(this.goodCompanies()[i], stat));
      }
    }

    if (bad.length < 3 || good.length < 3) {
      return null;
    }

    return permutationTest(bad, good);
  }

  public get referenceStatistics(): DashboardUnit {
    return this.allStatistics.find(unit => unit.competitorId === Number(this.$route.params.id)) || new DashboardUnit();
  }

  getLineX1(pointY: number): number {
    if (this.a * this.minX() + this.b + pointY <= this.maxY()) {
      return this.minX();
    } else {
      return (this.maxY() - this.b - pointY) / this.a;
    }
  }

  getLineY1(pointY: number): number {
    if (this.a * this.minX() + this.b + pointY <= this.maxY()) {
      return this.a * this.minX() + this.b + pointY;
    } else {
      return this.maxY();
    }
  }

  getLineX2(pointY: number): number {
    if (this.a * this.maxX() + this.b + pointY >= this.minY()) {
      return this.maxX();
    } else {
      return (this.minY() - this.b - pointY) / this.a;
    }
  }

  getLineY2(pointY: number): number {
    if (this.a * this.maxX() + this.b + pointY >= this.minY()) {
      return this.a * this.maxX() + this.b + pointY;
    } else {
      return this.minY();
    }
  }

  getLineLength(pointY: number): number {
    const x1 = this.getX(this.getLineX1(pointY));
    const x2 = this.getX(this.getLineX2(pointY)) + 40;
    const y1 = this.getY(this.getLineY1(pointY));
    const y2 = this.getY(this.getLineY2(pointY)) + 40;

    if (x1 < 10 || x2 > 990 || y2 < 10 || y1 > 990) {
      return 0;
    }

    return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
  }

  getLineAngle(pointY: number): number {
    const x1 = this.getX(this.getLineX1(pointY));
    const x2 = this.getX(this.getLineX2(pointY)) + 40;
    const y1 = this.getY(this.getLineY1(pointY));
    const y2 = this.getY(this.getLineY2(pointY)) + 40;

    // console.log('x1 = ' + x1 + ', x2 = ' + x2 + ', y1 = ' + y1 + ', y2 = ' + y2 + ', len = ' +
    // this.getLineLength(pointY) + ', ang = ' + (Math.atan((y2-y1) / (x2-x1)) * 180 / 3.1415));

    return (Math.atan((y2 - y1) / (x2 - x1)) * 180) / 3.1415;
  }
}
