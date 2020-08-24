import { Component, Vue } from 'vue-property-decorator';
import { mapGetters } from 'vuex';
import { AdvancedSearch } from '@/shared/model/advanced-search.model';

@Component({
  computed: {
    ...mapGetters({
      regionList: 'regionList'
    })
  }
})
export default class JhiSearch extends Vue {
  public selectedRegion: String = 'All Regions';
  public selectedCountry: String = 'France';
  public showAdvanced = false;

  public advSearchModel: AdvancedSearch = new AdvancedSearch();

  data() {
    return {
      what_field: '',
      where_field: ''
    };
  }

  public search(): void {
    this.$router.push({
      name: 'Results',
      query: {
        what: this.$data.what_field,
        where: this.$data.where_field,
        region: this.selectedRegion.toString(),
        country: this.selectedCountry.toString()
      }
    });
  }

  public advancedSearch(): void {
    const query = {
      what: this.$data.what_field,
      where: this.$data.where_field,
      region: this.selectedRegion.toString(),
      country: this.selectedCountry.toString()
    };
    if (this.advSearchModel.empFrom && this.advSearchModel.empFrom > 0) {
      Object.assign(query, { empFrom: this.advSearchModel.empFrom.toString() });
    } else {
      this.advSearchModel.empFrom = null;
    }
    if (this.advSearchModel.empTo && this.advSearchModel.empTo > 0) {
      Object.assign(query, { empTo: this.advSearchModel.empTo.toString() });
    } else {
      this.advSearchModel.empTo = null;
    }
    if (this.advSearchModel.transparency !== null) {
      Object.assign(query, { transparency: this.advSearchModel.transparency.toString() });
    }
    if (this.advSearchModel.privateEquity !== null) {
      Object.assign(query, { privateEquity: this.advSearchModel.privateEquity.toString() });
    }
    if (this.advSearchModel.listed !== null) {
      Object.assign(query, { listed: this.advSearchModel.listed.toString() });
    }
    this.$router.push({
      name: 'Results',
      query
    });
  }

  public clear(): void {
    this.clear_and_hide();
    this.search();
  }

  public clear_and_hide(): void {
    this.$data.what_field = '';
    this.$data.where_field = '';
    this.selectedRegion = 'All Regions';
    this.selectedCountry = 'France';
    this.closeAdvanced();
    this.$root.$emit('close-search');
  }

  public closeAdvanced() {
    this.showAdvanced = false;
    this.advSearchModel = new AdvancedSearch();
  }

  public close(): void {
    this.$root.$emit('close-search');
  }

  mounted() {
    this.$root.$on('clear_hide_search', this.clear_and_hide);
  }

  resetRegion(): void {
    this.selectedRegion = 'All Regions';
  }
}
