import { Inject, Mixins } from 'vue-property-decorator';
import Component from 'vue-class-component';
import ClientsService from '@/entities/clients/clients.service';
import { ListIndustries } from '@/shared/model/list-industries.model';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';
import CompetitorIndustryService from '@/entities/competitor-industry/competitor-industry.service';
import { CompetitorIndustry } from '@/shared/model/competitor-industry.model';
import { mapGetters } from 'vuex';
import Draggable from 'vuedraggable/src/vuedraggable.js';
import AddIndustry from '@/core/competitor/clients/add-industry.vue';
import AccessMixin from '@/account/AccessMixin';
import CompetitiveRatesService from '@/entities/competitive-rates/competitive-rates.service';
import { RateTypes } from '@/shared/model/competitive-rates.model';

@Component({
  computed: {
    ...mapGetters({
      currentLanguage: 'currentLanguage'
    })
  },
  components: {
    'add-industry': AddIndustry,
    Draggable
  }
})
export default class ClientsIndustryComponent extends Mixins(AccessMixin) {
  @Inject('clientsService') private clientsService: () => ClientsService;
  @Inject('listIndustriesService') private listIndustriesService: () => ListIndustriesService;
  @Inject('competitorIndustryService') private competitorIndustryService: () => CompetitorIndustryService;
  @Inject('competitiveRatesService') private competitiveRatesService: () => CompetitiveRatesService;

  public currentLanguage: string;

  public allIndustries: ListIndustries[] = [];
  public model: any = {};
  public existingIndustries: any = {};
  public competitorIndustries: CompetitorIndustry[] = [];
  public changes: any = {};

  public isSaveDisabled: Boolean = true;
  public competitorIndustriesDraggableList: ListIndustries[] = [];
  public availableIndustriesDraggableList: ListIndustries[] = [];

  public addIndustry(): void {
    this.$root.$emit('bv::show::modal', 'add-industry');
  }

  removeIndustry(industry) {
    this.competitorIndustriesDraggableList = this.competitorIndustriesDraggableList.filter(i => industry.id !== i.id);
    this.availableIndustriesDraggableList.push(industry);
    this.check(industry, false);
    this.$root.$emit('update_flags');
  }

  handleChange(event) {
    if (event.hasOwnProperty('added')) {
      const industry = event.added.element;
      this.check(industry, true);
    }
    if (event.hasOwnProperty('removed')) {
      const industry = event.removed.element;
      if (!this.existingIndustries[industry.id]) {
        this.check(industry, false);
      }
    }
  }

  displayName(value: string): string {
    if (!value.includes('@')) {
      return value;
    } else {
      const index = value.indexOf('@');
      if (this.currentLanguage === 'en') {
        return value.substring(0, index);
      }
      if (this.currentLanguage === 'fr') {
        return value.substring(index + 1);
      }
    }
    return value;
  }

  async mounted() {
    await this.fetchIndustries();
  }

  async fetchIndustries() {
    await this.fetchAllIndustries();
    await this.fetchClientIndustries();
    await this.fetchCompetitorIndustries();

    this.availableIndustriesDraggableList = this.allIndustries.filter(industry => !this.model[industry.id]);
    this.competitorIndustriesDraggableList = this.allIndustries.filter(industry => this.model[industry.id]);
  }

  async fetchAllIndustries() {
    this.allIndustries = (await this.listIndustriesService().retrieve()).data;
    const temp = {};
    this.allIndustries.forEach(industry => (temp[industry.id] = false));
    this.model = Object.assign(temp);
  }

  async fetchClientIndustries() {
    const clients = await this.clientsService().retrieveByCompetitorId(Number(this.$route.params.id));
    clients.forEach(client => {
      this.model[client.industry.id] = true;
    });
    this.existingIndustries = { ...this.model };
  }

  async fetchCompetitorIndustries() {
    this.competitorIndustries = await this.competitorIndustryService().getByCompetitorId(this.$route.params.id);
    this.competitorIndustries.forEach(dto => {
      this.model[dto.industryId] = true;
    });
  }

  check(industry: ListIndustries, value: boolean) {
    if (industry.id in this.changes) {
      delete this.changes[industry.id];
    } else {
      this.changes[industry.id] = value;
    }
    this.isSaveDisabled = this.noChanges();
  }

  noChanges(): boolean {
    for (const prop in this.changes) {
      if (this.changes.hasOwnProperty(prop)) {
        return false;
      }
    }
    return true;
  }

  async save() {
    const body: CompetitorIndustry[] = [];
    for (const key in this.changes) {
      if (this.changes.hasOwnProperty(key)) {
        const exist = this.competitorIndustries.find(industry => industry.industryId === Number(key));
        if (exist) {
          body.push(exist);
        } else {
          body.push(new CompetitorIndustry(null, Number(this.$route.params.id), Number(key)));
        }
      }
    }
    try {
      const response = await this.competitorIndustryService().refreshCompetitorIndustries(body);
      response.forEach(dto => this.competitorIndustries.push(dto));
      body.forEach(dto => {
        if (dto.id !== null) {
          this.competitorIndustries.splice(this.competitorIndustries.indexOf(dto), 1);
        }
      });
      this.model = { ...this.model, ...this.changes };
      // COMPETITIVE RATE
      this.$store.commit(
        'setCompetitorCompetitiveRates',
        await this.competitiveRatesService().update(
          Number(this.$route.params.id),
          RateTypes.CLIENTS,
          this.competitorIndustries.length / this.allIndustries.length
        )
      );
    } catch (err) {
      for (const key in this.changes) {
        if (this.changes.hasOwnProperty(key)) {
          this.model[key] = !this.model[key];
        }
      }
      console.error('Refresh failed, data rolled back! Error: ' + err);
    } finally {
      this.changes = {};
      this.isSaveDisabled = true;
    }
  }
}
