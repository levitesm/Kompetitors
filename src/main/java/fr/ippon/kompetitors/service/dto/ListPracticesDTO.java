package fr.ippon.kompetitors.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.ListPractices} entity.
 */
public class ListPracticesDTO implements Serializable {

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

        ListPracticesDTO listPracticesDTO = (ListPracticesDTO) o;
        if (listPracticesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listPracticesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListPracticesDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
