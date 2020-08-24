package fr.ippon.kompetitors.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.AnnualAccountStatistics} entity.
 */
public class AnnualAccountStatisticsDTO implements Serializable {

    private Long id;

    @NotNull
    private String siren;

    @NotNull
    private Integer year;

    private Integer code;

    private String message;

    private Instant modified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = (AnnualAccountStatisticsDTO) o;
        if (annualAccountStatisticsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annualAccountStatisticsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnnualAccountStatisticsDTO{" +
            "id=" + getId() +
            ", siren='" + getSiren() + "'" +
            ", year=" + getYear() +
            ", code=" + getCode() +
            ", message='" + getMessage() + "'" +
            ", modified='" + getModified() + "'" +
            "}";
    }
}
