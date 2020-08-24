import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import EditPr from '@/core/competitor/pr/edit-pr.vue';
import AddCompetitor from '@/core/add-competitor/add-competitor.vue';
import EditHr from '@/core/competitor/hr/edit-hr.vue';
import EditHrEmployees from '@/core/competitor/hr/edit-hr-employees.vue';
import CustomSpinner from '../components/spinner/spinner.vue';
import Question from '@/core/components/question.vue';
import EditReps from '@/core/edit-reps/edit-reps.vue';
import { Inject } from 'vue-property-decorator';
import RegionZipListService from '@/entities/region-zip-list/region-zip-list.service';
import RegionListService from '@/entities/region-list/region-list.service';
import EditCompetitorMain from '@/core/competitor/edit-competitor-main.vue';

@Component({
  data() {
    return {
      loading: <boolean>false
    };
  },

  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'edit-pr': EditPr,
    'edit-reps': EditReps,
    'edit-hr': EditHr,
    'edit-hr-employees': EditHrEmployees,
    'edit-competitor-main': EditCompetitorMain,
    'jhi-footer': JhiFooter,
    'add-competitor': AddCompetitor,
    CustomSpinner,
    Question
  }
})
export default class App extends Vue {
  @Inject('regionZipListService')
  private regionZipListService: () => RegionZipListService;
  @Inject('regionListService')
  private regionListService: () => RegionListService;

  async mounted() {
    this.$data.loading = false;
    this.$root.$on('loading_start', () => {
      this.$data.loading = true;
    });
    this.$root.$on('loading_stop', () => {
      this.$data.loading = false;
    });
    this.$store.commit('setRegionZipList', (await this.regionZipListService().retrieve()).data);
    this.$store.commit('setRegionList', (await this.regionListService().retrieve()).data);
  }
}
