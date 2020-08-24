import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class AccessMixin extends Vue {
  public LEGAL_VIEW = 'LEGAL_VIEW'; // Competitor screen Legal tab view access key
  public FINANCE_VIEW = 'FINANCE_VIEW'; // Competitor screen Finance tab view access key
  public CLIENT_VIEW = 'CLIENT_VIEW'; // Competitor screen Client tab view access key
  public TECH_VIEW = 'TECH_VIEW'; // Competitor screen Technical tab view access key
  public HR_VIEW = 'HR_VIEW'; // Competitor screen HR tab view access key
  public PR_VIEW = 'PR_VIEW'; // Competitor screen PR & Social tab view access key
  public DELETE_OFFICE = 'DELETE_OFFICE'; // Access key to delete agency button in competitor-legal-locations view
  public LEGAL_EDIT = 'LEGAL_EDIT'; // Competitor screen Legal tab edit access key
  public FINANCE_EDIT = 'FINANCE_EDIT'; // Competitor screen Finance tab edit access key
  public CLIENT_EDIT = 'CLIENT_EDIT'; // Competitor screen Client tab edit access key
  public TECH_EDIT = 'TECH_EDIT'; // Competitor screen Technical tab edit access key
  public HR_EDIT = 'HR_EDIT'; // Competitor screen PR & Social tab edit access key
  public PR_EDIT = 'PR_EDIT'; // Competitor screen HR tab view edit key
  public DELETE_COMPANY = 'DELETE_COMPANY'; // Competitor main screen delete company

  public hasAccess(accessKey: String): boolean {
    return this.$store.getters.accessKeys.includes(accessKey);
  }
}
