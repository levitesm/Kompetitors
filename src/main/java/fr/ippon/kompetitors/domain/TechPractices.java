package fr.ippon.kompetitors.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A TechPractices.
 */
@Entity
@Table(name = "tech_practices")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechPractices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("techPractices")
    private ListPractices value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("techPractices")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListPractices getValue() {
        return value;
    }

    public TechPractices value(ListPractices listPractices) {
        this.value = listPractices;
        return this;
    }

    public void setValue(ListPractices listPractices) {
        this.value = listPractices;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public TechPractices competitor(Competitors competitors) {
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
        if (!(o instanceof TechPractices)) {
            return false;
        }
        return id != null && id.equals(((TechPractices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TechPractices{" +
            "id=" + getId() +
            "}";
    }
}
