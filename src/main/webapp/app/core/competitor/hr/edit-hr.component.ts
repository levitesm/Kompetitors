import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import { Competitors } from '@/shared/model/competitors.model';
import { HrInfo } from '@/shared/model/hr-info.model';
import HrInfoService from '@/entities/hr-info/hr-info.service';
import { People } from '@/shared/model/people.model';
import PeopleService from '@/entities/people/people.service';
import { mapGetters } from 'vuex';

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'edit-hr-page');
    }
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  }
})
export default class EditHr extends Vue {
  public competitor!: Competitors;

  @Inject('hrInfoService')
  private hrInfoService: () => HrInfoService;
  @Inject('peopleService')
  private peopleService: () => PeopleService;

  public savingError = null;

  private headModel: People = new People();
  private headModel2: People = new People();
  private hrInfoModel: HrInfo = new HrInfo();

  get head_hr(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === 'HEAD_HR') : null;
  }
  get head_recr(): People {
    return this.competitor.people ? this.competitor.people.find(p => p.title === 'HEAD_RECR') : null;
  }

  get hrInfo(): HrInfo {
    return this.competitor.hr && this.competitor.hr.length > 0 ? this.competitor.hr[0] : undefined;
  }

  async mounted() {
    const comp = Competitors.fromId(Number(this.$route.params.id));
    if (this.head_hr) {
      this.headModel = { ...this.head_hr };
    } else {
      this.headModel.isKey = true;
      this.headModel.title = 'HEAD_HR';
    }
    this.headModel.competitors = comp;
    if (this.head_recr) {
      this.headModel2 = { ...this.head_recr };
    } else {
      this.headModel2.isKey = true;
      this.headModel2.title = 'HEAD_RECR';
    }
    this.headModel2.competitors = comp;
    if (this.hrInfo) {
      this.hrInfoModel = { ...this.hrInfo };
    }
    this.hrInfoModel.competitor = comp;
  }

  public async saveChanges(): Promise<void> {
    if (!this.validate()) {
      return;
    }

    try {
      if (this.hrInfoModel.id == null) {
        this.$store.commit('setCompetitorHrInfo', await this.hrInfoService().create(this.hrInfoModel));
      } else {
        this.$store.commit('setCompetitorHrInfo', await this.hrInfoService().update(this.hrInfoModel));
      }

      if (this.headModel.id == null) {
        this.$store.commit('addCompetitorPeople', await this.peopleService().create(this.headModel));
      } else {
        this.$store.commit('addCompetitorPeople', await this.peopleService().update(this.headModel));
      }
      if (this.headModel2.id == null) {
        this.$store.commit('addCompetitorPeople', await this.peopleService().create(this.headModel2));
      } else {
        this.$store.commit('addCompetitorPeople', await this.peopleService().update(this.headModel2));
      }

      this.savingError = false;

      this.$root.$emit('bv::hide::modal', 'edit-hr-page');
      this.$root.$emit('update_flags');
    } catch (err) {
      this.savingError = err;
    }
  }

  validate(): boolean {
    if (this.hrInfoModel.glassdoorRate) {
      if (this.hrInfoModel.glassdoorRate.toString().includes(',')) {
        this.hrInfoModel.glassdoorRate = Number.parseFloat(this.hrInfoModel.glassdoorRate.toString().replace(',', '.'));
      }
      if (
        this.hrInfoModel.glassdoorRate < 0 ||
        this.hrInfoModel.glassdoorRate > 5 ||
        !/^\d*\.?\d+$/.test(this.hrInfoModel.glassdoorRate.toString())
      ) {
        this.savingError = 'Glassdoor rating should be between 0 and 5 with dot or comma delimiter.';
        return false;
      }
    }

    if (this.hrInfoModel.cooptationPremiumAmount) {
      if (this.hrInfoModel.cooptationPremiumAmount < 0) {
        this.savingError = 'The cooptation premium amount should be positive';
        return false;
      }
    }

    if (this.hrInfoModel.juniorSalary) {
      if (this.hrInfoModel.juniorSalary < 0) {
        this.savingError = 'The junior salary on hire should be positive';
        return false;
      }
    }

    if (this.hrInfoModel.averageSalary) {
      if (this.hrInfoModel.averageSalary < 0) {
        this.savingError = 'The average salary should be positive';
        return false;
      }
    }

    if (this.hrInfoModel.averageContractDuration) {
      if (this.hrInfoModel.averageContractDuration < 0) {
        this.savingError = 'The average contract duration should be positive';
        return false;
      }
    }

    if (this.hrInfoModel.viadeoRate) {
      if (this.hrInfoModel.viadeoRate.toString().includes(',')) {
        this.hrInfoModel.viadeoRate = Number.parseFloat(this.hrInfoModel.viadeoRate.toString().replace(',', '.'));
      }
      if (
        this.hrInfoModel.viadeoRate < 0 ||
        this.hrInfoModel.viadeoRate > 5 ||
        !/^\d*\.?\d+$/.test(this.hrInfoModel.viadeoRate.toString())
      ) {
        this.savingError = 'Viadeo rating should be between 0 and 5 with dot or comma delimiter.';
        return false;
      }
    }

    return true;
  }
}
