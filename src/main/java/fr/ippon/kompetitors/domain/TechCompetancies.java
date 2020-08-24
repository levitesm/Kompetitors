package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TechCompetancies.
 */
@Entity
@Table(name = "tech_competancies")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechCompetancies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("techCompetancies")
    private ListCompetancies value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("techCompetancies")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListCompetancies getValue() {
        return value;
    }

    public TechCompetancies value(ListCompetancies listCompetancies) {
        this.value = listCompetancies;
        return this;
    }

    public void setValue(ListCompetancies listCompetancies) {
        this.value = listCompetancies;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public TechCompetancies competitor(Competitors competitors) {
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
        if (!(o instanceof TechCompetancies)) {
            return false;
        }
        return id != null && id.equals(((TechCompetancies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TechCompetancies{" +
            "id=" + getId() +
            "}";
    }
}
