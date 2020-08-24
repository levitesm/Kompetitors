import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitorsService from '../competitors/competitors.service';
import { ICompetitors } from '@/shared/model/competitors.model';

import AlertService from '@/shared/alert/alert.service';
import { IExternalUrls, ExternalUrls } from '@/shared/model/external-urls.model';
import ExternalUrlsService from './external-urls.service';

const validations: any = {
  externalUrls: {
    facebookUrl: {},
    twitterUrl: {},
    instagramUrl: {},
    youtubeUrl: {},
    linkedinUrl: {},
    githubUrl: {},
    blogFeed: {},
    googleAlertsFeed: {}
  }
};

@Component({
  validations
})
export default class ExternalUrlsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('externalUrlsService') private externalUrlsService: () => ExternalUrlsService;
  public externalUrls: IExternalUrls = new ExternalUrls();

  @Inject('competitorsService') private competitorsService: () => CompetitorsService;

  public competitors: ICompetitors[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.externalUrlsId) {
        vm.retrieveExternalUrls(to.params.externalUrlsId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.externalUrls.id) {
      this.externalUrlsService()
        .update(this.externalUrls)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.externalUrls.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.externalUrlsService()
        .create(this.externalUrls)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('kompetitors2App.externalUrls.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveExternalUrls(externalUrlsId): void {
    this.externalUrlsService()
      .find(externalUrlsId)
      .then(res => {
        this.externalUrls = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitorsService()
      .retrieve()
      .then(res => {
        this.competitors = res.data;
      });
  }
}
