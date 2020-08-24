import { Mixins, Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { mapGetters } from 'vuex';
import LegalAgencyComponent from '@/core/competitor/legal/legal-agency.vue';
import { Competitors } from '@/shared/model/competitors.model';
import EditLocations from '@/core/competitor/legal/edit-locations.vue';
import AccessMixin from '@/account/AccessMixin';

@Component({
  components: {
    'legal-agency': LegalAgencyComponent,
    'edit-locations': EditLocations
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor',
      competitorsInGroup: 'competitorsInGroup'
    })
  }
})
export default class LegalAgenciesComponent extends Mixins(AccessMixin) {
  public competitor!: Competitors;
  public competitorsInGroup!: Competitors[];
  public groupCountries: String[] = [];
  public selectedCountry: String = '';

  private static getCountries(competitorsInGroup: Competitors[]): String[] {
    const list = [];
    for (let i = 0; i < competitorsInGroup.length; i++) {
      if (!list.includes(competitorsInGroup[i].country.value)) {
        list.push(competitorsInGroup[i].country.value);
      }
    }
    return list;
  }

  mounted() {
    this.groupCountries = LegalAgenciesComponent.getCountries(this.competitorsInGroup).sort();
    this.selectedCountry = this.groupCountries[0];
  }

  public getFlag(country: String): String {
    if (country === 'France') {
      return '/content/icons/flag-fr.png';
    }
    if (country === 'USA') {
      return '/content/icons/united-states.png';
    }
    if (country === 'Russia') {
      return '/content/icons/flag-ru.png';
    }
    if (country === 'Australia') {
      return '/content/icons/australia.png';
    }
    return '';
  }

  private openEditModal() {
    this.$root.$emit('bv::show::modal', 'edit-locations');
  }
}
