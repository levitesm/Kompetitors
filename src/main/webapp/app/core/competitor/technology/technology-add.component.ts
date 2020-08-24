import { Inject, Prop, Watch } from 'vue-property-decorator';
import Component, { mixins } from 'vue-class-component';
import ListCompetanciesService from '@/entities/list-competancies/list-competancies.service';
import ListToolsService from '@/entities/list-tools/list-tools.service';
import ListServicesService from '@/entities/list-services/list-services.service';
import ListTechPartnersService from '@/entities/list-tech-partners/list-tech-partners.service';
import ListProjectTypesService from '@/entities/list-project-types/list-project-types.service';
import JhiDataUtils from '@/shared/data/data-utils.service';
import ListPracticesService from '@/entities/list-practices/list-practices.service';

class TechModel {
  constructor(public id?: number, public value?: string, public image?: string, public imageContentType?: string) {}
}

@Component
export default class TechnologyAddModal extends mixins(JhiDataUtils) {
  @Inject('listCompetanciesService') public listCompetanciesService: () => ListCompetanciesService;
  @Inject('listPracticesService') public listPracticesService: () => ListPracticesService;
  @Inject('listToolsService') public listToolsService: () => ListToolsService;
  @Inject('listServicesService') public listServicesService: () => ListServicesService;
  @Inject('listTechPartnersService') public listTechPartnersService: () => ListTechPartnersService;
  @Inject('listProjectTypesService') public listProjectTypesService: () => ListProjectTypesService;

  @Prop() public entity = '';
  public model: TechModel = new TechModel();
  public savingError = null;

  @Watch('entity')
  resetModel() {
    this.model = new TechModel();
  }

  async save() {
    if (!this.validate()) {
      return;
    }
    if (this.getService() !== null) {
      try {
        const response = await this.getService().create(this.model);
        this.$store.commit('setDictionary', { section: this.entity, data: response, add: true });
        this.resetModel();
        this.$root.$emit('bv::hide::modal', 'technology-add-modal');
      } catch (err) {
        this.savingError = err;
      }
    }
  }

  public selectFile() {
    (<any>this.$refs.fileLogo).click();
  }

  public getLogo(): string {
    if (this.model.image != null) {
      return 'data:' + this.model.imageContentType + '";base64,' + this.model.image;
    }
    return '../../content/images/logo2.svg';
  }

  clearLogo() {
    this.model.image = null;
    this.model.imageContentType = null;
  }

  get title(): string {
    return this.$t('technology-tab.information.add-' + this.entity) as string;
  }

  getService():
    | ListCompetanciesService
    | ListPracticesService
    | ListToolsService
    | ListServicesService
    | ListTechPartnersService
    | ListProjectTypesService {
    switch (this.entity) {
      case 'techCompetancies': {
        return this.listCompetanciesService();
      }
      case 'techPractices': {
        return this.listPracticesService();
      }
      case 'techTools': {
        return this.listToolsService();
      }
      case 'techServices': {
        return this.listServicesService();
      }
      case 'techPartners': {
        return this.listTechPartnersService();
      }
      case 'techProjects': {
        return this.listProjectTypesService();
      }
      default: {
        return null;
      }
    }
  }

  validate(): boolean {
    if (this.model.value.length < 3) {
      this.savingError = 'Name length should be 3 or more!';
      return false;
    }
    this.savingError = null;
    return true;
  }
}
