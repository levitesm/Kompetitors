package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.TechPartners} entity.
 */
public class TechPartnersDTO implements Serializable {

    private Long id;


    private Long valueId;

    private Long competitorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long listTechPartnersId) {
        this.valueId = listTechPartnersId;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorsId) {
        this.competitorId = competitorsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechPartnersDTO techPartnersDTO = (TechPartnersDTO) o;
        if (techPartnersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), techPartnersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TechPartnersDTO{" +
            "id=" + getId() +
            ", value=" + getValueId() +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
