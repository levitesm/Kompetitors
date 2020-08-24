import { Component, Inject, Mixins, Prop, Watch } from 'vue-property-decorator';
import CompetitorsService from '@/entities/competitors/competitors.service';
import LegalServices from '@/entities/legal/legal.service';
import GlobalGroupsService from '@/entities/global-groups/global-groups.service';
import { Competitors } from '@/shared/model/competitors.model';
import JhiDataUtils from '@/shared/data/data-utils.service';
import FinanceService from '@/entities/finance/finance.service';
import { Finance } from '@/shared/model/finance.model';
import { mapGetters } from 'vuex';
import { GlobalGroups } from '@/shared/model/global-groups.model';
import Question from '@/core/components/question.vue';

@Component({
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  },
  components: {
    Question
  }
})
export default class EditCompetitorMain extends Mixins(JhiDataUtils) {
  public competitor!: Competitors;

  @Inject('competitorsService')
  private competitorsService: () => CompetitorsService;

  @Inject('legalService')
  private legalService: () => LegalServices;

  @Inject('financeService')
  private financeService: () => FinanceService;

  @Inject('globalGroupsService')
  private globalGroupsService: () => GlobalGroupsService;

  @Prop() public revenue: number;

  public competitorModel: Competitors = new Competitors();
  public globalGroupModel: GlobalGroups = new GlobalGroups();
  public foundedModel = '0';
  public revenueModel = '0';

  public savingError = null;

  public get founded(): number {
    return this.competitor && this.competitor.legal && this.competitor.legal.length > 0
      ? new Date(this.competitor.legal[0].founded).getFullYear()
      : 0;
  }

  @Watch('revenue') watchRevenue() {
    this.resetForm();
  }

  public resetForm() {
    this.competitorModel = this.competitor ? { ...this.competitor } : Competitors.fromId(Number(this.$route.params.id));
    this.globalGroupModel = this.competitor && this.competitor.globalGroups ? { ...this.competitor.globalGroups } : new GlobalGroups();
    this.foundedModel = this.founded.toString();
    this.revenueModel = this.revenue.toString();
  }

  public async save() {
    if (!this.validate()) {
      return;
    }

    const promises: Promise<void>[] = [];
    try {
      if (
        this.competitor.globalGroups.logo !== this.globalGroupModel.logo ||
        this.competitor.globalGroups.logoContentType !== this.globalGroupModel.logoContentType ||
        this.competitor.globalGroups.webSite !== this.globalGroupModel.webSite
      ) {
        promises.push(this.saveGlobalGroup());
      }

      if (this.founded.toString() !== this.foundedModel && this.competitor.legal && this.competitor.legal.length > 0) {
        promises.push(this.saveLegal());
      }

      if (this.competitor.webSite !== this.competitorModel.webSite) {
        promises.push(this.saveCompetitor());
      }

      if (this.revenue.toString() !== this.revenueModel) {
        promises.push(this.saveRevenue());
      }

      if (promises.length > 0) {
        await Promise.all(promises);
      }
      this.$root.$emit('bv::hide::modal', 'edit-competitor-main');
    } catch (err) {
      this.savingError = err;
    }
  }

  public async saveGlobalGroup(): Promise<void> {
    this.$store.commit('setGlobalGroup', await this.globalGroupsService().update(this.globalGroupModel));
  }

  public async saveLegal(): Promise<void> {
    const legal = this.competitor.legal[0];
    legal.founded = new Date(this.foundedModel + '-01-01');
    legal.competitor = Competitors.fromId(Number(this.$route.params.id));
    this.$store.commit('setLegal', await this.legalService().update(legal));
  }

  public async saveCompetitor(): Promise<void> {
    this.$store.commit('setCompetitor', await this.competitorsService().update(this.competitorModel));
  }

  public async saveRevenue(): Promise<void> {
    const lastFinance =
      this.competitor.finances && this.competitor.finances.length > 0
        ? this.competitor.finances.reduce((prev, current) => (prev.year > current.year ? prev : current))
        : null;
    if (lastFinance && lastFinance.year === new Date().getFullYear() - 1) {
      lastFinance.competitors = Competitors.fromId(Number(this.$route.params.id));
      lastFinance.revenue = Number(this.revenueModel);
      this.$store.commit('setFinance', await this.financeService().update(lastFinance));
    } else {
      const finance = new Finance();
      finance.competitors = Competitors.fromId(Number(this.$route.params.id));
      finance.revenue = Number(this.revenueModel);
      finance.year = new Date().getFullYear() - 1;
      this.$store.commit('setFinance', await this.financeService().create(finance));
    }
  }

  public validate(): boolean {
    if (!this.foundedModel || this.foundedModel.length !== 4) {
      this.savingError = 'Foundation year format should be four digits NNNN';
      return;
    }
    if (!this.revenueModel || !/^\d+$/.test(this.revenueModel)) {
      this.savingError = 'Revenue should be a positive number';
      return;
    }

    this.savingError = null;
    return true;
  }

  public getLogo(): string {
    if (this.globalGroupModel && this.globalGroupModel.logo) {
      return 'data:' + this.globalGroupModel.logoContentType + '";base64,' + this.globalGroupModel.logo;
    }
    return '../../content/images/logo2.svg';
  }

  public selectFile() {
    (<any>this.$refs.fileLogo).click();
  }

  public resLogo(): void {
    this.globalGroupModel.logo = null;
    this.globalGroupModel.logoContentType = null;
  }
}
