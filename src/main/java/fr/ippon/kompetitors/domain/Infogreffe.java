package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Infogreffe.
 */
@Entity
@Table(name = "infogreffe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Infogreffe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "departement")
    private String departement;

    @Column(name = "ville")
    private String ville;

    @Column(name = "num_dept")
    private String numDept;

    @Column(name = "code_greffe")
    private String codeGreffe;

    @Column(name = "date_immatriculation")
    private String dateImmatriculation;

    @Column(name = "ca_1")
    private String ca1;

    @Column(name = "siren")
    private String siren;

    @Column(name = "ca_2")
    private String ca2;

    @Column(name = "forme_juridique")
    private String formeJuridique;

    @Column(name = "resultat_3")
    private String resultat3;

    @Column(name = "resultat_2")
    private String resultat2;

    @Column(name = "resultat_1")
    private String resultat1;

    @Column(name = "ficheidentite")
    private String ficheidentite;

    @Column(name = "duree_1")
    private String duree1;

    @Column(name = "date_de_publication")
    private String dateDePublication;

    @Column(name = "statut")
    private String statut;

    @Column(name = "nic")
    private String nic;

    @Column(name = "code_ape")
    private String codeApe;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tranche_ca_millesime_3")
    private String trancheCaMillesime3;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "duree_2")
    private String duree2;

    @Column(name = "effectif_1")
    private String effectif1;

    @Column(name = "effectif_3")
    private String effectif3;

    @Column(name = "effectif_2")
    private String effectif2;

    @Column(name = "ca_3")
    private String ca3;

    @Column(name = "tranche_ca_millesime_1")
    private String trancheCaMillesime1;

    @Column(name = "duree_3")
    private String duree3;

    @Column(name = "tranche_ca_millesime_2")
    private String trancheCaMillesime2;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "date_de_cloture_exercice_1")
    private String dateDeClotureExercice1;

    @Column(name = "date_de_cloture_exercice_3")
    private String dateDeClotureExercice3;

    @Column(name = "date_de_cloture_exercice_2")
    private String dateDeClotureExercice2;

    @Column(name = "libelle_ape")
    private String libelleApe;

    @Column(name = "greffe")
    private String greffe;

    @Column(name = "millesime_3")
    private String millesime3;

    @Column(name = "millesime_2")
    private String millesime2;

    @Column(name = "millesime_1")
    private String millesime1;

    @Column(name = "region")
    private String region;

    @ManyToOne
    @JsonIgnoreProperties(value={"societeMain", "offices", "finances", "prinfo", "hr", "people", "dialogs" })
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartement() {
        return departement;
    }

    public Infogreffe departement(String departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getVille() {
        return ville;
    }

    public Infogreffe ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumDept() {
        return numDept;
    }

    public Infogreffe numDept(String numDept) {
        this.numDept = numDept;
        return this;
    }

    public void setNumDept(String numDept) {
        this.numDept = numDept;
    }

    public String getCodeGreffe() {
        return codeGreffe;
    }

    public Infogreffe codeGreffe(String codeGreffe) {
        this.codeGreffe = codeGreffe;
        return this;
    }

    public void setCodeGreffe(String codeGreffe) {
        this.codeGreffe = codeGreffe;
    }

    public String getDateImmatriculation() {
        return dateImmatriculation;
    }

    public Infogreffe dateImmatriculation(String dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
        return this;
    }

    public void setDateImmatriculation(String dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
    }

    public String getCa1() {
        return ca1;
    }

    public Infogreffe ca1(String ca1) {
        this.ca1 = ca1;
        return this;
    }

    public void setCa1(String ca1) {
        this.ca1 = ca1;
    }

    public String getSiren() {
        return siren;
    }

    public Infogreffe siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getCa2() {
        return ca2;
    }

    public Infogreffe ca2(String ca2) {
        this.ca2 = ca2;
        return this;
    }

    public void setCa2(String ca2) {
        this.ca2 = ca2;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public Infogreffe formeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
        return this;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public String getResultat3() {
        return resultat3;
    }

    public Infogreffe resultat3(String resultat3) {
        this.resultat3 = resultat3;
        return this;
    }

    public void setResultat3(String resultat3) {
        this.resultat3 = resultat3;
    }

    public String getResultat2() {
        return resultat2;
    }

    public Infogreffe resultat2(String resultat2) {
        this.resultat2 = resultat2;
        return this;
    }

    public void setResultat2(String resultat2) {
        this.resultat2 = resultat2;
    }

    public String getResultat1() {
        return resultat1;
    }

    public Infogreffe resultat1(String resultat1) {
        this.resultat1 = resultat1;
        return this;
    }

    public void setResultat1(String resultat1) {
        this.resultat1 = resultat1;
    }

    public String getFicheidentite() {
        return ficheidentite;
    }

    public Infogreffe ficheidentite(String ficheidentite) {
        this.ficheidentite = ficheidentite;
        return this;
    }

    public void setFicheidentite(String ficheidentite) {
        this.ficheidentite = ficheidentite;
    }

    public String getDuree1() {
        return duree1;
    }

    public Infogreffe duree1(String duree1) {
        this.duree1 = duree1;
        return this;
    }

    public void setDuree1(String duree1) {
        this.duree1 = duree1;
    }

    public String getDateDePublication() {
        return dateDePublication;
    }

    public Infogreffe dateDePublication(String dateDePublication) {
        this.dateDePublication = dateDePublication;
        return this;
    }

    public void setDateDePublication(String dateDePublication) {
        this.dateDePublication = dateDePublication;
    }

    public String getStatut() {
        return statut;
    }

    public Infogreffe statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNic() {
        return nic;
    }

    public Infogreffe nic(String nic) {
        this.nic = nic;
        return this;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCodeApe() {
        return codeApe;
    }

    public Infogreffe codeApe(String codeApe) {
        this.codeApe = codeApe;
        return this;
    }

    public void setCodeApe(String codeApe) {
        this.codeApe = codeApe;
    }

    public String getAdresse() {
        return adresse;
    }

    public Infogreffe adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTrancheCaMillesime3() {
        return trancheCaMillesime3;
    }

    public Infogreffe trancheCaMillesime3(String trancheCaMillesime3) {
        this.trancheCaMillesime3 = trancheCaMillesime3;
        return this;
    }

    public void setTrancheCaMillesime3(String trancheCaMillesime3) {
        this.trancheCaMillesime3 = trancheCaMillesime3;
    }

    public String getDenomination() {
        return denomination;
    }

    public Infogreffe denomination(String denomination) {
        this.denomination = denomination;
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDuree2() {
        return duree2;
    }

    public Infogreffe duree2(String duree2) {
        this.duree2 = duree2;
        return this;
    }

    public void setDuree2(String duree2) {
        this.duree2 = duree2;
    }

    public String getEffectif1() {
        return effectif1;
    }

    public Infogreffe effectif1(String effectif1) {
        this.effectif1 = effectif1;
        return this;
    }

    public void setEffectif1(String effectif1) {
        this.effectif1 = effectif1;
    }

    public String getEffectif3() {
        return effectif3;
    }

    public Infogreffe effectif3(String effectif3) {
        this.effectif3 = effectif3;
        return this;
    }

    public void setEffectif3(String effectif3) {
        this.effectif3 = effectif3;
    }

    public String getEffectif2() {
        return effectif2;
    }

    public Infogreffe effectif2(String effectif2) {
        this.effectif2 = effectif2;
        return this;
    }

    public void setEffectif2(String effectif2) {
        this.effectif2 = effectif2;
    }

    public String getCa3() {
        return ca3;
    }

    public Infogreffe ca3(String ca3) {
        this.ca3 = ca3;
        return this;
    }

    public void setCa3(String ca3) {
        this.ca3 = ca3;
    }

    public String getTrancheCaMillesime1() {
        return trancheCaMillesime1;
    }

    public Infogreffe trancheCaMillesime1(String trancheCaMillesime1) {
        this.trancheCaMillesime1 = trancheCaMillesime1;
        return this;
    }

    public void setTrancheCaMillesime1(String trancheCaMillesime1) {
        this.trancheCaMillesime1 = trancheCaMillesime1;
    }

    public String getDuree3() {
        return duree3;
    }

    public Infogreffe duree3(String duree3) {
        this.duree3 = duree3;
        return this;
    }

    public void setDuree3(String duree3) {
        this.duree3 = duree3;
    }

    public String getTrancheCaMillesime2() {
        return trancheCaMillesime2;
    }

    public Infogreffe trancheCaMillesime2(String trancheCaMillesime2) {
        this.trancheCaMillesime2 = trancheCaMillesime2;
        return this;
    }

    public void setTrancheCaMillesime2(String trancheCaMillesime2) {
        this.trancheCaMillesime2 = trancheCaMillesime2;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public Infogreffe codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getDateDeClotureExercice1() {
        return dateDeClotureExercice1;
    }

    public Infogreffe dateDeClotureExercice1(String dateDeClotureExercice1) {
        this.dateDeClotureExercice1 = dateDeClotureExercice1;
        return this;
    }

    public void setDateDeClotureExercice1(String dateDeClotureExercice1) {
        this.dateDeClotureExercice1 = dateDeClotureExercice1;
    }

    public String getDateDeClotureExercice3() {
        return dateDeClotureExercice3;
    }

    public Infogreffe dateDeClotureExercice3(String dateDeClotureExercice3) {
        this.dateDeClotureExercice3 = dateDeClotureExercice3;
        return this;
    }

    public void setDateDeClotureExercice3(String dateDeClotureExercice3) {
        this.dateDeClotureExercice3 = dateDeClotureExercice3;
    }

    public String getDateDeClotureExercice2() {
        return dateDeClotureExercice2;
    }

    public Infogreffe dateDeClotureExercice2(String dateDeClotureExercice2) {
        this.dateDeClotureExercice2 = dateDeClotureExercice2;
        return this;
    }

    public void setDateDeClotureExercice2(String dateDeClotureExercice2) {
        this.dateDeClotureExercice2 = dateDeClotureExercice2;
    }

    public String getLibelleApe() {
        return libelleApe;
    }

    public Infogreffe libelleApe(String libelleApe) {
        this.libelleApe = libelleApe;
        return this;
    }

    public void setLibelleApe(String libelleApe) {
        this.libelleApe = libelleApe;
    }

    public String getGreffe() {
        return greffe;
    }

    public Infogreffe greffe(String greffe) {
        this.greffe = greffe;
        return this;
    }

    public void setGreffe(String greffe) {
        this.greffe = greffe;
    }

    public String getMillesime3() {
        return millesime3;
    }

    public Infogreffe millesime3(String millesime3) {
        this.millesime3 = millesime3;
        return this;
    }

    public void setMillesime3(String millesime3) {
        this.millesime3 = millesime3;
    }

    public String getMillesime2() {
        return millesime2;
    }

    public Infogreffe millesime2(String millesime2) {
        this.millesime2 = millesime2;
        return this;
    }

    public void setMillesime2(String millesime2) {
        this.millesime2 = millesime2;
    }

    public String getMillesime1() {
        return millesime1;
    }

    public Infogreffe millesime1(String millesime1) {
        this.millesime1 = millesime1;
        return this;
    }

    public void setMillesime1(String millesime1) {
        this.millesime1 = millesime1;
    }

    public String getRegion() {
        return region;
    }

    public Infogreffe region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public Infogreffe competitor(Competitors competitors) {
        this.competitor = competitors;
        return this;
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
        if (!(o instanceof Infogreffe)) {
            return false;
        }
        return id != null && id.equals(((Infogreffe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Infogreffe{" +
            "id=" + getId() +
            ", departement='" + getDepartement() + "'" +
            ", ville='" + getVille() + "'" +
            ", numDept='" + getNumDept() + "'" +
            ", codeGreffe='" + getCodeGreffe() + "'" +
            ", dateImmatriculation='" + getDateImmatriculation() + "'" +
            ", ca1='" + getCa1() + "'" +
            ", siren='" + getSiren() + "'" +
            ", ca2='" + getCa2() + "'" +
            ", formeJuridique='" + getFormeJuridique() + "'" +
            ", resultat3='" + getResultat3() + "'" +
            ", resultat2='" + getResultat2() + "'" +
            ", resultat1='" + getResultat1() + "'" +
            ", ficheidentite='" + getFicheidentite() + "'" +
            ", duree1='" + getDuree1() + "'" +
            ", dateDePublication='" + getDateDePublication() + "'" +
            ", statut='" + getStatut() + "'" +
            ", nic='" + getNic() + "'" +
            ", codeApe='" + getCodeApe() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", trancheCaMillesime3='" + getTrancheCaMillesime3() + "'" +
            ", denomination='" + getDenomination() + "'" +
            ", duree2='" + getDuree2() + "'" +
            ", effectif1='" + getEffectif1() + "'" +
            ", effectif3='" + getEffectif3() + "'" +
            ", effectif2='" + getEffectif2() + "'" +
            ", ca3='" + getCa3() + "'" +
            ", trancheCaMillesime1='" + getTrancheCaMillesime1() + "'" +
            ", duree3='" + getDuree3() + "'" +
            ", trancheCaMillesime2='" + getTrancheCaMillesime2() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", dateDeClotureExercice1='" + getDateDeClotureExercice1() + "'" +
            ", dateDeClotureExercice3='" + getDateDeClotureExercice3() + "'" +
            ", dateDeClotureExercice2='" + getDateDeClotureExercice2() + "'" +
            ", libelleApe='" + getLibelleApe() + "'" +
            ", greffe='" + getGreffe() + "'" +
            ", millesime3='" + getMillesime3() + "'" +
            ", millesime2='" + getMillesime2() + "'" +
            ", millesime1='" + getMillesime1() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
