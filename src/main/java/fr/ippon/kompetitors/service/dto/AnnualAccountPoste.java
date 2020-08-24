package fr.ippon.kompetitors.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnualAccountPoste {

    @JsonProperty("CodeInfogreffe")
    String CodeInfogreffe;

    @JsonProperty("Valeur")
    String valeur;

    public String getCodeInfogreffe() {
        return CodeInfogreffe;
    }

    public void setCodeInfogreffe(String codeInfogreffe) {
        CodeInfogreffe = codeInfogreffe;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "AnnualAccountPoste{" +
            "CodeInfogreffe='" + CodeInfogreffe + '\'' +
            ", valeur='" + valeur + '\'' +
            '}';
    }
}
