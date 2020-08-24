package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Representatives.
 */
@Entity
@Table(name = "representatives")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Representatives implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "competitor_siren")
    private String competitorSiren;

    @Column(name = "qualite")
    private String qualite;

    @Column(name = "type")
    private String type;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom_usage")
    private String nomUsage;

    @Column(name = "date_naissance")
    private String dateNaissance;

    @Column(name = "denomination_pm")
    private String denominationPM;

    @Column(name = "siren_pm")
    private String sirenPM;

    @Column(name = "linked_in_url")
    private String linkedInUrl;

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

    public Representatives competitorSiren(String competitorSiren) {
        this.competitorSiren = competitorSiren;
        return this;
    }

    public void setCompetitorSiren(String competitorSiren) {
        this.competitorSiren = competitorSiren;
    }

    public String getQualite() {
        return qualite;
    }

    public Representatives qualite(String qualite) {
        this.qualite = qualite;
        return this;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public String getType() {
        return type;
    }

    public Representatives type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public Representatives nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Representatives prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomUsage() {
        return nomUsage;
    }

    public Representatives nomUsage(String nomUsage) {
        this.nomUsage = nomUsage;
        return this;
    }

    public void setNomUsage(String nomUsage) {
        this.nomUsage = nomUsage;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public Representatives dateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getDenominationPM() {
        return denominationPM;
    }

    public Representatives denominationPM(String denominationPM) {
        this.denominationPM = denominationPM;
        return this;
    }

    public void setDenominationPM(String denominationPM) {
        this.denominationPM = denominationPM;
    }

    public String getSirenPM() {
        return sirenPM;
    }

    public Representatives sirenPM(String sirenPM) {
        this.sirenPM = sirenPM;
        return this;
    }

    public void setSirenPM(String sirenPM) {
        this.sirenPM = sirenPM;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public Representatives linkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
        return this;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public Boolean isOld() {
        return old;
    }

    public Representatives old(Boolean old) {
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
        if (!(o instanceof Representatives)) {
            return false;
        }
        return id != null && id.equals(((Representatives) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Representatives{" +
            "id=" + getId() +
            ", competitorSiren='" + getCompetitorSiren() + "'" +
            ", qualite='" + getQualite() + "'" +
            ", type='" + getType() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nomUsage='" + getNomUsage() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", denominationPM='" + getDenominationPM() + "'" +
            ", sirenPM='" + getSirenPM() + "'" +
            ", linkedInUrl='" + getLinkedInUrl() + "'" +
            ", old='" + isOld() + "'" +
            "}";
    }
}
