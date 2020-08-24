package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.TechCompetancies} entity.
 */
public class TechCompetanciesDTO implements Serializable {

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

    public void setValueId(Long listCompetanciesId) {
        this.valueId = listCompetanciesId;
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

        TechCompetanciesDTO techCompetanciesDTO = (TechCompetanciesDTO) o;
        if (techCompetanciesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), techCompetanciesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TechCompetanciesDTO{" +
            "id=" + getId() +
            ", value=" + getValueId() +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
