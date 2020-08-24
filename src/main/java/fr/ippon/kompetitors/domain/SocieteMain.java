package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SocieteMain.
 */
@Entity
@Table(name = "societe_main")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SocieteMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "siren")
    private String siren;

    @Column(name = "deno")
    private String deno;

    @Column(name = "greffe")
    private String greffe;

    @Column(name = "enseigne")
    private String enseigne;

    @Column(name = "psiret")
    private String psiret;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "codepostal")
    private String codepostal;

    @Column(name = "normcommune")
    private String normcommune;

    @Column(name = "commune")
    private String commune;

    @Column(name = "ape")
    private String ape;

    @Column(name = "apetexte")
    private String apetexte;

    @Column(name = "dateimmat")
    private String dateimmat;

    @Column(name = "dcren")
    private String dcren;

    @Column(name = "nationalite")
    private String nationalite;

    @Column(name = "formejur")
    private String formejur;

    @Column(name = "capital")
    private String capital;

    @Column(name = "devisecap")
    private String devisecap;

    @Column(name = "typecap")
    private String typecap;

    @Column(name = "url")
    private String url;

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

    public String getSiren() {
        return siren;
    }

    public SocieteMain siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getDeno() {
        return deno;
    }

    public SocieteMain deno(String deno) {
        this.deno = deno;
        return this;
    }

    public void setDeno(String deno) {
        this.deno = deno;
    }

    public String getGreffe() {
        return greffe;
    }

    public SocieteMain greffe(String greffe) {
        this.greffe = greffe;
        return this;
    }

    public void setGreffe(String greffe) {
        this.greffe = greffe;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public SocieteMain enseigne(String enseigne) {
        this.enseigne = enseigne;
        return this;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    public String getPsiret() {
        return psiret;
    }

    public SocieteMain psiret(String psiret) {
        this.psiret = psiret;
        return this;
    }

    public void setPsiret(String psiret) {
        this.psiret = psiret;
    }

    public String getAdresse() {
        return adresse;
    }

    public SocieteMain adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public SocieteMain codepostal(String codepostal) {
        this.codepostal = codepostal;
        return this;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getNormcommune() {
        return normcommune;
    }

    public SocieteMain normcommune(String normcommune) {
        this.normcommune = normcommune;
        return this;
    }

    public void setNormcommune(String normcommune) {
        this.normcommune = normcommune;
    }

    public String getCommune() {
        return commune;
    }

    public SocieteMain commune(String commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getApe() {
        return ape;
    }

    public SocieteMain ape(String ape) {
        this.ape = ape;
        return this;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getApetexte() {
        return apetexte;
    }

    public SocieteMain apetexte(String apetexte) {
        this.apetexte = apetexte;
        return this;
    }

    public void setApetexte(String apetexte) {
        this.apetexte = apetexte;
    }

    public String getDateimmat() {
        return dateimmat;
    }

    public SocieteMain dateimmat(String dateimmat) {
        this.dateimmat = dateimmat;
        return this;
    }

    public void setDateimmat(String dateimmat) {
        this.dateimmat = dateimmat;
    }

    public String getDcren() {
        return dcren;
    }

    public SocieteMain dcren(String dcren) {
        this.dcren = dcren;
        return this;
    }

    public void setDcren(String dcren) {
        this.dcren = dcren;
    }

    public String getNationalite() {
        return nationalite;
    }

    public SocieteMain nationalite(String nationalite) {
        this.nationalite = nationalite;
        return this;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getFormejur() {
        return formejur;
    }

    public SocieteMain formejur(String formejur) {
        this.formejur = formejur;
        return this;
    }

    public void setFormejur(String formejur) {
        this.formejur = formejur;
    }

    public String getCapital() {
        return capital;
    }

    public SocieteMain capital(String capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getDevisecap() {
        return devisecap;
    }

    public SocieteMain devisecap(String devisecap) {
        this.devisecap = devisecap;
        return this;
    }

    public void setDevisecap(String devisecap) {
        this.devisecap = devisecap;
    }

    public String getTypecap() {
        return typecap;
    }

    public SocieteMain typecap(String typecap) {
        this.typecap = typecap;
        return this;
    }

    public void setTypecap(String typecap) {
        this.typecap = typecap;
    }

    public String getUrl() {
        return url;
    }

    public SocieteMain url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public SocieteMain competitor(Competitors competitors) {
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
        if (!(o instanceof SocieteMain)) {
            return false;
        }
        return id != null && id.equals(((SocieteMain) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SocieteMain{" +
            "id=" + getId() +
            ", siren='" + getSiren() + "'" +
            ", deno='" + getDeno() + "'" +
            ", greffe='" + getGreffe() + "'" +
            ", enseigne='" + getEnseigne() + "'" +
            ", psiret='" + getPsiret() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codepostal='" + getCodepostal() + "'" +
            ", normcommune='" + getNormcommune() + "'" +
            ", commune='" + getCommune() + "'" +
            ", ape='" + getApe() + "'" +
            ", apetexte='" + getApetexte() + "'" +
            ", dateimmat='" + getDateimmat() + "'" +
            ", dcren='" + getDcren() + "'" +
            ", nationalite='" + getNationalite() + "'" +
            ", formejur='" + getFormejur() + "'" +
            ", capital='" + getCapital() + "'" +
            ", devisecap='" + getDevisecap() + "'" +
            ", typecap='" + getTypecap() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
