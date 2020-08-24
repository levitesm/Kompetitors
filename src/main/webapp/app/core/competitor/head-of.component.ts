import { Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';

@Component({
  props: {
    title: String,
    name: String,
    linkedinUrl: String
  }
})
export default class HeadOfComponent extends Vue {
  public title!: '';
  public name!: '';
  public linkedinUrl!: '';

  getUrl(): String {
    return this.linkedinUrl.includes('http') ? this.linkedinUrl : '//' + this.linkedinUrl;
  }
}
