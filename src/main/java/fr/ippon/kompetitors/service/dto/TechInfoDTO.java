package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.TechInfo} entity.
 */
public class TechInfoDTO implements Serializable {

    private Long id;

    private Integer techSpecialistsNumber;


    private Long competitorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTechSpecialistsNumber() {
        return techSpecialistsNumber;
    }

    public void setTechSpecialistsNumber(Integer techSpecialistsNumber) {
        this.techSpecialistsNumber = techSpecialistsNumber;
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

        TechInfoDTO techInfoDTO = (TechInfoDTO) o;
        if (techInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), techInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TechInfoDTO{" +
            "id=" + getId() +
            ", techSpecialistsNumber=" + getTechSpecialistsNumber() +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
