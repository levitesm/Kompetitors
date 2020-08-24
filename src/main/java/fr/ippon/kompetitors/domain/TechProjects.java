package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TechProjects.
 */
@Entity
@Table(name = "tech_projects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechProjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("techProjects")
    private ListProjectTypes value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("techProjects")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListProjectTypes getValue() {
        return value;
    }

    public TechProjects value(ListProjectTypes listProjectTypes) {
        this.value = listProjectTypes;
        return this;
    }

    public void setValue(ListProjectTypes listProjectTypes) {
        this.value = listProjectTypes;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public TechProjects competitor(Competitors competitors) {
        this.competitor = competitors;
        return this;
    }

    public void setCompetitor(Competitors competitors) {
        this.competitor = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechProjects)) {
            return false;
        }
        return id != null && id.equals(((TechProjects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TechProjects{" +
            "id=" + getId() +
            "}";
    }
}
