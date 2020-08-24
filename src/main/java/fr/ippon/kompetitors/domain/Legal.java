package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Legal.
 */
@Entity
@Table(name = "legal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Legal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "siren")
    private String siren;

    @Column(name = "greffe")
    private String greffe;

    @Column(name = "founded")
    private LocalDate founded;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "legal_form")
    private String legalForm;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Competitors competitor;

    @ManyToOne
    @JsonIgnoreProperties("legals")
    private ListOwnerships ownership;

    @ManyToOne
    @JsonIgnoreProperties("legals")
    private ListActivities activity;

    @ManyToOne
    @JsonIgnoreProperties("legals")
    private ListPricings pricing;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public Legal legalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
        return this;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getSiren() {
        return siren;
    }

    public Legal siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getGreffe() {
        return greffe;
    }

    public Legal greffe(String greffe) {
        this.greffe = greffe;
        return this;
    }

    public void setGreffe(String greffe) {
        this.greffe = greffe;
    }

    public LocalDate getFounded() {
        return founded;
    }

    public Legal founded(LocalDate founded) {
        this.founded = founded;
        return this;
    }

    public void setFounded(LocalDate founded) {
        this.founded = founded;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Legal updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public Legal legalForm(String legalForm) {
        this.legalForm = legalForm;
        return this;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public Legal competitor(Competitors competitors) {
        this.competitor = competitors;
        return this;
    }

    public void setCompetitor(Competitors competitors) {
        this.competitor = competitors;
    }

    public ListOwnerships getOwnership() {
        return ownership;
    }

    public Legal ownership(ListOwnerships listOwnerships) {
        this.ownership = listOwnerships;
        return this;
    }

    public void setOwnership(ListOwnerships listOwnerships) {
        this.ownership = listOwnerships;
    }

    public ListActivities getActivity() {
        return activity;
    }

    public Legal activity(ListActivities listActivities) {
        this.activity = listActivities;
        return this;
    }

    public void setActivity(ListActivities listActivities) {
        this.activity = listActivities;
    }

    public ListPricings getPricing() {
        return pricing;
    }

    public Legal pricing(ListPricings listPricings) {
        this.pricing = listPricings;
        return this;
    }

    public void setPricing(ListPricings listPricings) {
        this.pricing = listPricings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @JsonIgnore
    public Long getCompetitorId() {
        return competitor != null ? competitor.getId() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Legal)) {
            return false;
        }
        return id != null && id.equals(((Legal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Legal{" +
            "id=" + getId() +
            ", legalAddress='" + getLegalAddress() + "'" +
            ", siren='" + getSiren() + "'" +
            ", greffe='" + getGreffe() + "'" +
            ", founded='" + getFounded() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", legalForm='" + getLegalForm() + "'" +
            "}";
    }
}
