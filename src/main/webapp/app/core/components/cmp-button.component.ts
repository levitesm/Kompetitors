import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class CmpButton extends Vue {
  @Prop({ default: 'contained' }) type: string; // contained, outlined
  @Prop() text: string;
  @Prop() onClick: Function;
  @Prop({ default: false }) disabled: boolean;
}
