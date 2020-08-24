import Component from 'vue-class-component';
import { Vue, Inject, Prop } from 'vue-property-decorator';
import OfficesServices from '@/entities/offices/offices.service';
import { Competitors } from '@/shared/model/competitors.model';
import { Offices } from '@/shared/model/offices.model';
import { mapGetters } from 'vuex';
import CustomSpinner from '../../../components/spinner/spinner.vue';
import Question from '@/core/components/question.vue';

@Component({
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  },
  components: {
    Question
  }
})
export default class AddAgency extends Vue {
  public competitor!: Competitors;

  @Inject('officesService')
  private officesService: () => OfficesServices;

  @Prop() public selectedOffice: Offices;
  @Prop() public editOffice: boolean;

  public savingError = null;
  public establishedYear = '';
  public officeModel: Offices = new Offices();

  setOffice() {
    if (this.editOffice && this.selectedOffice) {
      this.officeModel = { ...this.selectedOffice };
      this.officeModel.competitors = Competitors.fromId(Number(this.competitor.id));
      this.establishedYear = new Date(this.selectedOffice.established).getFullYear().toString();
    } else {
      this.resetModel();
    }
  }

  resetModel() {
    this.officeModel = new Offices();
    this.officeModel.competitors = Competitors.fromId(Number(this.competitor.id));
    this.establishedYear = '';
  }

  async save() {
    if (!this.validate()) {
      return;
    }

    this.officeModel.established = new Date(Date.parse(this.establishedYear + '-01-01'));

    try {
      if (this.officeModel.id) {
        this.$store.commit('updateCompetitorOffice', await this.officesService().update(this.officeModel));
      } else {
        this.officeModel.name = this.competitor.offices.length.toString();
        const response = await this.officesService().create(this.officeModel);
        this.$store.commit('addCompetitorOffice', response);
        this.$root.$emit('agency_added', response.id);
      }

      this.$root.$emit('bv::hide::modal', 'add-agency-modal');
    } catch (err) {
      this.savingError = err;
    }
  }

  private validate(): boolean {
    if (!this.officeModel.cityAsText || this.officeModel.cityAsText.length === 0) {
      this.savingError = 'You have to enter a City for the agency.';
      return;
    }

    if (this.officeModel.post != null && this.officeModel.post.length !== 5) {
      this.savingError = 'Post format should be five digits NNNNN';
      return;
    }

    if (this.establishedYear != null && this.establishedYear.length !== 4) {
      this.savingError = 'Foundation year format should be four digits NNNN';
      return;
    }

    if (this.officeModel.numberEmployees < 0 || this.officeModel.numberEmployees > 2147483647) {
      this.savingError = 'Number of Employees should be positive and less than 2147483648';
      return false;
    }

    if (this.officeModel.numberConsultants < 0 || this.officeModel.numberConsultants > 2147483647) {
      this.savingError = 'Number of Consultants should be positive and less than 2147483648';
      return false;
    }

    if (this.officeModel.numberClients < 0 || this.officeModel.numberClients > 2147483647) {
      this.savingError = 'Number of Clients should be positive and less than 2147483648';
      return false;
    }

    this.savingError = null;
    return true;
  }
}
