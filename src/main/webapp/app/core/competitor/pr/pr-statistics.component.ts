import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import PrInfoService from '@/entities/pr-info/pr-info.service';
import { PrInfo } from '@/shared/model/pr-info.model';
import { mapGetters } from 'vuex';
import { Competitors } from '@/shared/model/competitors.model';
import CmpListItem from '@/core/components/cmp-list-item.vue';
import AccessMixin from '@/account/AccessMixin';
import EditPrStatisticsModal from '@/core/competitor/pr/edit-pr-statistics.vue';
import CustomSpinner from '../../../../components/spinner/spinner.vue';

@Component({
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  },
  components: {
    'list-item': CmpListItem,
    'edit-pr-statistics-modal': EditPrStatisticsModal,
    'custom-spinner': CustomSpinner
  }
})
export default class PrStatisticsComponent extends Mixins(AccessMixin) {
  public competitor!: Competitors;
  @Inject('prInfoService') private prInfoService: () => PrInfoService;
  public loading = false;

  public get prInfo(): PrInfo {
    return this.competitor.prinfo && this.competitor.prinfo.length > 0
      ? this.competitor.prinfo.reduce((result, info) => {
          return info.date < result.date ? result : info;
        }, {})
      : new PrInfo();
  }

  async mounted() {
    await this.fetchPrInfo();
  }

  async fetchPrInfo() {
    try {
      this.loading = true;
      this.$store.commit('setPrInfo', await this.prInfoService().findByCompetitorId(Number(this.$route.params.id)));
    } catch (err) {
      console.log('Failed to fetch PrInfo:', err);
    } finally {
      this.loading = false;
    }
  }

  showEditPr() {
    this.$root.$emit('bv::show::modal', 'edit-pr-statistics-modal');
  }
}
