import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import { TechCompetancies } from '@/shared/model/tech-competancies.model';
import { TechTools } from '@/shared/model/tech-tools.model';
import { TechServices } from '@/shared/model/tech-services.model';
import { TechPartners } from '@/shared/model/tech-partners.model';
import { TechProjects } from '@/shared/model/tech-projects.model';
import { ListCompetancies } from '@/shared/model/list-competancies.model';
import { ListTools } from '@/shared/model/list-tools.model';
import { ListServices } from '@/shared/model/list-services.model';
import { ListTechPartners } from '@/shared/model/list-tech-partners.model';
import { ListProjectTypes } from '@/shared/model/list-project-types.model';
import { TechPractices } from '@/shared/model/tech-practices.model';
import { ListPractices } from '@/shared/model/list-practices.model';
import Question from '@/core/components/question.vue';

@Component({
  name: 'technology-edit',
  components: {
    Question
  }
})
export default class TechnologyEdit extends Vue {
  @Prop() public dictionary: ListCompetancies[] | ListPractices[] | ListTools[] | ListServices[] | ListTechPartners[] | ListProjectTypes[];
  @Prop() public values: TechCompetancies[] | TechPractices[] | TechTools[] | TechServices[] | TechPartners[] | TechProjects[];
  @Prop() public pageId: string;

  public selected: any[] = [];
  public changes = {};

  @Watch('dictionary')
  watchDictionary() {
    this.checkSelected();
  }

  @Watch('values')
  watchValues() {
    this.checkSelected();
  }

  checkSelected() {
    this.selected = this.values.map(item => {
      const model = { id: item.valueId, value: this.dictionary[item.valueId] ? this.dictionary[item.valueId].value : '' };
      if (this.pageId === 'edit-tech-section-tech-partners') {
        Object.assign(model, {
          image: this.dictionary[item.valueId] ? this.dictionary[item.valueId]['image'] : '',
          imageContentType: this.dictionary[item.valueId] ? this.dictionary[item.valueId]['imageContentType'] : ''
        });
      }
      return model;
    });
  }

  change(item: any, event: any) {
    if (item.id in this.changes) {
      delete this.changes[item.id];
    } else {
      this.changes[item.id] = !!event;
    }
  }

  save() {
    this.$root.$emit('bv::hide::modal', this.pageId);
    this.$emit('save', { ...this.changes });
    this.changes = {};
    this.$root.$emit('update_flags');
  }
}
