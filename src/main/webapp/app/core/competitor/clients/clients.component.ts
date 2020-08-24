import { Component, Inject, Mixins, Prop } from 'vue-property-decorator';
import ClientsService from '@/entities/clients/clients.service';
import { People } from '@/shared/model/people.model';
import { Competitors } from '@/shared/model/competitors.model';
import { IClients } from '@/shared/model/clients.model';
import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';
import { ListIndustries } from '@/shared/model/list-industries.model';
import AddClient from '@/core/competitor/clients/add-client.vue';
import EditSales from '@/core/competitor/clients/edit-sales.vue';
import { mapGetters } from 'vuex';
import HeadOfComponent from '@/core/competitor/head-of.vue';
import PeopleService from '@/entities/people/people.service';
import CompetitiveRateComponent from '@/core/competitor/competitive-rate.vue';
import AccessMixin from '@/account/AccessMixin';
import LocalisationMixin from '@/locale/localisation.mixin';
import Question from '@/core/components/question.vue';

export const HEAD_SALES = 'HEAD_SALES';

@Component({
  name: 'clients',
  components: {
    'add-client': AddClient,
    'edit-sales': EditSales,
    'head-of': HeadOfComponent,
    'competitive-rate': CompetitiveRateComponent,
    Question
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  }
})
export default class Clients extends Mixins(AccessMixin, LocalisationMixin) {
  public competitor!: Competitors;

  @Inject('clientsService') private clientsService: () => ClientsService;
  @Inject('clientsProjectsService') private clientsProjectsService: () => ClientsProjectsService;
  @Inject('peopleService') private peopleService: () => PeopleService;

  @Prop()
  public compId: number;

  public clients = [];
  public addClientId: number = null;

  get headSales(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === HEAD_SALES) : null;
  }

  async mounted() {
    this.$root.$on('change_in_clients', this.fetchClients);
    await Promise.all([this.fetchClients(), this.fetchHeadSales()]);
  }

  get competitorId(): number {
    if (!this.compId) {
      console.error('Clients: competitorId is null');
    }
    return !!this.compId ? this.compId : 0;
  }

  get pricing(): string {
    const name =
      this.competitor.legal && this.competitor.legal.length > 0 && this.competitor.legal[0].pricing
        ? this.competitor.legal[0].pricing.value
        : '';
    return this.localisationFromName(name);
  }

  private async fetchHeadSales() {
    const response = await this.peopleService().retrieveByCompetitorIdAndTitle(Number(this.$route.params.id), HEAD_SALES);
    response.forEach(employee => this.$store.commit('addCompetitorPeople', employee));
  }

  public async fetchClients(): Promise<void> {
    this.clients = await this.clientsService().retrieveByCompetitorId(this.competitorId);
  }

  get headDisplayName(): String {
    let result = '';
    if (this.headSales) {
      result += this.headSales.fName || '';
      result += this.headSales.lName ? ' ' + this.headSales.lName : '';
    }
    return result;
  }

  public getStatus(c: IClients): string {
    if (c.projects.length > 0) {
      return c.projects[0].status;
    }
    return 'unknown';
  }

  public async deleteClient(c: IClients): Promise<void> {
    try {
      await this.clientsService().delete(c.id);
      this.clients.splice(this.clients.indexOf(c), 1);
      this.$root.$emit('update_flags');
    } catch (err) {
      console.log('Failed to delete client.', err);
    }
  }

  public formatUrl(url: String): String {
    return url.toLowerCase().includes('http') ? url : '//' + url;
  }

  public showEditClients(): void {
    this.$root.$emit('bv::show::modal', 'edit-clients-page');
  }

  public addClient(): void {
    this.addClientId = null;
    this.$root.$emit('bv::show::modal', 'add-client');
  }

  public showEditOneClient(c: IClients): void {
    this.addClientId = c.id;
    this.$root.$emit('bv::show::modal', 'add-client');
  }

  getIndustry(ind: ListIndustries): string {
    let str = '';
    if (ind.value.indexOf('@') > 0) {
      if (this.$store.getters.currentLanguage === 'fr') {
        str = ind.value.substring(ind.value.indexOf('@') + 1);
      } else {
        str = ind.value.substring(0, ind.value.indexOf('@'));
      }
    } else {
      str = ind.value;
    }
    return str;
  }

  get rate() {
    return this.competitor.competitiveRates[0].clientsRate;
  }
}
