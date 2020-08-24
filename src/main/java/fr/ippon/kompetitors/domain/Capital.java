package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Capital.
 */
@Entity
@Table(name = "capital")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Capital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "competitor_siren")
    private String competitorSiren;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "devise")
    private String devise;

    @Column(name = "nbr_parts")
    private Long nbrParts;

    @Column(name = "pourcentage_detention_pp")
    private Double pourcentageDetentionPP;

    @Column(name = "pourcentage_detention_pm")
    private Double pourcentageDetentionPM;

    @Column(name = "listed")
    private Boolean listed;

    @Column(name = "private_capital")
    private Boolean privateCapital;

    @Column(name = "independent_c")
    private Boolean independentC;

    @Column(name = "independent_e")
    private Boolean independentE;

    @Column(name = "old")
    private Boolean old;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitorSiren() {
        return competitorSiren;
    }

    public Capital competitorSiren(String competitorSiren) {
        this.competitorSiren = competitorSiren;
        return this;
    }

    public void setCompetitorSiren(String competitorSiren) {
        this.competitorSiren = competitorSiren;
    }

    public Double getMontant() {
        return montant;
    }

    public Capital montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return devise;
    }

    public Capital devise(String devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Long getNbrParts() {
        return nbrParts;
    }

    public Capital nbrParts(Long nbrParts) {
        this.nbrParts = nbrParts;
        return this;
    }

    public void setNbrParts(Long nbrParts) {
        this.nbrParts = nbrParts;
    }

    public Double getPourcentageDetentionPP() {
        return pourcentageDetentionPP;
    }

    public Capital pourcentageDetentionPP(Double pourcentageDetentionPP) {
        this.pourcentageDetentionPP = pourcentageDetentionPP;
        return this;
    }

    public void setPourcentageDetentionPP(Double pourcentageDetentionPP) {
        this.pourcentageDetentionPP = pourcentageDetentionPP;
    }

    public Double getPourcentageDetentionPM() {
        return pourcentageDetentionPM;
    }

    public Capital pourcentageDetentionPM(Double pourcentageDetentionPM) {
        this.pourcentageDetentionPM = pourcentageDetentionPM;
        return this;
    }

    public void setPourcentageDetentionPM(Double pourcentageDetentionPM) {
        this.pourcentageDetentionPM = pourcentageDetentionPM;
    }

    public Boolean isListed() {
        return listed;
    }

    public Capital listed(Boolean listed) {
        this.listed = listed;
        return this;
    }

    public void setListed(Boolean listed) {
        this.listed = listed;
    }

    public Boolean isPrivateCapital() {
        return privateCapital;
    }

    public Capital privateCapital(Boolean privateCapital) {
        this.privateCapital = privateCapital;
        return this;
    }

    public void setPrivateCapital(Boolean privateCapital) {
        this.privateCapital = privateCapital;
    }

    public Boolean isIndependentC() {
        return independentC;
    }

    public Capital independentC(Boolean independentC) {
        this.independentC = independentC;
        return this;
    }

    public void setIndependentC(Boolean independentC) {
        this.independentC = independentC;
    }

    public Boolean isIndependentE() {
        return independentE;
    }

    public Capital independentE(Boolean independentE) {
        this.independentE = independentE;
        return this;
    }

    public void setIndependentE(Boolean independentE) {
        this.independentE = independentE;
    }

    public Boolean isOld() {
        return old;
    }

    public Capital old(Boolean old) {
        this.old = old;
        return this;
    }

    public void setOld(Boolean old) {
        this.old = old;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Capital)) {
            return false;
        }
        return id != null && id.equals(((Capital) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Capital{" +
            "id=" + getId() +
            ", competitorSiren='" + getCompetitorSiren() + "'" +
            ", montant=" + getMontant() +
            ", devise='" + getDevise() + "'" +
            ", nbrParts=" + getNbrParts() +
            ", pourcentageDetentionPP=" + getPourcentageDetentionPP() +
            ", pourcentageDetentionPM=" + getPourcentageDetentionPM() +
            ", listed='" + isListed() + "'" +
            ", privateCapital='" + isPrivateCapital() + "'" +
            ", independentC='" + isIndependentC() + "'" +
            ", independentE='" + isIndependentE() + "'" +
            ", old='" + isOld() + "'" +
            "}";
    }
}
