package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

/**
 * A Competitors.
 */
@Entity
@Table(name = "competitors")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Competitors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "country_phone")
    private String countryPhone;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @ManyToOne
    @JsonIgnoreProperties("competitors")
    private GlobalGroups globalGroups;

    @OneToMany(mappedBy = "competitors", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitors")
    private Set<Offices> offices = new HashSet<>();

    @OneToMany(mappedBy = "competitors", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitors")
    private Set<Finance> finances = new HashSet<>();

    @OneToMany(mappedBy = "competitors", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties("competitors")
    private Set<PrInfo> prinfo = new HashSet<>();

    @OneToMany(mappedBy = "competitors", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties("competitors")
    private Set<People> people = new HashSet<>();

    @OneToMany(mappedBy = "competitors", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitors")
    private Set<Dialogs> dialogs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("competitors")
    private ListCountries country;

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitor")
    private Set<Legal> legal = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitor")
    private Set<CompetitiveRates> competitiveRates = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitor")
    private Set<HrInfo> hr;

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties("competitor")
    private Set<SocieteMain> societeMain;

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitor")
    private Set<Infogreffe> infogreffe;

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties(value={"competitor"})
    private Set<TechCompetancies> techCompetancies = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties(value={"competitor"})
    private Set<TechPractices> techPractices = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties(value={"competitor"})
    private Set<TechTools> techTools = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties(value={"competitor"})
    private Set<TechServices> techServices = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties(value={"competitor"})
    private Set<TechPartners> techPartners = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnoreProperties(value={"competitor"})
    private Set<TechProjects> techProjects = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<EmployeePricing> employeePricings = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<DashboardFinance> dashboardFinances = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<CompetitorIndustry> competitorIndustries = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<TechInfo> techInfos = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<EmployeesTypology> employeeTechnologies = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Workforce> workforces = new HashSet<>();

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("competitor")
    private Set<EmployeeSalaries> salaries = new HashSet<>();

    public Competitors() {
    }

    public Competitors(Long id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Set<EmployeeSalaries> getSalaries() {
        return salaries;
    }

    public void setSalaries(Set<EmployeeSalaries> salaries) {
        this.salaries = salaries;
    }

    public String getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(String countryPhone) {
        this.countryPhone = countryPhone;
    }
    public Competitors countryPhone(String countryPhone) {
        this.countryPhone = countryPhone;
        return this;
    }

    public Set<SocieteMain> getSocieteMain() {
        return societeMain;
    }

    public void setSocieteMain(Set<SocieteMain> societeMain) {
        this.societeMain = societeMain;
    }

    public Set<Infogreffe> getInfogreffe() {
        return infogreffe;
    }

    public void setInfogreffe(Set<Infogreffe> infogreffe) {
        this.infogreffe = infogreffe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Competitors name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public Competitors webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Competitors logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Competitors logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public GlobalGroups getGlobalGroups() {
        return globalGroups;
    }

    public Competitors globalGroups(GlobalGroups globalGroups) {
        this.globalGroups = globalGroups;
        return this;
    }

    public void setGlobalGroups(GlobalGroups globalGroups) {
        this.globalGroups = globalGroups;
    }

    public Set<Offices> getOffices() {
        return offices;
    }

    public Competitors offices(Set<Offices> offices) {
        this.offices = offices;
        return this;
    }

    public Competitors addOffices(Offices offices) {
        this.offices.add(offices);
        offices.setCompetitors(this);
        return this;
    }

    public Competitors removeOffices(Offices offices) {
        this.offices.remove(offices);
        offices.setCompetitors(null);
        return this;
    }

    public void setOffices(Set<Offices> offices) {
        this.offices = offices;
    }

    public Set<Finance> getFinances() {
        return finances;
    }

    public Competitors finances(Set<Finance> finances) {
        this.finances = finances;
        return this;
    }

    public Competitors addFinance(Finance finance) {
        this.finances.add(finance);
        finance.setCompetitors(this);
        return this;
    }

    public Competitors removeFinance(Finance finance) {
        this.finances.remove(finance);
        finance.setCompetitors(null);
        return this;
    }

    public void setFinances(Set<Finance> finances) {
        this.finances = finances;
    }

    public Set<PrInfo> getPrinfo() {
        return prinfo;
    }

    public Competitors prinfo(Set<PrInfo> prInfos) {
        this.prinfo = prInfos;
        return this;
    }

    public Competitors addPr(PrInfo prInfo) {
        this.prinfo.add(prInfo);
        prInfo.setCompetitors(this);
        return this;
    }

    public Competitors removePr(PrInfo prInfo) {
        this.prinfo.remove(prInfo);
        prInfo.setCompetitors(null);
        return this;
    }

    public void setPrinfo(Set<PrInfo> prInfos) {
        this.prinfo = prInfos;
    }

    public Set<People> getPeople() {
        return people;
    }

    public Competitors people(Set<People> people) {
        this.people = people;
        return this;
    }

    public Competitors addPeople(People people) {
        this.people.add(people);
        people.setCompetitors(this);
        return this;
    }

    public Competitors removePeople(People people) {
        this.people.remove(people);
        people.setCompetitors(null);
        return this;
    }

    public void setPeople(Set<People> people) {
        this.people = people;
    }

    public Set<Dialogs> getDialogs() {
        return dialogs;
    }

    public Competitors dialogs(Set<Dialogs> dialogs) {
        this.dialogs = dialogs;
        return this;
    }

    public Competitors addDialogs(Dialogs dialogs) {
        this.dialogs.add(dialogs);
        dialogs.setCompetitors(this);
        return this;
    }

    public Competitors removeDialogs(Dialogs dialogs) {
        this.dialogs.remove(dialogs);
        dialogs.setCompetitors(null);
        return this;
    }

    public void setDialogs(Set<Dialogs> dialogs) {
        this.dialogs = dialogs;
    }

    public ListCountries getCountry() {
        return country;
    }

    public Competitors country(ListCountries listCountries) {
        this.country = listCountries;
        return this;
    }

    public void setCountry(ListCountries listCountries) {
        this.country = listCountries;
    }

    public Set<CompetitiveRates> getCompetitiveRates() {
        return competitiveRates;
    }

    public void setCompetitiveRates(Set<CompetitiveRates> competitiveRates) {
        this.competitiveRates = competitiveRates;
    }

    public Competitors addCompetitiveRates(CompetitiveRates rates) {
        this.competitiveRates.add(rates);
        rates.setCompetitor(this);
        return this;
    }

    public Set<Legal> getLegal() {
        return legal;
    }

    public void setLegal(Set<Legal> legal) {
        this.legal = legal;
    }

    public Competitors addLegal(Legal legal) {
        this.legal.add(legal);
        legal.setCompetitor(this);
        return this;
    }
    public Set<HrInfo> getHr() {
        return hr;
    }

    public void setHr(Set<HrInfo> hr) {
        this.hr = hr;
    }


    public Set<TechCompetancies> getTechCompetancies() {
        return techCompetancies;
    }

    public Competitors techCompetancies(Set<TechCompetancies> techCompetancies) {
        this.techCompetancies = techCompetancies;
        return this;
    }

    public Competitors addTechCompetancies(TechCompetancies techCompetancies) {
        this.techCompetancies.add(techCompetancies);
        techCompetancies.setCompetitor(this);
        return this;
    }

    public Competitors removeTechCompetancies(TechCompetancies techCompetancies) {
        this.techCompetancies.remove(techCompetancies);
        techCompetancies.setCompetitor(null);
        return this;
    }

    public void setTechCompetancies(Set<TechCompetancies> techCompetancies) {
        this.techCompetancies = techCompetancies;
    }


    public Set<TechPractices> getTechPractices() {
        return techPractices;
    }

    public Competitors techPractices(Set<TechPractices> techPractices) {
        this.techPractices = techPractices;
        return this;
    }

    public Competitors addTechPractices(TechPractices techPractices) {
        this.techPractices.add(techPractices);
        techPractices.setCompetitor(this);
        return this;
    }

    public Competitors removeTechPractices(TechPractices techPractices) {
        this.techPractices.remove(techPractices);
        techPractices.setCompetitor(null);
        return this;
    }

    public void setTechPractices(Set<TechPractices> techPractices) {
        this.techPractices = techPractices;
    }

    public Set<TechTools> getTechTools() {
        return techTools;
    }

    public Competitors techTools(Set<TechTools> techTools) {
        this.techTools = techTools;
        return this;
    }

    public Competitors addTechTools(TechTools techTools) {
        this.techTools.add(techTools);
        techTools.setCompetitor(this);
        return this;
    }

    public Competitors removeTechTools(TechTools techTools) {
        this.techTools.remove(techTools);
        techTools.setCompetitor(null);
        return this;
    }

    public void setTechTools(Set<TechTools> techTools) {
        this.techTools = techTools;
    }

    public Set<TechServices> getTechServices() {
        return techServices;
    }

    public Competitors techServices(Set<TechServices> techServices) {
        this.techServices = techServices;
        return this;
    }

    public Competitors addTechServices(TechServices techServices) {
        this.techServices.add(techServices);
        techServices.setCompetitor(this);
        return this;
    }

    public Competitors removeTechServices(TechServices techServices) {
        this.techServices.remove(techServices);
        techServices.setCompetitor(null);
        return this;
    }

    public void setTechServices(Set<TechServices> techServices) {
        this.techServices = techServices;
    }

    public Set<TechPartners> getTechPartners() {
        return techPartners;
    }

    public Competitors techPartners(Set<TechPartners> techPartners) {
        this.techPartners = techPartners;
        return this;
    }

    public Competitors addTechPartners(TechPartners techPartners) {
        this.techPartners.add(techPartners);
        techPartners.setCompetitor(this);
        return this;
    }

    public Competitors removeTechPartners(TechPartners techPartners) {
        this.techPartners.remove(techPartners);
        techPartners.setCompetitor(null);
        return this;
    }

    public void setTechPartners(Set<TechPartners> techPartners) {
        this.techPartners = techPartners;
    }

    public Set<TechProjects> getTechProjects() {
        return techProjects;
    }

    public Competitors techProjects(Set<TechProjects> techProjects) {
        this.techProjects = techProjects;
        return this;
    }

    public Competitors addTechProjects(TechProjects techProjects) {
        this.techProjects.add(techProjects);
        techProjects.setCompetitor(this);
        return this;
    }

    public Competitors removeTechProjects(TechProjects techProjects) {
        this.techProjects.remove(techProjects);
        techProjects.setCompetitor(null);
        return this;
    }

    public void setTechProjects(Set<TechProjects> techProjects) {
        this.techProjects = techProjects;
    }

    public Set<EmployeePricing> getEmployeePricings() {
        return employeePricings;
    }

    public void setEmployeePricings(Set<EmployeePricing> employeePricings) {
        this.employeePricings = employeePricings;
    }

    public Set<DashboardFinance> getDashboardFinances() {
        return dashboardFinances;
    }

    public void setDashboardFinances(Set<DashboardFinance> dashboardFinances) {
        this.dashboardFinances = dashboardFinances;
    }

    public Set<CompetitorIndustry> getCompetitorIndustries() {
        return competitorIndustries;
    }

    public void setCompetitorIndustries(Set<CompetitorIndustry> competitorIndustries) {
        this.competitorIndustries = competitorIndustries;
    }

    public Set<TechInfo> getTechInfos() {
        return techInfos;
    }

    public void setTechInfos(Set<TechInfo> techInfos) {
        this.techInfos = techInfos;
    }

    public Set<EmployeesTypology> getEmployeeTechnologies() {
        return employeeTechnologies;
    }

    public void setEmployeeTechnologies(Set<EmployeesTypology> employeeTechnologies) {
        this.employeeTechnologies = employeeTechnologies;
    }

    public Set<Workforce> getWorkforces() {
        return workforces;
    }

    public void setWorkforces(Set<Workforce> workforces) {
        this.workforces = workforces;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competitors)) return false;

        Competitors that = (Competitors) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getWebSite() != null ? !getWebSite().equals(that.getWebSite()) : that.getWebSite() != null) return false;
        if (getCountryPhone() != null ? !getCountryPhone().equals(that.getCountryPhone()) : that.getCountryPhone() != null)
            return false;
        if (!Arrays.equals(getLogo(), that.getLogo())) return false;
        if (getLogoContentType() != null ? !getLogoContentType().equals(that.getLogoContentType()) : that.getLogoContentType() != null)
            return false;
        if (getGlobalGroups() != null ? !getGlobalGroups().equals(that.getGlobalGroups()) : that.getGlobalGroups() != null)
            return false;
        if (getOffices() != null ? !getOffices().equals(that.getOffices()) : that.getOffices() != null) return false;
        if (getFinances() != null ? !getFinances().equals(that.getFinances()) : that.getFinances() != null) return false;
        if (getDialogs() != null ? !getDialogs().equals(that.getDialogs()) : that.getDialogs() != null) return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getLegal() != null ? !getLegal().equals(that.getLegal()) : that.getLegal() != null) return false;
        if (getCompetitiveRates() != null ? !getCompetitiveRates().equals(that.getCompetitiveRates()) : that.getCompetitiveRates() != null)
            return false;
        if (getHr() != null ? !getHr().equals(that.getHr()) : that.getHr() != null) return false;
        return getInfogreffe() != null ? getInfogreffe().equals(that.getInfogreffe()) : that.getInfogreffe() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getWebSite() != null ? getWebSite().hashCode() : 0);
        result = 31 * result + (getCountryPhone() != null ? getCountryPhone().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getLogo());
        result = 31 * result + (getLogoContentType() != null ? getLogoContentType().hashCode() : 0);
        result = 31 * result + (getGlobalGroups() != null ? getGlobalGroups().hashCode() : 0);
        result = 31 * result + (getOffices() != null ? getOffices().hashCode() : 0);
        result = 31 * result + (getFinances() != null ? getFinances().hashCode() : 0);
        result = 31 * result + (getDialogs() != null ? getDialogs().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getLegal() != null ? getLegal().hashCode() : 0);
        result = 31 * result + (getCompetitiveRates() != null ? getCompetitiveRates().hashCode() : 0);
        result = 31 * result + (getHr() != null ? getHr().hashCode() : 0);
        result = 31 * result + (getInfogreffe() != null ? getInfogreffe().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Competitors{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", webSite='" + webSite + '\'' +
            ", countryPhone='" + countryPhone + '\'' +
            ", logo=" + Arrays.toString(logo) +
            ", logoContentType='" + logoContentType + '\'' +
            ", globalGroups=" + globalGroups +
            ", offices=" + offices +
            ", finances=" + finances +
            ", dialogs=" + dialogs +
            ", country=" + country +
            ", legal=" + legal +
            ", competitiveRates=" + competitiveRates +
            ", hr=" + hr +
            ", infogreffe=" + infogreffe +
            '}';
    }
}

