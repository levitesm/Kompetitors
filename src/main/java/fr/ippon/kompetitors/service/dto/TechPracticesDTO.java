package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.TechPractices} entity.
 */
public class TechPracticesDTO implements Serializable {

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

    public void setValueId(Long listPracticesId) {
        this.valueId = listPracticesId;
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

        TechPracticesDTO techPracticesDTO = (TechPracticesDTO) o;
        if (techPracticesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), techPracticesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TechPracticesDTO{" +
            "id=" + getId() +
            ", value=" + getValueId() +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
