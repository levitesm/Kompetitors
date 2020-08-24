import { Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';

@Component({
  props: {
    title: String,
    rate: Number
  }
})
export default class CompetitiveRateComponent extends Vue {
  public title!: '';
  public rate!: 0;

  ratingOn(): number {
    return Math.floor(this.rateChecked() / 20);
  }
  rateChecked() {
    return this.rate && this.rate >= 0 && this.rate <= 100 ? this.rate : 0;
  }
}
