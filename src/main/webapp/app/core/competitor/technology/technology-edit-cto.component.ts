import { Component, Inject, Prop, Vue, Watch } from 'vue-property-decorator';
import { People } from '@/shared/model/people.model';
import { Competitors } from '@/shared/model/competitors.model';
import PeopleService from '@/entities/people/people.service';
import { TechInfo } from '@/shared/model/tech-info.model';
import TechInfoService from '@/entities/tech-info/tech-info.service';
import Question from '@/core/components/question.vue';

@Component({
  components: {
    Question
  }
})
export default class TechnologyEditCtoModal extends Vue {
  @Prop() public headCTO: People;
  @Prop() public techInfo: TechInfo;

  @Inject('peopleService') private peopleService: () => PeopleService;
  @Inject('techInfoService') private techInfoService: () => TechInfoService;

  public ctoModel: People = new People();
  public techInfoModel: TechInfo = new TechInfo();
  public savingError = null;

  @Watch('headCTO')
  resetCtoModel() {
    if (this.headCTO) {
      this.ctoModel = { ...this.headCTO };
    } else {
      this.ctoModel = new People();
      this.ctoModel.competitors = Competitors.fromId(Number(this.$route.params.id));
      this.ctoModel.title = 'CTO';
      this.ctoModel.isKey = true;
    }
  }

  @Watch('techInfo')
  resetTechInfoModel() {
    if (this.techInfo) {
      this.techInfoModel = { ...this.techInfo };
    } else {
      this.techInfoModel = new TechInfo();
      this.techInfoModel.competitorId = Number(this.$route.params.id);
    }
  }

  mounted() {
    this.resetCtoModel();
    this.resetTechInfoModel();
  }

  async saveChanges() {
    if (!this.validate()) {
      return;
    }
    try {
      if (this.ctoModel.id) {
        this.$store.commit('addCompetitorPeople', await this.peopleService().update(this.ctoModel));
      } else {
        this.$store.commit('addCompetitorPeople', await this.peopleService().create(this.ctoModel));
      }
      if (this.techInfoModel.id) {
        const response = await this.techInfoService().update(this.techInfoModel);
        this.$emit('updateTechInfo', response);
      } else {
        const response = await this.techInfoService().create(this.techInfoModel);
        this.$emit('updateTechInfo', response);
      }
      this.$root.$emit('bv::hide::modal', 'technology-edit-cto-modal');
      this.$root.$emit('update_flags');
    } catch (err) {
      this.savingError = err;
    }
  }

  validate(): boolean {
    this.savingError = null;
    if (isNaN(this.techInfoModel.techSpecialistsNumber)) {
      this.savingError = 'Number of specialists should be numeric.';
      return false;
    } else if (this.techInfoModel.techSpecialistsNumber < 0) {
      this.savingError = 'Number of specialists should be positive.';
      return false;
    }
    return true;
  }
}
