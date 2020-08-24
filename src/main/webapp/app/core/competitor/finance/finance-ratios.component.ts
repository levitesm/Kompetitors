import { Component, Inject, Mixins } from 'vue-property-decorator';
import { IAnnualAccount } from '@/shared/model/annual-account.model';
import AnnualAccountService from '@/entities/annual-account/annual-account.service';
import { mapGetters } from 'vuex';
import FinanceFormulaMixin from '@/shared/mixins/finance-formula-mixin';
import FinanceMonthlyRatiosComponent from '@/core/competitor/finance/finance-monthly-ratios.vue';

@Component({
  computed: {
    ...mapGetters({
      annualAccounts: 'annualAccounts'
    })
  },
  props: {
    siren: String
  },
  components: {
    FinanceMonthlyRatiosComponent
  }
})
export default class FinanceRatios extends Mixins(FinanceFormulaMixin) {
  @Inject('annualAccountService') private annualAccountService: () => AnnualAccountService;
  public annualAccounts!: IAnnualAccount[];
  public prevYear = new Date().getFullYear() - 1;

  mounted() {
    if (this.$props.siren === '' || (this.annualAccounts.length > 0 && this.annualAccounts[0].siren === this.$props.siren)) {
      return;
    }

    this.annualAccountService()
      .getAllBySiren(this.$props.siren)
      .then(response => {
        this.$store.commit('setAnnualAccounts', response);
      });
  }
}
