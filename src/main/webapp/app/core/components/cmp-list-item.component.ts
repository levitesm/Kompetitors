import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class CmpListItem extends Vue {
  @Prop() name: string;
  @Prop() value: string;
  @Prop() unit: string;
}
