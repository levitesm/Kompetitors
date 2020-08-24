import { Component, Prop, Vue } from 'vue-property-decorator';
import CmpButton from '@/core/components/cmp-button.vue';

@Component({
  components: {
    CmpButton
  }
})
export default class ConfirmModal extends Vue {
  @Prop() public onConfirm: Function;
  @Prop() public message: string;
  public savingError = null;

  public async confirm(): Promise<void> {
    try {
      await this.onConfirm();
      this.closeModal();
    } catch (err) {
      console.log(err);
      this.savingError = err;
    }
  }

  public closeModal(): void {
    this.savingError = null;
    this.$root.$emit('bv::hide::modal', 'confirm-modal');
  }
}
