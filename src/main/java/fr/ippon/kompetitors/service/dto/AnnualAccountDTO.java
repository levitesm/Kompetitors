package fr.ippon.kompetitors.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.AnnualAccount} entity.
 */
public class AnnualAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private String siren;

    @NotNull
    private Integer year;

    @NotNull
    private String code;

    private Long value;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnnualAccountDTO annualAccountDTO = (AnnualAccountDTO) o;
        if (annualAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annualAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnnualAccountDTO{" +
            "id=" + getId() +
            ", siren='" + getSiren() + "'" +
            ", year=" + getYear() +
            ", code='" + getCode() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
