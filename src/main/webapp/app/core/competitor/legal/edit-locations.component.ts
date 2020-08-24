import { Inject } from 'vue-property-decorator';
import Component, { mixins } from 'vue-class-component';
import { mapGetters } from 'vuex';
import AccessMixin from '@/account/AccessMixin';
import { Offices } from '@/shared/model/offices.model';
import OfficesServices from '@/entities/offices/offices.service';

@Component({
  computed: {
    ...mapGetters({
      competitorsInGroup: 'competitorsInGroup'
    })
  }
})
export default class EditLocations extends mixins(AccessMixin) {
  @Inject('officesService')
  private officesService: () => OfficesServices;
  private toBeDeleted = [];

  save() {
    this.confirmDelete();
    this.$root.$emit('bv::hide::modal', 'edit-locations');
  }

  addOfficeToBeDeleted(office: Offices) {
    if (this.hasAccess('DELETE_OFFICE') && !office.mainOffice && !this.hasClients(office)) {
      this.toBeDeleted.push(office);
    }
  }

  removeOfficeToBeDeleted(office: Offices) {
    this.toBeDeleted = this.toBeDeleted.filter(o => o.id !== office.id);
  }

  confirmDelete() {
    this.toBeDeleted.forEach(office => this.deleteOffice(office));
  }

  isToBeDeleted(office: Offices) {
    return this.toBeDeleted.filter(o => o.id === office.id).length > 0;
  }

  displayDeleteIconFor(office: Offices) {
    return this.isDeletable(office) && !this.isToBeDeleted(office);
  }

  private isDeletable(office: Offices) {
    return this.hasAccess('DELETE_OFFICE') && !office.mainOffice;
  }

  hasClients(office: Offices) {
    return office.clients.length > 0;
  }

  async deleteOffice(office: Offices): Promise<boolean> {
    if (this.isDeletable(office) && !this.hasClients(office)) {
      const response = await this.officesService().delete(office.id);
      if (response.status === 204) {
        this.$store.commit('deleteCompetitorOffice', office);
        this.$root.$emit('agency_deleted', office);
        return true;
      } else {
        return false;
      }
    }
    return false;
  }
}
