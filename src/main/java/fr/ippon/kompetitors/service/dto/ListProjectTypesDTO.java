package fr.ippon.kompetitors.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.ListProjectTypes} entity.
 */
public class ListProjectTypesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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

        ListProjectTypesDTO listProjectTypesDTO = (ListProjectTypesDTO) o;
        if (listProjectTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listProjectTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListProjectTypesDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
