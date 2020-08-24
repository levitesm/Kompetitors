package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TechInfo.
 */
@Entity
@Table(name = "tech_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TechInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tech_specialists_number")
    private Integer techSpecialistsNumber;

    @ManyToOne
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTechSpecialistsNumber() {
        return techSpecialistsNumber;
    }

    public TechInfo techSpecialistsNumber(Integer techSpecialistsNumber) {
        this.techSpecialistsNumber = techSpecialistsNumber;
        return this;
    }

    public void setTechSpecialistsNumber(Integer techSpecialistsNumber) {
        this.techSpecialistsNumber = techSpecialistsNumber;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public TechInfo competitor(Competitors competitors) {
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
        if (!(o instanceof TechInfo)) {
            return false;
        }
        return id != null && id.equals(((TechInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TechInfo{" +
            "id=" + getId() +
            ", techSpecialistsNumber=" + getTechSpecialistsNumber() +
            "}";
    }
}
