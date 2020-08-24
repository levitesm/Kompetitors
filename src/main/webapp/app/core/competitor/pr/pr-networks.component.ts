import { Inject, Mixins } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { mapGetters } from 'vuex';
import { Competitors } from '@/shared/model/competitors.model';
import RSSParser from 'rss-parser';
import AccessMixin from '@/account/AccessMixin';
import ExternalUrlsService from '@/entities/external-urls/external-urls.service';
import { ExternalUrls } from '@/shared/model/external-urls.model';
import EditPr from '@/core/competitor/pr/edit-pr.vue';

@Component({
  components: {
    'edit-pr': EditPr
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor',
      externalUrls: 'externalUrls'
    })
  }
})
export default class PrNetworksComponent extends Mixins(AccessMixin) {
  public competitor!: Competitors;
  public externalUrls!: ExternalUrls;

  @Inject('externalUrlsService') private externalUrlsService: () => ExternalUrlsService;

  public BLOG: any = null;
  public BLOG_STATUS = '';

  async mounted() {
    await this.fetchExternalUrls();
    await this.getBlogFeed();
  }

  public async getBlogFeed(): Promise<void> {
    if (this.externalUrls.blogFeed == null) {
      this.BLOG_STATUS = 'NOTSET';
      return;
    }
    if (this.externalUrls.blogFeed === '') {
      this.BLOG_STATUS = 'NOTSET';
      return;
    }

    const url = this.externalUrls.blogFeed;

    const response = await fetch('https://cors-anywhere.herokuapp.com/' + url);
    if (response.ok) {
      const text = await response.text();

      const parser = new RSSParser();
      parser.parseString(text, (err, parsed) => {
        if (err) {
          console.log(`>>>>>>>Error occured while parsing RSS Feed ${err.toString()}`);
          this.BLOG_STATUS = 'ERROR';
        } else {
          this.BLOG = parsed;
          this.BLOG_STATUS = 'OK';
        }
      });
    } else {
      console.log(`>>>>>>>Error occured while parsing RSS Feed Response STATUS - ${response.statusText}`);
      this.BLOG_STATUS = 'ERROR';
    }
  }

  async fetchExternalUrls() {
    const id = Number(this.$route.params.id);
    try {
      this.$store.commit('setExternalUrls', await this.externalUrlsService().retrieveByCompetitorId(id));
    } catch (err) {
      console.log('Urls not found:', err);
      this.$store.commit('setExternalUrls', ExternalUrls.byCompetitorId(id));
    }
  }

  public formatUrl(url: String): String {
    return url.toLowerCase().includes('http') ? url : '//' + url;
  }

  public showEditPr(): void {
    this.$root.$emit('bv::show::modal', 'edit-pr-modal');
  }
}
