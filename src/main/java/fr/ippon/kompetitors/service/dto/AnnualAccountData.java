package fr.ippon.kompetitors.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AnnualAccountData {

    @JsonProperty("CodeGreffe")
    String codeGreffe;

    @JsonProperty("Siren")
    String siren;

    @JsonProperty("Denomination")
    String denomination;

    @JsonProperty("Millesime")
    String millesime;

    @JsonProperty("TypeComptes")
    String typeComptes;

    @JsonProperty("Liasse")
    String liasse;

    @JsonProperty("Devise")
    String devise;

    @JsonProperty("DateCloture")
    String dateCloture;

    @JsonProperty("DureeExo")
    String dureeExo;

    @JsonProperty("DureeExoPrecedent")
    String dureeExoPrecedent;

    @JsonProperty("Postes")
    List<AnnualAccountPoste> postes;

    public String getCodeGreffe() {
        return codeGreffe;
    }

    public void setCodeGreffe(String codeGreffe) {
        this.codeGreffe = codeGreffe;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getMillesime() {
        return millesime;
    }

    public void setMillesime(String millesime) {
        this.millesime = millesime;
    }

    public String getTypeComptes() {
        return typeComptes;
    }

    public void setTypeComptes(String typeComptes) {
        this.typeComptes = typeComptes;
    }

    public String getLiasse() {
        return liasse;
    }

    public void setLiasse(String liasse) {
        this.liasse = liasse;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(String dateCloture) {
        this.dateCloture = dateCloture;
    }

    public String getDureeExo() {
        return dureeExo;
    }

    public void setDureeExo(String dureeExo) {
        this.dureeExo = dureeExo;
    }

    public String getDureeExoPrecedent() {
        return dureeExoPrecedent;
    }

    public void setDureeExoPrecedent(String dureeExoPrecedent) {
        this.dureeExoPrecedent = dureeExoPrecedent;
    }

    public List<AnnualAccountPoste> getPostes() {
        return postes;
    }

    public void setPostes(List<AnnualAccountPoste> postes) {
        this.postes = postes;
    }

    @Override
    public String toString() {
        return "AnnualAccountData{" +
            "codeGreffe='" + codeGreffe + '\'' +
            ", siren='" + siren + '\'' +
            ", denomination='" + denomination + '\'' +
            ", millesime='" + millesime + '\'' +
            ", typeComptes='" + typeComptes + '\'' +
            ", liasse='" + liasse + '\'' +
            ", devise='" + devise + '\'' +
            ", dateCloture='" + dateCloture + '\'' +
            ", dureeExo='" + dureeExo + '\'' +
            ", dureeExoPrecedent='" + dureeExoPrecedent + '\'' +
            ", postes=" + postes +
            '}';
    }
}
