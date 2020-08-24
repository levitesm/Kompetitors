package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ShareHolders.
 */
@Entity
@Table(name = "share_holders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShareHolders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "competitor_siren")
    private String competitorSiren;

    @Column(name = "type_personne")
    private String typePersonne;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "civilite")
    private String civilite;

    @Column(name = "nom_patronymique")
    private String nomPatronymique;

    @Column(name = "nom_usage")
    private String nomUsage;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "libelle_forme_juridique")
    private String libelleFormeJuridique;

    @Column(name = "code_forme_juridique")
    private String codeFormeJuridique;

    @Column(name = "siren")
    private String siren;

    @Column(name = "code_ape")
    private String codeApe;

    @Column(name = "date_naissance")
    private String dateNaissance;

    @Column(name = "nbr_parts")
    private Long nbrParts;

    @Column(name = "pourcentage_detention")
    private Double pourcentageDetention;

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

    public ShareHolders competitorSiren(String competitorSiren) {
        this.competitorSiren = competitorSiren;
        return this;
    }

    public void setCompetitorSiren(String competitorSiren) {
        this.competitorSiren = competitorSiren;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public ShareHolders typePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
        return this;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getDenomination() {
        return denomination;
    }

    public ShareHolders denomination(String denomination) {
        this.denomination = denomination;
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getCivilite() {
        return civilite;
    }

    public ShareHolders civilite(String civilite) {
        this.civilite = civilite;
        return this;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNomPatronymique() {
        return nomPatronymique;
    }

    public ShareHolders nomPatronymique(String nomPatronymique) {
        this.nomPatronymique = nomPatronymique;
        return this;
    }

    public void setNomPatronymique(String nomPatronymique) {
        this.nomPatronymique = nomPatronymique;
    }

    public String getNomUsage() {
        return nomUsage;
    }

    public ShareHolders nomUsage(String nomUsage) {
        this.nomUsage = nomUsage;
        return this;
    }

    public void setNomUsage(String nomUsage) {
        this.nomUsage = nomUsage;
    }

    public String getPrenom() {
        return prenom;
    }

    public ShareHolders prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLibelleFormeJuridique() {
        return libelleFormeJuridique;
    }

    public ShareHolders libelleFormeJuridique(String libelleFormeJuridique) {
        this.libelleFormeJuridique = libelleFormeJuridique;
        return this;
    }

    public void setLibelleFormeJuridique(String libelleFormeJuridique) {
        this.libelleFormeJuridique = libelleFormeJuridique;
    }

    public String getCodeFormeJuridique() {
        return codeFormeJuridique;
    }

    public ShareHolders codeFormeJuridique(String codeFormeJuridique) {
        this.codeFormeJuridique = codeFormeJuridique;
        return this;
    }

    public void setCodeFormeJuridique(String codeFormeJuridique) {
        this.codeFormeJuridique = codeFormeJuridique;
    }

    public String getSiren() {
        return siren;
    }

    public ShareHolders siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getCodeApe() {
        return codeApe;
    }

    public ShareHolders codeApe(String codeApe) {
        this.codeApe = codeApe;
        return this;
    }

    public void setCodeApe(String codeApe) {
        this.codeApe = codeApe;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public ShareHolders dateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Long getNbrParts() {
        return nbrParts;
    }

    public ShareHolders nbrParts(Long nbrParts) {
        this.nbrParts = nbrParts;
        return this;
    }

    public void setNbrParts(Long nbrParts) {
        this.nbrParts = nbrParts;
    }

    public Double getPourcentageDetention() {
        return pourcentageDetention;
    }

    public ShareHolders pourcentageDetention(Double pourcentageDetention) {
        this.pourcentageDetention = pourcentageDetention;
        return this;
    }

    public void setPourcentageDetention(Double pourcentageDetention) {
        this.pourcentageDetention = pourcentageDetention;
    }

    public Boolean isOld() {
        return old;
    }

    public ShareHolders old(Boolean old) {
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
        if (!(o instanceof ShareHolders)) {
            return false;
        }
        return id != null && id.equals(((ShareHolders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShareHolders{" +
            "id=" + getId() +
            ", competitorSiren='" + getCompetitorSiren() + "'" +
            ", typePersonne='" + getTypePersonne() + "'" +
            ", denomination='" + getDenomination() + "'" +
            ", civilite='" + getCivilite() + "'" +
            ", nomPatronymique='" + getNomPatronymique() + "'" +
            ", nomUsage='" + getNomUsage() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", libelleFormeJuridique='" + getLibelleFormeJuridique() + "'" +
            ", codeFormeJuridique='" + getCodeFormeJuridique() + "'" +
            ", siren='" + getSiren() + "'" +
            ", codeApe='" + getCodeApe() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", nbrParts=" + getNbrParts() +
            ", pourcentageDetention=" + getPourcentageDetention() +
            ", old='" + isOld() + "'" +
            "}";
    }
}
