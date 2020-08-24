import { Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';

@Component({
  props: {
    prevYear: Number,
    displayName: String,
    tooltip: String,
    func: Function,
    more: Boolean,
    then: Number,
    unit: String
  }
})
export default class FinanceMonthlyRatiosComponent extends Vue {
  public prevYear: Number;
  public displayName: String;
  public tooltip: String;
  public func: Function;
  public more: Boolean;
  public then: Number;
  public unit: Function;

  percent(oldValue: number, newValue: number): number {
    return this.func(oldValue) !== 0 && this.func(newValue) !== 0
      ? Number((((this.func(newValue) - this.func(oldValue)) * 100) / Math.abs(this.func(oldValue))).toFixed(0))
      : 0;
  }

  getGreen(year: number): boolean {
    if (this.more === undefined || this.then === undefined) {
      return undefined;
    }
    return this.more === true ? this.func(year) >= this.then : this.func(year) <= this.then;
  }
}
