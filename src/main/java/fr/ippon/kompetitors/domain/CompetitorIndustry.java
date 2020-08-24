package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CompetitorIndustry.
 */
@Entity
@Table(name = "competitor_industry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompetitorIndustry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Competitors competitor;

    @ManyToOne(optional = false)
    @NotNull
    private ListIndustries industry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public CompetitorIndustry competitor(Competitors competitors) {
        this.competitor = competitors;
        return this;
    }

    public void setCompetitor(Competitors competitors) {
        this.competitor = competitors;
    }

    public ListIndustries getIndustry() {
        return industry;
    }

    public CompetitorIndustry industry(ListIndustries listIndustries) {
        this.industry = listIndustries;
        return this;
    }

    public void setIndustry(ListIndustries listIndustries) {
        this.industry = listIndustries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitorIndustry)) {
            return false;
        }
        return id != null && id.equals(((CompetitorIndustry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetitorIndustry{" +
            "id=" + getId() +
            "}";
    }
}
