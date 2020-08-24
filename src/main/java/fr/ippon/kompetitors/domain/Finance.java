package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Finance.
 */
@Entity
@Table(name = "finance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Finance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "margin")
    private Double margin;

    @Column(name = "ebitda")
    private Double ebitda;

    @Column(name = "occupation_rate")
    private Double occupationRate;

    @Column(name = "revenue")
    private Double revenue;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @ManyToOne
    @JsonIgnoreProperties("finances")
    private Competitors competitors;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMargin() {
        return margin;
    }

    public Finance margin(Double margin) {
        this.margin = margin;
        return this;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Double getEbitda() {
        return ebitda;
    }

    public Finance ebitda(Double ebitda) {
        this.ebitda = ebitda;
        return this;
    }

    public void setEbitda(Double ebitda) {
        this.ebitda = ebitda;
    }

    public Double getOccupationRate() {
        return occupationRate;
    }

    public Finance occupationRate(Double occupationRate) {
        this.occupationRate = occupationRate;
        return this;
    }

    public void setOccupationRate(Double occupationRate) {
        this.occupationRate = occupationRate;
    }

    public Double getRevenue() {
        return revenue;
    }

    public Finance revenue(Double revenue) {
        this.revenue = revenue;
        return this;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Integer getYear() {
        return year;
    }

    public Finance year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Competitors getCompetitors() {
        return competitors;
    }

    public Finance competitors(Competitors competitors) {
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
        if (!(o instanceof Finance)) {
            return false;
        }
        return id != null && id.equals(((Finance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Finance{" +
            "id=" + getId() +
            ", margin=" + getMargin() +
            ", ebitda=" + getEbitda() +
            ", occupationRate=" + getOccupationRate() +
            ", revenue=" + getRevenue() +
            ", year=" + getYear() +
            "}";
    }
}
