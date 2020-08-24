import { Component, Mixins, Prop, Watch } from 'vue-property-decorator';
import TechnologyEdit from './technology-edit.vue';
import { mapGetters } from 'vuex';
import { Competitors } from '@/shared/model/competitors.model';
import { TechCompetancies } from '@/shared/model/tech-competancies.model';
import { TechTools } from '@/shared/model/tech-tools.model';
import { TechServices } from '@/shared/model/tech-services.model';
import { TechPartners } from '@/shared/model/tech-partners.model';
import { TechProjects } from '@/shared/model/tech-projects.model';
import { ListCompetancies } from '@/shared/model/list-competancies.model';
import { ListTools } from '@/shared/model/list-tools.model';
import { ListServices } from '@/shared/model/list-services.model';
import { ListTechPartners } from '@/shared/model/list-tech-partners.model';
import { ListProjectTypes } from '@/shared/model/list-project-types.model';
import AccessMixin from '@/account/AccessMixin';
import { ListPractices } from '@/shared/model/list-practices.model';
import { TechPractices } from '@/shared/model/tech-practices.model';

@Component({
  name: 'technology-section',
  components: {
    TechnologyEdit
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor',
      dictionaries: 'dictionaries'
    })
  }
})
export default class TechnologySection extends Mixins(AccessMixin) {
  @Prop() public title: string;
  @Prop() public collectionName: string;
  @Prop() public dictionaryService: Function;
  @Prop() public techService: Function;

  public competitor: Competitors;
  public dictionaries: any;

  @Watch('competitor')
  async onCompetitorChange() {
    await this.fetchValues();
  }

  async mounted() {
    await this.fetchValues();
  }

  async fetchValues() {
    try {
      this.$store.commit('setDictionary', {
        section: this.collectionName,
        data: (await this.dictionaryService().retrieve()).data
      });
      this.$store.commit('setTechSection', {
        section: this.collectionName,
        data: await this.techService().retrieveByCompetitorId(Number(this.$route.params.id))
      });
    } catch (err) {
      console.error(err);
    }
  }

  get rate(): number {
    if (Number.isNaN(Math.round((this.values.length / Object.keys(this.dictionary).length) * 5))) {
      return 0;
    }
    return Math.round((this.values.length / Object.keys(this.dictionary).length) * 5);
  }
  get dictionary(): ListCompetancies[] | ListPractices[] | ListTools[] | ListServices[] | ListTechPartners[] | ListProjectTypes[] {
    return this.dictionaries.hasOwnProperty(this.collectionName) ? this.dictionaries[this.collectionName] : [];
  }

  get values(): TechCompetancies[] | TechPractices[] | TechTools[] | TechServices[] | TechPartners[] | TechProjects[] {
    return this.competitor && this.competitor[this.collectionName] ? this.competitor[this.collectionName] : [];
  }

  displayName(value: TechCompetancies | TechPractices | TechTools | TechServices | TechPartners | TechProjects, field: string): string {
    return value &&
      this.dictionaries.hasOwnProperty(this.collectionName) &&
      this.dictionaries[this.collectionName] &&
      this.dictionaries[this.collectionName][value.valueId] &&
      this.dictionaries[this.collectionName][value.valueId][field]
      ? this.dictionaries[this.collectionName][value.valueId][field]
      : '';
  }

  get pageId(): string {
    return (
      'edit-tech-section-' +
      this.title
        .toLowerCase()
        .replace(' ', '-')
        .replace('/', '-')
    );
  }

  public showEditModal(): void {
    this.$root.$emit('bv::show::modal', this.pageId);
  }

  showCreateModal() {
    this.$emit('addTechnology', this.collectionName);
  }

  async save(changes: any) {
    const body = [];
    const updatedValues = [...this.values];
    for (const key in changes) {
      if (changes.hasOwnProperty(key)) {
        if (changes[key]) {
          body.push({
            id: null,
            valueId: key,
            competitorId: this.$route.params.id
          });
        } else {
          const exist = this.values.find(value => value.valueId === Number(key));
          if (exist) {
            body.push(exist);
            updatedValues.splice(updatedValues.indexOf(exist), 1);
          }
        }
      }
    }
    const response = await this.techService().refresh(body);
    response.forEach(value => updatedValues.push(value));
    this.$store.commit('setTechSection', { section: this.collectionName, data: updatedValues });
  }
}
