package fr.ippon.kompetitors.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AnnualAccountResponse implements Serializable {

    @JsonProperty("Metadata")
    AnnualAccountMetadata metadata;

    @JsonProperty("Data")
    AnnualAccountData data;

    public AnnualAccountMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(AnnualAccountMetadata metadata) {
        this.metadata = metadata;
    }

    public AnnualAccountData getData() {
        return data;
    }

    public void setData(AnnualAccountData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AnnualAccountResponse{" +
            "metadata=" + metadata +
            ", data=" + data +
            '}';
    }
}
