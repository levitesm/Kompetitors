package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TechServices.
 */
@Entity
@Table(name = "tech_services")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("techServices")
    private ListServices value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("techServices")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListServices getValue() {
        return value;
    }

    public TechServices value(ListServices listServices) {
        this.value = listServices;
        return this;
    }

    public void setValue(ListServices listServices) {
        this.value = listServices;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public TechServices competitor(Competitors competitors) {
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
        if (!(o instanceof TechServices)) {
            return false;
        }
        return id != null && id.equals(((TechServices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TechServices{" +
            "id=" + getId() +
            "}";
    }
}
