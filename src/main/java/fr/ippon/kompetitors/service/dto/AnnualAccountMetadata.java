package fr.ippon.kompetitors.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnualAccountMetadata {
    @JsonProperty("CreditsUsed")
    String creditsUsed;

    @JsonProperty("CreditsLeft")
    String creditsLeft;

    public String getCreditsUsed() {
        return creditsUsed;
    }

    public void setCreditsUsed(String creditsUsed) {
        this.creditsUsed = creditsUsed;
    }

    public String getCreditsLeft() {
        return creditsLeft;
    }

    public void setCreditsLeft(String creditsLeft) {
        this.creditsLeft = creditsLeft;
    }

    @Override
    public String toString() {
        return "AnnualAccountMetadata{" +
            "creditsUsed='" + creditsUsed + '\'' +
            ", creditsLeft='" + creditsLeft + '\'' +
            '}';
    }
}
