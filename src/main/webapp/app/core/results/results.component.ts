import { Component, Vue, Inject, Watch } from 'vue-property-decorator';
import { GroupsSearchDTO } from '@/shared/model/group-search.model';
import GlobalGroupsService from '@/entities/global-groups/global-groups.service';
import 'vuelayers/lib/style.css';
import OfficesService from '@/entities/offices/offices.service';
import { OfficeSearchDTO } from '@/shared/model/office-search.model';
import CmpMap from '@/core/components/cmp-map.vue';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';

@Component({
  components: {
    'cmp-map': CmpMap
  }
})
export default class Results extends Vue {
  @Inject('globalGroupsService') private globalGroupsService: () => GlobalGroupsService;
  @Inject('officesService') private officesService: () => OfficesService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;

  public offices: OfficeSearchDTO[] = [];

  public maximalTotalRate = null;

  public groups: GroupsSearchDTO[] = [];
  public filterModel = {
    isOpen: false,
    options: {
      All: '',
      '+50M€': 'M50',
      '-50M€': 'L50',
      Listed: 'listed',
      Transparent: 'transparency',
      'Not transparent': 'noTransparency',
      Independent: 'independence',
      Private: 'privateEquity'
    },
    selected: 'All'
  };

  public sortModel = {
    isOpen: false,
    options: {
      'A-Z': 'AZ',
      Revenue: 'revenueDesc',
      Agencies: 'agenciesDesc',
      Employees: 'employeesDesc',
      Glassdoor: 'glassdoorDesc',
      Viadeo: 'viadeoDesc'
    },
    selected: 'A-Z'
  };

  $refs!: {
    // <- "!" is needed
    filterBtnBlock: HTMLElement;
    sortBtnBlock: HTMLElement;
  };

  public applyFilter(option: string) {
    this.filterModel.selected = option;
    this.filterModel.isOpen = false;
    this.$router.push({
      query: Object.assign({}, this.$route.query, { filter: this.filterModel.options[option] })
    });
  }

  public applySort(option: string) {
    this.sortModel.selected = option;
    this.sortModel.isOpen = false;
    if ('A-Z' === option) {
      this.groups.sort((a, b) => {
        if (a.name.toLowerCase() < b.name.toLowerCase()) {
          return -1;
        }
        if (a.name.toLowerCase() > b.name.toLowerCase()) {
          return 1;
        }
        return 0;
      });
    }
    if ('Revenue' === option) {
      this.groups.sort((a, b) => {
        return b.revenue - a.revenue;
      });
    }
    if ('Agencies' === option) {
      this.groups.sort((a, b) => {
        return b.agencies - a.agencies;
      });
    }
    if ('Employees' === option) {
      this.groups.sort((a, b) => {
        return b.employees - a.employees;
      });
    }
    if ('Glassdoor' === option) {
      this.groups.sort((a, b) => {
        return b.glassdoor - a.glassdoor;
      });
    }
    if ('Viadeo' === option) {
      this.groups.sort((a, b) => {
        return b.viadeo - a.viadeo;
      });
    }
  }

  async mounted() {
    this.closeDropdown();
    this.maximalTotalRate = await this.competitiveRatesService().getMaximalTotalRate();
  }

  // close filter dropdown when clicked outside
  closeDropdown() {
    const self = this;
    window.addEventListener('click', function(e) {
      if (self.$refs.filterBtnBlock && !self.$refs.filterBtnBlock.contains(e.target as Node)) {
        self.filterModel.isOpen = false;
      }
      if (self.$refs.sortBtnBlock && !self.$refs.sortBtnBlock.contains(e.target as Node)) {
        self.sortModel.isOpen = false;
      }
    });
  }

  public async find(): Promise<void> {
    this.$root.$emit('loading_start');

    let reg = this.$data.region;
    if (reg === 'All Regions') {
      reg = '';
    }

    this.groups = await this.globalGroupsService().findByWhatAndWhereAndRegion(
      this.$data.what_field,
      this.$data.where_field,
      reg,
      this.$data.country
    );
    this.$root.$emit('loading_stop');

    try {
      this.offices = await this.officesService().findOffices(this.$data.what_field, this.$data.where_field, reg, this.$data.country);
    } catch (err) {
      console.log('Failed to fetch offices!', err);
    }
  }

  @Watch('$route', { immediate: true, deep: true })
  async onUrlChange(to: any, from: any) {
    if (
      !from ||
      to.query.what !== from.query.what ||
      to.query.where !== from.query.where ||
      to.query.region !== from.query.region ||
      to.query.country !== from.query.country
    ) {
      this.$data.what_field = (this.$route.query.what || '').toString().trim();
      this.$data.where_field = (this.$route.query.where || '').toString().trim();
      this.$data.region = (this.$route.query.region || '').toString().trim();
      this.$data.country = (this.$route.query.country || '').toString().trim();
      await this.find();
    }
    this.$data.filter = (this.$route.query.filter || '').toString().trim();
    this.$data.empFrom = Number(to.query.empFrom) || 0;
    this.$data.empTo = Number(to.query.empTo) || 0;
    this.$data.transparency = Boolean(to.query.transparency) ? to.query.transparency === 'true' : null;
    this.$data.privateEquity = Boolean(to.query.privateEquity) ? to.query.privateEquity === 'true' : null;
    this.$data.listed = Boolean(to.query.listed) ? to.query.listed === 'true' : null;

    this.filterModel.selected = Object.keys(this.filterModel.options).find(key => this.filterModel.options[key] === this.$data.filter);
  }

