import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import RepresentativesService from '@/entities/representatives/representatives.service';
import { Representatives } from '@/shared/model/representatives.model';

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'edit-reps-page');
    }
  }
})
export default class EditReps extends Vue {
  @Inject('representativesService') private representativesService: () => RepresentativesService;

  public representatives: Representatives[] = [];
  public savingError = null;

  async mounted() {
    await this.refill();
  }

  public async refill(): Promise<void> {
    this.representatives = await this.representativesService().retrieveBySiren(localStorage.getItem('siren'));
  }

  public async saveChanges(): Promise<void> {
    try {
      for (let i = 0; i < this.representatives.length; i++) {
        const r = this.representatives[i];
        if (!r.old && r.type === 'PP') {
          await this.representativesService().update(r);
        }
      }

      this.savingError = false;

      this.$root.$emit('change_in_reps');
      this.$root.$emit('bv::hide::modal', 'edit-reps-page');
    } catch (err) {
      this.savingError = err;
    }
  }
}
