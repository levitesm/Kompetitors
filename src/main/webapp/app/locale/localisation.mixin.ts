import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { mapGetters } from 'vuex';

@Component({
  computed: {
    ...mapGetters({
      currentLanguage: 'currentLanguage'
    })
  }
})
export default class LocalisationMixin extends Vue {
  public currentLanguage: string;

  public localisationFromName(name: string): string {
    let result = name;
    const index = name.indexOf('@');
    if (index >= 0) {
      result = this.currentLanguage === 'fr' ? name.substring(index + 1) : name.substring(0, index);
    }
    return result;
  }
}
