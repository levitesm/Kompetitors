import Component from 'vue-class-component';
import { Vue, Inject, Prop } from 'vue-property-decorator';
import { Competitors } from '@/shared/model/competitors.model';
import { Offices } from '@/shared/model/offices.model';
import { Clients } from '@/shared/model/clients.model';
import ClientsService from '@/entities/clients/clients.service';
import ListIndustriesService from '@/entities/list-industries/list-industries.service';
import ListClientsProjectTypesService from '@/entities/list-clients-project-types/list-clients-project-types.service';
import { ListIndustries } from '@/shared/model/list-industries.model';
import { ListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';
import { ClientsProjects } from '@/shared/model/clients-projects.model';
import ClientsProjectsService from '@/entities/clients-projects/clients-projects.service';
import { mapGetters } from 'vuex';

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'add-client');
    }
  },
  computed: {
    ...mapGetters({
      competitor: 'competitor'
    })
  }
})
export default class AddClient extends Vue {
  public competitor!: Competitors;

  @Inject('clientsService')
  private clientsService: () => ClientsService;
  @Inject('listIndustriesService')
  private listIndustriesService: () => ListIndustriesService;
  @Inject('listClientsProjectTypesService')
  private listClientsProjectTypesService: () => ListClientsProjectTypesService;
  @Inject('clientsProjectsService')
  private clientsProjectsService: () => ClientsProjectsService;

  @Prop()
  public clientId: number;

  public savingError = null;

  public newClient = new Clients();
  public proj = new ClientsProjects();

  public officeList = [];
  public selectedOffice = new Offices();
  public indList = [];
  public selectedInd = new ListIndustries();
  public projTypeList = [];
  public selectedProjType = new ListClientsProjectTypes();

  async mounted() {
    this.officeList = this.competitor.offices;

    this.indList = (await this.listIndustriesService().retrieve()).data;

    this.projTypeList = (await this.listClientsProjectTypesService().retrieve()).data;

    if (!this.clientId) {
      this.selectedOffice = this.officeList[0];
      this.selectedInd = this.indList.length > 0 ? this.indList[0] : null;
      this.selectedProjType = this.projTypeList.length > 0 ? this.projTypeList[0] : null;
    } else {
      this.newClient = await this.clientsService().find(this.clientId);
      for (let i = 0; i < this.officeList.length; i++) {
        if (this.officeList[i].id === this.newClient.offices.id) {
          this.selectedOffice = this.officeList[i];
          break;
        }
      }
      this.selectedInd = this.newClient.industry;
      this.proj = this.newClient.projects[0];
      this.selectedProjType = this.proj.projectType;
    }

    this.newClient.updateDate = new Date();
  }

  public async saveChanges(): Promise<void> {
    function clean(value: string) {
      if (value != null) {
        value = value.trim();
      }
      if (value === '') {
        value = null;
      }
      return value;
    }

    this.savingError = null;

    this.newClient.name = clean(this.newClient.name);
    if (!this.newClient.name) {
      this.savingError = "Please enter Client's name";
      return;
    }

    try {
      this.newClient.offices = this.selectedOffice;
      this.newClient.industry = this.selectedInd;
      this.proj.projectType = this.selectedProjType;
      if (!this.newClient.id) {
        const client = await this.clientsService().create(this.newClient);
        this.proj.clients = client;
        await this.clientsProjectsService().create(this.proj);
      } else {
        await this.clientsService().update(this.newClient);
        await this.clientsProjectsService().update(this.proj);
      }

      this.savingError = false;

      this.$root.$emit('change_in_clients');
      this.$root.$emit('bv::hide::modal', 'add-client');
      this.$root.$emit('update_flags');
    } catch (err) {
      this.savingError = err;
    }
  }

  getIndustry(ind: ListIndustries): string {
    let str = '';
    if (ind.value.indexOf('@') > 0) {
      if (this.$store.getters.currentLanguage === 'fr') {
        str = ind.value.substring(ind.value.indexOf('@') + 1);
      } else {
        str = ind.value.substring(0, ind.value.indexOf('@'));
      }
    } else {
      str = ind.value;
    }
    return str;
  }
}
