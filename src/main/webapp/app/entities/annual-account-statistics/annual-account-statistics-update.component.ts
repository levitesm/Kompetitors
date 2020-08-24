import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { IAnnualAccountStatistics, AnnualAccountStatistics } from '@/shared/model/annual-account-statistics.model';
import AnnualAccountStatisticsService from './annual-account-statistics.service';

const validations: any = {
  annualAccountStatistics: {
    siren: {
      required
    },
    year: {
      required,
      numeric
    },
    code: {},
    message: {},
    modified: {}
  }
};

@Component({
  validations
})
export default class AnnualAccountStatisticsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('annualAccountStatisticsService') private annualAccountStatisticsService: () => AnnualAccountStatisticsService;
  public annualAccountStatistics: IAnnualAccountStatistics = new AnnualAccountStatistics();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.annualAccountStatisticsId) {
        vm.retrieveAnnualAccountStatistics(to.params.annualAccountStatisticsId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.annualAccountStatistics.id) {
      this.annualAccountStatisticsService()
        .update(this.annualAccountStatistics)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.annualAccountStatistics.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.annualAccountStatisticsService()
        .create(this.annualAccountStatistics)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.annualAccountStatistics.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.annualAccountStatistics[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.annualAccountStatistics[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.annualAccountStatistics[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.annualAccountStatistics[field] = null;
    }
  }

  public retrieveAnnualAccountStatistics(annualAccountStatisticsId): void {
    this.annualAccountStatisticsService()
      .find(annualAccountStatisticsId)
      .then(res => {
        res.modified = new Date(res.modified);
        this.annualAccountStatistics = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
