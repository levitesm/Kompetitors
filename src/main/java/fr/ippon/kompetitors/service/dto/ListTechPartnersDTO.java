package fr.ippon.kompetitors.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.ListTechPartners} entity.
 */
public class ListTechPartnersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String value;

    @Lob
    private byte[] image;

    private String imageContentType;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ListTechPartnersDTO listTechPartnersDTO = (ListTechPartnersDTO) o;
        if (listTechPartnersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listTechPartnersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListTechPartnersDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