  get filteredGroups(): GroupsSearchDTO[] {
    let result = [...this.groups.filter(a => a.reference !== true)];
    if (this.$data.filter === 'M50') {
      result = result.filter(a => this.getLastRevenue(a) >= 50);
    }
    if (this.$data.filter === 'L50') {
      result = result.filter(a => this.getLastRevenue(a) < 50);
    }
    if (this.$data.filter === 'listed') {
      result = result.filter(a => a.listed === true);
    }
    if (this.$data.filter === 'transparency') {
      result = result.filter(a => a.transparency === true);
    }
    if (this.$data.filter === 'noTransparency') {
      result = result.filter(a => a.transparency === false);
    }
    if (this.$data.filter === 'independence') {
      result = result.filter(a => a.independent === true);
    }
    if (this.$data.filter === 'privateEquity') {
      result = result.filter(a => a.privatecapital === true);
    }
    if (this.$data.empFrom > 0) {
      result = result.filter(a => a.employees > this.$data.empFrom);
    }
    if (this.$data.empTo > 0) {
      result = result.filter(a => a.employees < this.$data.empTo);
    }
    if (this.$data.transparency !== null) {
      result = result.filter(a => a.transparency === this.$data.transparency);
    }
    if (this.$data.privateEquity !== null) {
      result = result.filter(a => a.privatecapital === this.$data.privateEquity);
    }
    if (this.$data.listed !== null) {
      result = result.filter(a => a.listed === this.$data.listed);
    }
    return result;
  }

  public showCompetitor(inId): void {
    this.$router.push({
      name: 'Competitor',
      params: {
        id: inId,
        what: this.$data.what_field,
        where: this.$data.where_field,
        filter: this.$data.filter,
        region: this.$data.region,
        country: this.$data.country,
        empFrom: this.$data.empFrom,
        empTo: this.$data.empTo,
        transparency: this.$data.transparency,
        privateEquity: this.$data.privateEquity,
        listed: this.$data.listed
      }
    });
  }

  public getLastRevenue(item: GroupsSearchDTO): number {
    return Math.round(item.revenue / 100000) / 10;
  }

  public getLastRevenueMillions(item: GroupsSearchDTO): number {
    let result = Math.round(item.revenue / 100000) / 10;
    if (result >= 100000) {
      result = Math.round(result / 1000 + Number.EPSILON);
    } else if (result >= 1000) {
      result = Math.round((result / 1000 + Number.EPSILON) * 10) / 10;
    } else if (result >= 100) {
      result = Math.round(result + Number.EPSILON);
    } else if (result >= 1) {
      result = Math.round((result + Number.EPSILON) * 10) / 10;
    } else {
      result = 0;
    }
    return result;
  }

  public getLastRevenueUnit(item: GroupsSearchDTO): string {
    let result = ' €';
    if (item.revenue >= 1000000000) {
      result = ' B' + result;
    } else if (item.revenue >= 1000000) {
      result = ' M' + result;
    }
    return result;
  }

  public formatName(str: string): string {
    if (str.length > 14) {
      return str.slice(0, 12) + ' . . .';
    }
    return str;
  }

  data() {
    return {
      what_field: this.$route && this.$route.query && this.$route.query.what ? this.$route.query.what : '',
      where_field: this.$route && this.$route.query && this.$route.query.where ? this.$route.query.where : '',
      filter: this.$route && this.$route.query && this.$route.query.filter ? this.$route.query.filter : '',
      region: this.$route && this.$route.query && this.$route.query.region ? this.$route.query.region : '',
      country: this.$route && this.$route.query && this.$route.query.country ? this.$route.query.country : '',
      empFrom: this.$route && this.$route.query && this.$route.query.empFrom ? Number(this.$route.query.empFrom) : 0,
      empTo: this.$route && this.$route.query && this.$route.query.empTo ? Number(this.$route.query.empTo) : 0,
      transparency: this.$route && this.$route.query && this.$route.query.transparency ? Boolean(this.$route.query.transparency) : null,
      privateEquity: this.$route && this.$route.query && this.$route.query.privateEquity ? Boolean(this.$route.query.privateEquity) : null,
      listed: this.$route && this.$route.query && this.$route.query.listed ? Boolean(this.$route.query.listed) : null
    };
  }

  public getLogo(item): string {
    if (item.logo != null) {
      return 'data:' + item.logocontenttype + '";base64,' + item.logo;
    }
    return '../../content/images/logo2.svg';
  }

  goToAgencies(competitorId: number) {
    this.$router.push({
      path: `/competitor/${competitorId}?section=1&subsection=5`
    });
  }

  techRate(rate: number): number {
    return Math.round(rate / 20);
  }

  totalRate(rate: number): number {
    if (this.maximalTotalRate === null) {
      return 0;
    }
    return Math.round((rate / this.maximalTotalRate) * 5);
  }
}
