import { Prop, Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { Offices } from '@/shared/model/offices.model';
import { mapGetters } from 'vuex';
import { RegionZipList } from '@/shared/model/region-zip-list.model';
import { ListCountries } from '@/shared/model/list-countries.model';

@Component({
  computed: {
    ...mapGetters({
      regionZipList: 'regionZipList'
    })
  }
})
export default class LegalAgencyComponent extends Vue {
  @Prop() readonly office!: Offices;
  @Prop() readonly country!: string;

  fullAddress(): String {
    const address = this.office.address || '-';
    const post = this.office.post ? ', ' + this.office.post : '';
    return address + post;
  }

  address(): String {
    const address = this.office.address || '-';
    return address;
  }

  post(): String {
    const post = this.office.post ? this.office.post + ' ' : '';
    return post;
  }

  getRegion(): String {
    if (!this.office.post || this.country !== 'France') {
      return 'Unknown';
    }
    const regionZipList: RegionZipList[] = this.$store.getters.regionZipList;
    for (let i = 0; i < regionZipList.length; i++) {
      if (this.office.post.indexOf(regionZipList[i].zip) === 0) {
        return regionZipList[i].region;
      }
    }
    return '';
  }
}
