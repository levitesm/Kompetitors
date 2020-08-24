import { Component, Mixins } from 'vue-property-decorator';
import { mapGetters } from 'vuex';
import RSSParser from 'rss-parser';
import GoogleAlertsEditModal from '@/core/competitor/pr/google-alerts-edit.vue';
import CustomSpinner from '../../../../components/spinner/spinner.vue';
import AccessMixin from '@/account/AccessMixin';
import { ExternalUrls } from '@/shared/model/external-urls.model';

@Component({
  computed: {
    ...mapGetters({
      externalUrls: 'externalUrls'
    })
  },
  components: {
    'google-alerts-edit-modal': GoogleAlertsEditModal,
    'custom-spinner': CustomSpinner
  }
})
export default class GoogleAlertsComponent extends Mixins(AccessMixin) {
  public externalUrls!: ExternalUrls;

  public items: any[] = [];
  public title = '';
  public buildDate: string = null;

  public error = null;
  public loading = false;

  async mounted() {
    await this.parseFeed();
  }

  async parseFeed() {
    if (!this.externalUrls || !this.externalUrls.googleAlertsFeed) {
      return;
    }
    this.loading = true;
    try {
      const response = await fetch('https://cors-anywhere.herokuapp.com/' + this.externalUrls.googleAlertsFeed);
      if (response.ok) {
        const text = await response.text();
        const parser = new RSSParser();
        await parser.parseString(text, (err, parsed) => {
          if (err) {
            console.error(`Error occurred while parsing RSS Feed: ${err.toString()}`);
          } else {
            this.items = parsed.items;
            this.title = parsed.title;
            this.buildDate = parsed.lastBuildDate;
          }
        });
      } else {
        this.items = [];
        this.title = null;
        this.buildDate = null;
        console.error(`Failed to read Google Alerts feed url: ${response.statusText}`);
      }
    } catch (err) {
      console.error(err);
    } finally {
      this.loading = false;
    }
  }

  public getDate(value: string): string {
    if (value) {
      const date = new Date(value);
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}  ${date.toLocaleTimeString()}`;
    }
    return '';
  }

  showAddFeed() {
    this.$root.$emit('bv::show::modal', 'google-alerts-edit-modal');
  }
}
