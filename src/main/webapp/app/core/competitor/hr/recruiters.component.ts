import { Inject, Mixins, Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { mapGetters } from 'vuex';
import { Competitors, ICompetitors } from '@/shared/model/competitors.model';
import AccessMixin from '@/account/AccessMixin';
import { IOffices } from '@/shared/model/offices.model';
import OfficesService from '@/entities/offices/offices.service';
import { IPeople } from '@/shared/model/people.model';
import PeopleService from '@/entities/people/people.service';
import AddRecruiterModal from '@/core/competitor/hr/add-recruiter.vue';
import Question from '@/core/components/question.vue';

@Component({
  components: {
    'add-recruiter-modal': AddRecruiterModal,
    Question
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor',
      competitorsInGroup: 'competitorsInGroup'
    })
  }
})
export default class RecruitersComponent extends Mixins(AccessMixin) {
  @Inject('officesService') private officesService: () => OfficesService;
  @Inject('peopleService') private peopleService: () => PeopleService;

  public competitor!: Competitors;
  public competitorsInGroup!: Competitors[];
  public groupCountries: String[] = [];
  public selectedCountry: String = '';
  public selectedOffice: IOffices = null;
  public show = false;
  public recruitersList: IPeople[] = null;

  private static getCountries(competitorsInGroup: Competitors[]): String[] {
    const list = [];
    for (let i = 0; i < competitorsInGroup.length; i++) {
      if (!list.includes(competitorsInGroup[i].country.value)) {
        list.push(competitorsInGroup[i].country.value);
      }
    }
    return list;
  }

  mounted() {
    this.groupCountries = RecruitersComponent.getCountries(this.competitorsInGroup);
    this.$root.$on('recruiter-added', value => {
      this.recruitersList.push(value);
      this.save();
    });
  }

  public getFlag(country: String): String {
    if (country === 'France') {
      return '/content/icons/flag-fr.png';
    }
    return '';
  }

  public back(): void {
    this.selectedOffice = null;
  }

  selectOffice(off: IOffices, com: ICompetitors): void {
    this.selectedOffice = off;
    this.selectedOffice.competitors = new Competitors();
    this.selectedOffice.competitors.id = com.id;
    this.getRecruiters();
  }
  showEdit(): void {
    this.show = true;
  }
  public showAddRecruiter(): void {
    this.$root.$emit('bv::show::modal', 'add-recruiter-page');
  }

  save(): void {
    this.selectedOffice.recruitersUpdate = new Date();
    this.officesService().update(this.selectedOffice);
    this.show = false;
  }

  async getRecruiters(): Promise<void> {
    this.recruitersList = await this.peopleService().retrieveByOfficeAndTitle(this.selectedOffice.id, 'Recruiter');
  }

  async deleteRecruiter(recruiter: IPeople): Promise<void> {
    await this.peopleService().delete(recruiter.id);
    this.recruitersList = this.recruitersList.filter(a => {
      return a.id !== recruiter.id;
    });
  }
}
