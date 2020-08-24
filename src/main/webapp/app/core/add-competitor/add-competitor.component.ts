import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import CompetitorsService from '@/entities/competitors/competitors.service';

import { Competitors } from '@/shared/model/competitors.model';
import { Offices } from '@/shared/model/offices.model';
import { GlobalGroups } from '@/shared/model/global-groups.model';
import { Legal } from '@/shared/model/legal.model';
import { mapGetters } from 'vuex';
import ListCountriesService from '@/entities/list-countries/list-countries.service';
import { ListCountries } from '@/shared/model/list-countries.model';

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'add-competitor');
    }
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor',
      createGroup: 'createGroup'
    })
  }
})
export default class AddCompetitor extends Vue {
  public competitor: Competitors;
  public createGroup: boolean;

  @Inject('competitorsService')
  private competitorsService: () => CompetitorsService;
  @Inject('listCountriesService')
  private listCountriesService: () => ListCountriesService;

  private groupModel: GlobalGroups = new GlobalGroups();
  private competitorModel: Competitors = new Competitors();
  private legalModel: Legal = new Legal();
  private officeModel: Offices = new Offices();
  private established = '';
  private listCountries: ListCountries[] = new Array();
  private selectedCountry = new ListCountries();

  public savingError = null;

  async mounted() {
    if (!this.createGroup) {
      this.groupModel = this.competitor.globalGroups;
    }
    this.listCountries = (await this.listCountriesService().retrieve()).data;
    this.selectedCountry = this.findFrance();
  }

  public async saveChanges(): Promise<void> {
    if (!this.validate()) {
      return;
    }
    try {
      const date = new Date(this.established + '-01-01');
      this.officeModel.established = date;
      this.officeModel.mainOffice = true;
      this.legalModel.founded = date;
      this.competitorModel.country = this.selectedCountry;
      if (this.legalModel.siren === '') {
        this.legalModel.siren = null;
      }
      const response = await this.competitorsService().createWithGroup(
        this.groupModel,
        this.legalModel,
        this.officeModel,
        this.competitorModel
      );

      this.$router.push({
        name: 'Competitor',
        params: {
          id: response.id.toString()
        }
      });
    } catch (err) {
      console.error(err);
      this.savingError = err;
    }
  }

  private validate(): boolean {
    if (!this.groupModel.name) {
      this.savingError = 'You have to enter a Group Name.';
      return false;
    }
    if (!this.competitorModel.name) {
      this.savingError = 'You have to enter a Company Name.';
      return false;
    }
    if (!this.legalModel.siren && this.selectedCountry.value === 'France') {
      this.savingError = 'You have to enter the SIREN.';
      return false;
    }
    if (!this.officeModel.cityAsText) {
      this.savingError = 'You have to enter a City for a new agency.';
      return false;
    }
    if (this.officeModel.post != null && this.officeModel.post.length !== 5) {
      this.savingError = 'Post format should be five digits NNNNN';
      return false;
    }
    if (this.established != null && this.established.length !== 4) {
      this.savingError = 'Foundation year format should be four digits NNNN';
      return false;
    }
    return true;
  }

  private findFrance(): ListCountries {
    for (let i = 0; i < this.listCountries.length; i++) {
      if (this.listCountries[i].value === 'France') {
        return this.listCountries[i];
      }
    }
  }
}
