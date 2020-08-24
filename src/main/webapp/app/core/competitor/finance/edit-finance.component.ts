import { Inject, Vue } from 'vue-property-decorator';
import { People } from '@/shared/model/people.model';
import PeopleService from '@/entities/people/people.service';
import Component from 'vue-class-component';
import { Competitors } from '@/shared/model/competitors.model';
import { mapGetters } from 'vuex';
import { HEAD_FINANCE } from '@/core/competitor/finance/finance-general.component';

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'edit-finance-page');
    }
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  }
})
export default class EditFinanceModal extends Vue {
  public competitor!: Competitors;
  public savingError = false;

  @Inject('peopleService')
  private peopleService: () => PeopleService;

  get headFinance(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === HEAD_FINANCE) : null;
  }

  public model: People = {};

  mounted() {
    if (this.headFinance) {
      this.model = { ...this.headFinance };
    } else {
      this.model = new People();
      this.model.isKey = true;
      this.model.title = HEAD_FINANCE;
    }
    this.model.competitors = new Competitors();
    this.model.competitors.id = Number(this.$route.params.id);
  }

  public async saveChanges(): Promise<void> {
    try {
      if (this.model.id) {
        this.$store.commit('addCompetitorPeople', await this.peopleService().update(this.model));
      } else {
        this.$store.commit('addCompetitorPeople', await this.peopleService().create(this.model));
      }
      this.savingError = false;

      this.$root.$emit('bv::hide::modal', 'edit-finance-page');
      this.$root.$emit('update_flags');
    } catch (err) {
      this.savingError = err;
    }
  }
}
