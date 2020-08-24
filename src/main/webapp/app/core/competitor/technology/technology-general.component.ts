import { Component, Inject, Mixins } from 'vue-property-decorator';
import HeadOfComponent from '@/core/competitor/head-of.vue';
import { People } from '@/shared/model/people.model';
import TechnologySection from './technology-section.vue';

import ListCompetanciesService from '@/entities/list-competancies/list-competancies.service';
import ListToolsService from '@/entities/list-tools/list-tools.service';
import ListServicesService from '@/entities/list-services/list-services.service';
import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';
import ListProjectTypesService from '@/entities/list-project-types/list-project-types.service';

import TechCompetanciesService from '@/entities/tech-competancies/tech-competancies.service';
import TechToolsService from '@/entities/tech-tools/tech-tools.service';
import TechServicesService from '@/entities/tech-services/tech-services.service';
import TechPartnersService from '@/entities/tech-partners/tech-partners.service';
import TechProjectsService from '@/entities/tech-projects/tech-projects.service';
import TechnologyAddModal from '@/core/competitor/technology/technology-add.vue';
import { mapGetters } from 'vuex';
import CompetitiveRateComponent from '@/core/competitor/competitive-rate.vue';
import { Competitors } from '@/shared/model/competitors.model';
import PeopleService from '@/entities/people/people.service';
import TechnologyEditCtoModal from '@/core/competitor/technology/technology-edit-cto.vue';
import TechInfoService from '@/entities/tech-info/tech-info.service';
import { TechInfo } from '@/shared/model/tech-info.model';
import AccessMixin from '@/account/AccessMixin';
import ListPracticesService from '@/entities/list-practices/list-practices.service';
import TechPracticesService from '@/entities/tech-practices/tech-practices.service';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';
import { RateTypes } from '@/shared/model/competitive-rates.model';

export const CTO = 'CTO';

@Component({
  name: 'technology-general',
  components: {
    'head-of': HeadOfComponent,
    TechnologySection,
    'technology-add-modal': TechnologyAddModal,
    'competitive-rate': CompetitiveRateComponent,
    'technology-edit-cto-modal': TechnologyEditCtoModal
  },
  computed: {
    ...mapGetters({
      dictionaries: 'dictionaries',
      competitor: 'competitor'
    })
  }
})
export default class TechnologyGeneral extends Mixins(AccessMixin) {
  public competitor!: Competitors;

  @Inject('peopleService') private peopleService: () => PeopleService;
  @Inject('techInfoService') private techInfoService: () => TechInfoService;

  @Inject('listCompetanciesService') public listCompetanciesService: () => ListCompetanciesService;
  @Inject('listPracticesService') public listPracticesService: () => ListPracticesService;
  @Inject('listToolsService') public listToolsService: () => ListToolsService;
  @Inject('listServicesService') public listServicesService: () => ListServicesService;
  @Inject('listTechPartnersService') public listTechPartnersService: () => ListTechPartnersService;
  @Inject('listProjectTypesService') public listProjectTypesService: () => ListProjectTypesService;

  @Inject('techCompetanciesService') public techCompetanciesService: () => TechCompetanciesService;
  @Inject('techPracticesService') public techPracticesService: () => TechPracticesService;
  @Inject('techToolsService') public techToolsService: () => TechToolsService;
  @Inject('techServicesService') public techServicesService: () => TechServicesService;
  @Inject('techPartnersService') public techPartnersService: () => TechPartnersService;
  @Inject('techProjectsService') public techProjectsService: () => TechProjectsService;
  @Inject('competitiveRatesService') public competitiveRatesService: () => CompetitiveRatesService;

  public dictionaries: any;
  public entity = '';
  public techInfo: TechInfo = new TechInfo();

  get headCTO(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === CTO) : null;
  }

  async mounted() {
    await Promise.all([this.fetchCTO(), this.fetchTechInfo()]);
    this.$root.$on('update_flags', this.updateRate);
  }

  private async fetchCTO() {
    const response = await this.peopleService().retrieveByCompetitorIdAndTitle(Number(this.$route.params.id), CTO);
    response.forEach(employee => this.$store.commit('addCompetitorPeople', employee));
  }

  private async fetchTechInfo() {
    const response = await this.techInfoService().retrieveByCompetitorId(Number(this.$route.params.id));
    this.techInfo = response && response.length > 0 ? response[0] : null;
  }

  get headDisplayName(): String {
    let result = '';
    if (this.headCTO) {
      result += this.headCTO.fName || '';
      result += this.headCTO.lName ? ' ' + this.headCTO.lName : '';
    }
    return result;
  }

  openAddModal(value: any) {
    this.entity = value;
    this.$root.$emit('bv::show::modal', 'technology-add-modal');
  }

  showEditCTO() {
    this.$root.$emit('bv::show::modal', 'technology-edit-cto-modal');
  }

  updateTechInfo(data: TechInfo) {
    this.techInfo = data;
  }

  get rawTotalRate(): number {
    return (
      (this.competitor['techCompetancies'] !== null ? this.competitor['techCompetancies'].length : 0) /
        Object.keys(this.dictionaries['techCompetancies']).length +
      (this.competitor['techPractices'] !== null ? this.competitor['techPractices'].length : 0) /
        Object.keys(this.dictionaries['techPractices']).length +
      (this.competitor['techTools'] !== null ? this.competitor['techTools'].length : 0) /
        Object.keys(this.dictionaries['techTools']).length +
      (this.competitor['techServices'] !== null ? this.competitor['techServices'].length : 0) /
        Object.keys(this.dictionaries['techServices']).length +
      (this.competitor['techPartners'] !== null ? this.competitor['techPartners'].length : 0) /
        Object.keys(this.dictionaries['techPartners']).length +
      (this.competitor['techProjects'] !== null ? this.competitor['techProjects'].length : 0) /
        Object.keys(this.dictionaries['techProjects']).length
    );
  }

  get rate() {
    return this.competitor.competitiveRates[0].techRate;
  }

  async updateRate() {
    this.$store.commit(
      'setCompetitorCompetitiveRates',
      await this.competitiveRatesService().update(Number(this.$route.params.id), RateTypes.TECHNICAL, this.rawTotalRate)
    );
  }
}
