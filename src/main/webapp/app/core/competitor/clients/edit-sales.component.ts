import Component from 'vue-class-component';
import { Vue, Inject, Prop, Mixins } from 'vue-property-decorator';
import { Competitors } from '@/shared/model/competitors.model';
import { People } from '@/shared/model/people.model';
import PeopleService from '@/entities/people/people.service';
import LegalService from '@/entities/legal/legal.service';
import ListPricingsService from '@/entities/list-pricings/list-pricings.service';
import { Legal } from '@/shared/model/legal.model';
import { ListPricings } from '@/shared/model/list-pricings.model';
import { mapGetters } from 'vuex';
import { HEAD_SALES } from '@/core/competitor/clients/clients.component';
import LocalisationMixin from '@/locale/localisation.mixin';

@Component({
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  }
})
export default class EditSales extends Mixins(LocalisationMixin) {
  public competitor!: Competitors;

  @Inject('listPricingsService')
  private listPricingsService: () => ListPricingsService;
  @Inject('legalService')
  private legalService: () => LegalService;
  @Inject('peopleService')
  private peopleService: () => PeopleService;

  public savingError = null;

  public legalModel = new Legal();
  public head_sales = new People();

  public pricingList = [];
  public selectedPricing = new ListPricings();

  get headSales(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === HEAD_SALES) : null;
  }

  get legal(): Legal {
    return this.competitor.legal && this.competitor.legal.length > 0 ? this.competitor.legal[0] : null;
  }

  async mounted() {
    if (this.headSales) {
      this.head_sales = { ...this.headSales };
    } else {
      this.head_sales = new People();
      this.head_sales.isKey = true;
      this.head_sales.title = HEAD_SALES;
    }
    this.head_sales.competitors = new Competitors();
    this.head_sales.competitors.id = Number(this.$route.params.id);

    if (this.legal) {
      this.legalModel = { ...this.legal };
    }
    this.legalModel.competitor = Competitors.fromId(Number(this.$route.params.id));

    this.pricingList = (await this.listPricingsService().retrieve()).data;
  }

  public async saveChanges(): Promise<void> {
    try {
      if (this.legalModel.id) {
        this.$store.commit('setCompetitorLegal', await this.legalService().update(this.legalModel));
      } else {
        this.$store.commit('setCompetitorLegal', await this.legalService().create(this.legalModel));
      }

      if (this.head_sales.id) {
        this.$store.commit('addCompetitorPeople', await this.peopleService().update(this.head_sales));
      } else {
        this.$store.commit('addCompetitorPeople', await this.peopleService().create(this.head_sales));
      }

      this.savingError = false;

      this.$root.$emit('bv::hide::modal', 'edit-clients-page');
      this.$root.$emit('update_flags');
    } catch (err) {
      this.savingError = err;
    }
  }
}
