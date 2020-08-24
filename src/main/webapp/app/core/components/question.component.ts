import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class Question extends Vue {
  alertq() {
    alert(this.$t('app.question'));
  }
}
