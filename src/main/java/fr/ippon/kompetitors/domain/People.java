package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A People.
 */
@Entity
@Table(name = "people")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class People implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "f_name", nullable = false)
    private String fName;

    @NotNull
    @Column(name = "l_name", nullable = false)
    private String lName;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "linked_page")
    private String linkedPage;

    @Column(name = "is_key")
    private Boolean isKey;

    @Column(name = "specific_office")
    private Long specificOffice;

    @ManyToOne
    @JsonIgnoreProperties(value={"societeMain", "offices", "finances", "prinfo", "hr", "people", "dialogs", "legal", "infogreffe" })
    private Competitors competitors;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public People fName(String fName) {
        this.fName = fName;
        return this;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public People lName(String lName) {
        this.lName = lName;
        return this;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getTitle() {
        return title;
    }

    public People title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkedPage() {
        return linkedPage;
    }

    public People linkedPage(String linkedPage) {
        this.linkedPage = linkedPage;
        return this;
    }

    public void setLinkedPage(String linkedPage) {
        this.linkedPage = linkedPage;
    }

    public Boolean isIsKey() {
        return isKey;
    }

    public People isKey(Boolean isKey) {
        this.isKey = isKey;
        return this;
    }

    public Boolean getKey() {
        return isKey;
    }

    public void setKey(Boolean key) {
        isKey = key;
    }

    public Long getSpecificOffice() {
        return specificOffice;
    }

    public void setSpecificOffice(Long specificOffice) {
        this.specificOffice = specificOffice;
    }

    public void setIsKey(Boolean isKey) {
        this.isKey = isKey;
    }

    public Competitors getCompetitors() {
        return competitors;
    }

    public People competitors(Competitors competitors) {
        this.competitors = competitors;
        return this;
    }

    public void setCompetitors(Competitors competitors) {
        this.competitors = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof People)) {
            return false;
        }
        return id != null && id.equals(((People) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "People{" +
            "id=" + getId() +
            ", fName='" + getfName() + "'" +
            ", lName='" + getlName() + "'" +
            ", title='" + getTitle() + "'" +
            ", linkedPage='" + getLinkedPage() + "'" +
            ", isKey='" + isIsKey() + "'" +
            "}";
    }
}
