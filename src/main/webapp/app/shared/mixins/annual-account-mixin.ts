import Vue from 'vue';
import Component from 'vue-class-component';
import { IAnnualAccount } from '@/shared/model/annual-account.model';
import { mapGetters } from 'vuex';

@Component({
  computed: {
    ...mapGetters({
      annualAccounts: 'annualAccounts'
    })
  }
})
export default class AnnualAccountMixin extends Vue {
  public annualAccounts!: IAnnualAccount[];

  valueByCodeAndYear(code: String, year: number): number {
    const account: IAnnualAccount = this.annualAccounts.find(acc => acc.code === code && acc.year === year);
    return account ? account.value : undefined;
  }
}
