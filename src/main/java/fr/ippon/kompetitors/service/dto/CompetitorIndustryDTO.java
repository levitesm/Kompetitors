package fr.ippon.kompetitors.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.CompetitorIndustry} entity.
 */
public class CompetitorIndustryDTO implements Serializable {

    private Long id;


    private Long competitorId;

    private Long industryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorsId) {
        this.competitorId = competitorsId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long listIndustriesId) {
        this.industryId = listIndustriesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetitorIndustryDTO competitorIndustryDTO = (CompetitorIndustryDTO) o;
        if (competitorIndustryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitorIndustryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitorIndustryDTO{" +
            "id=" + getId() +
            ", competitor=" + getCompetitorId() +
            ", industry=" + getIndustryId() +
            "}";
    }
}
