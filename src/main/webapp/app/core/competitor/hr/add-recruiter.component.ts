import { Inject, Prop, Vue } from 'vue-property-decorator';
import { People } from '@/shared/model/people.model';
import PeopleService from '@/entities/people/people.service';
import Component from 'vue-class-component';
import { Offices } from '@/shared/model/offices.model';

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'add-recruiter-page');
    }
  }
})
export default class AddRecruiterModal extends Vue {
  @Prop() public selectedOffice: Offices;

  public savingError = '';

  @Inject('peopleService')
  private peopleService: () => PeopleService;

  public model: People = new People();

  mounted() {
    this.model.isKey = false;
    this.model.title = 'Recruiter';
    this.model.competitors = this.$props.selectedOffice.competitors;
    this.model.specificOffice = this.$props.selectedOffice.id;
  }

  public async saveChanges(): Promise<void> {
    if (this.model.lName === '' || this.model.fName === '') {
      this.savingError = 'You have to enter First and Last names!';
      return;
    }
    try {
      this.$root.$emit('recruiter-added', await this.peopleService().create(this.model));

      this.savingError = '';

      this.$root.$emit('bv::hide::modal', 'add-recruiter-page');
    } catch (err) {
      this.savingError = err;
    }
  }
}
