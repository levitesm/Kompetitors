package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A HrInfo.
 */
@Entity
@Table(name = "hr_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HrInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "interviews_number")
    private Integer interviewsNumber;

    @Column(name = "recrutment_time")
    private String recrutmentTime;

    @Column(name = "reviewed_cv_percent")
    private Double reviewedCvPercent;

    @Column(name = "hr_details")
    private String hrDetails;

    @Column(name = "vacancies_url")
    private String vacanciesUrl;

    @Column(name = "hr_specialists_number")
    private Integer hrSpecialistsNumber;

    @Column(name = "recruiters_number")
    private Integer recruitersNumber;

    @Column(name = "glassdoor_rate")
    private Float glassdoorRate;

    @Column(name = "viadeo_rate")
    private Float viadeoRate;

    @Column(name = "glassdoor_url")
    private String glassdoorUrl;

    @Column(name = "viadeo_url")
    private String viadeoUrl;

    @Column(name = "technical_test")
    private Boolean technicalTest;

    @Column(name = "personality_test")
    private Boolean personalityTest;

    @Column(name = "english_test")
    private Boolean englishTest;

    @Column(name = "cooptation_premium_amount")
    private Integer cooptationPremiumAmount;

    @Column(name = "junior_salary")
    private Integer juniorSalary;

    @Column(name = "average_salary")
    private Integer averageSalary;

    @Column(name = "signing_incentives")
    private String signingIncentives;

    @Column(name = "average_contract_duration")
    private Integer averageContractDuration;

    @ManyToOne
    @JoinColumn(unique = true)
    @JsonIgnoreProperties(value={"societeMain", "offices", "finances", "prinfo", "hr", "people", "dialogs", "legal", "infogreffe"})
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInterviewsNumber() {
        return interviewsNumber;
    }

    public HrInfo interviewsNumber(Integer interviewsNumber) {
        this.interviewsNumber = interviewsNumber;
        return this;
    }

    public void setInterviewsNumber(Integer interviewsNumber) {
        this.interviewsNumber = interviewsNumber;
    }

    public String getRecrutmentTime() {
        return recrutmentTime;
    }

    public HrInfo recrutmentTime(String recrutmentTime) {
        this.recrutmentTime = recrutmentTime;
        return this;
    }

    public void setRecrutmentTime(String recrutmentTime) {
        this.recrutmentTime = recrutmentTime;
    }

    public Double getReviewedCvPercent() {
        return reviewedCvPercent;
    }

    public HrInfo reviewedCvPercent(Double reviewedCvPercent) {
        this.reviewedCvPercent = reviewedCvPercent;
        return this;
    }

    public void setReviewedCvPercent(Double reviewedCvPercent) {
        this.reviewedCvPercent = reviewedCvPercent;
    }

    public String getHrDetails() {
        return hrDetails;
    }

    public HrInfo hrDetails(String hrDetails) {
        this.hrDetails = hrDetails;
        return this;
    }

    public void setHrDetails(String hrDetails) {
        this.hrDetails = hrDetails;
    }

    public String getVacanciesUrl() {
        return vacanciesUrl;
    }

    public HrInfo vacanciesUrl(String vacanciesUrl) {
        this.vacanciesUrl = vacanciesUrl;
        return this;
    }

    public void setVacanciesUrl(String vacanciesUrl) {
        this.vacanciesUrl = vacanciesUrl;
    }

    public Integer getHrSpecialistsNumber() {
        return hrSpecialistsNumber;
    }

    public HrInfo hrSpecialistsNumber(Integer hrSpecialistsNumber) {
        this.hrSpecialistsNumber = hrSpecialistsNumber;
        return this;
    }

    public void setHrSpecialistsNumber(Integer hrSpecialistsNumber) {
        this.hrSpecialistsNumber = hrSpecialistsNumber;
    }

    public Float getGlassdoorRate() {
        return glassdoorRate;
    }

    public Integer getRecruitersNumber() {
        return recruitersNumber;
    }

    public void setRecruitersNumber(Integer recruitersNumber) {
        this.recruitersNumber = recruitersNumber;
    }

    public HrInfo glassdoorRate(Float glassdoorRate) {
        this.glassdoorRate = glassdoorRate;
        return this;
    }

    public void setGlassdoorRate(Float glassdoorRate) {
        this.glassdoorRate = glassdoorRate;
    }

    public Float getViadeoRate() {
        return viadeoRate;
    }

    public HrInfo viadeoRate(Float viadeoRate) {
        this.viadeoRate = viadeoRate;
        return this;
    }

    public void setViadeoRate(Float viadeoRate) {
        this.viadeoRate = viadeoRate;
    }

    public String getGlassdoorUrl() {
        return glassdoorUrl;
    }

    public HrInfo glassdoorUrl(String glassdoorUrl) {
        this.glassdoorUrl = glassdoorUrl;
        return this;
    }

    public void setGlassdoorUrl(String glassdoorUrl) {
        this.glassdoorUrl = glassdoorUrl;
    }

    public String getViadeoUrl() {
        return viadeoUrl;
    }

    public HrInfo viadeoUrl(String viadeoUrl) {
        this.viadeoUrl = viadeoUrl;
        return this;
    }

    public void setViadeoUrl(String viadeoUrl) {
        this.viadeoUrl = viadeoUrl;
    }

    public Integer getCooptationPremiumAmount() {
        return cooptationPremiumAmount;
    }

    public HrInfo cooptationPremiumAmount(Integer cooptationPremiumAmount) {
        this.cooptationPremiumAmount = cooptationPremiumAmount;
        return this;
    }

    public void setCooptationPremiumAmount(Integer cooptationPremiumAmount) {
        this.cooptationPremiumAmount = cooptationPremiumAmount;
    }

    public Integer getJuniorSalary() {
        return juniorSalary;
    }

    public HrInfo juniorSalary(Integer juniorSalary) {
        this.juniorSalary = juniorSalary;
        return this;
    }

    public void setJuniorSalary(Integer juniorSalary) {
        this.juniorSalary = juniorSalary;
    }

    public Integer getAverageSalary() {
        return averageSalary;
    }

    public HrInfo averageSalary(Integer averageSalary) {
        this.averageSalary = averageSalary;
        return this;
    }

    public void setAverageSalary(Integer averageSalary) {
        this.averageSalary = averageSalary;
    }

    public String getSigningIncentives() {
        return signingIncentives;
    }

    public HrInfo signingIncentives(String signingIncentives) {
        this.signingIncentives = signingIncentives;
        return this;
    }

    public void setSigningIncentives(String signingIncentives) {
        this.signingIncentives = signingIncentives;
    }

    public Integer getAverageContractDuration() {
        return averageContractDuration;
    }

    public HrInfo averageContractDuration(Integer averageContractDuration) {
        this.averageContractDuration = averageContractDuration;
        return this;
    }

    public void setAverageContractDuration(Integer averageContractDuration) {
        this.averageContractDuration = averageContractDuration;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public HrInfo competitor(Competitors competitors) {
        this.competitor = competitors;
        return this;
    }

    public Boolean getTechnicalTest() {
        return technicalTest;
    }

    public void setTechnicalTest(Boolean technicalTest) {
        this.technicalTest = technicalTest;
    }

    public Boolean getPersonalityTest() {
        return personalityTest;
    }

    public void setPersonalityTest(Boolean personalityTest) {
        this.personalityTest = personalityTest;
    }

    public Boolean getEnglishTest() {
        return englishTest;
    }

    public void setEnglishTest(Boolean englishTest) {
        this.englishTest = englishTest;
    }

    public void setCompetitor(Competitors competitors) {
        this.competitor = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HrInfo)) {
            return false;
        }
        return id != null && id.equals(((HrInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HrInfo{" +
            "id=" + getId() +
            ", interviewsNumber=" + getInterviewsNumber() +
            ", recrutmentTime='" + getRecrutmentTime() + "'" +
            ", reviewedCvPercent=" + getReviewedCvPercent() +
            ", hrDetails='" + getHrDetails() + "'" +
            ", vacanciesUrl='" + getVacanciesUrl() + "'" +
            ", hrSpecialistsNumber=" + getHrSpecialistsNumber() +
            ", glassdoorRate=" + getGlassdoorRate() +
            ", viadeoRate=" + getViadeoRate() +
            ", glassdoorUrl='" + getGlassdoorUrl() + "'" +
            ", viadeoUrl='" + getViadeoUrl() + "'" +
            ", cooptationPremiumAmount=" + getCooptationPremiumAmount() +
            ", juniorSalary=" + getJuniorSalary() +
            ", averageSalary=" + getAverageSalary() +
            ", signingIncentives='" + getSigningIncentives() + "'" +
            ", averageContractDuration=" + getAverageContractDuration() +
            "}";
    }
}
