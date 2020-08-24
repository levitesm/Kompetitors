package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TechPartners.
 */
@Entity
@Table(name = "tech_partners")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechPartners implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("techPartners")
    private ListTechPartners value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("techPartners")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListTechPartners getValue() {
        return value;
    }

    public TechPartners value(ListTechPartners listTechPartners) {
        this.value = listTechPartners;
        return this;
    }

    public void setValue(ListTechPartners listTechPartners) {
        this.value = listTechPartners;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public TechPartners competitor(Competitors competitors) {
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
        if (!(o instanceof TechPartners)) {
            return false;
        }
        return id != null && id.equals(((TechPartners) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TechPartners{" +
            "id=" + getId() +
            "}";
    }
}
