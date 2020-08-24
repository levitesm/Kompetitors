package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CompetitiveRates.
 */
@Entity
@Table(name = "competitive_rates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompetitiveRates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_rate")
    private Double totalRate = 0D;

    @Column(name = "tech_rate")
    private Double techRate = 0D;

    @Column(name = "finance_rate")
    private Double financeRate = 0D;

    @Column(name = "clients_rate")
    private Double clientsRate = 0D;

    @Column(name = "hr_rate")
    private Double hrRate = 0D;

    @ManyToOne
    @JoinColumn(unique = true)
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalRate() {
        return totalRate;
    }

    public CompetitiveRates totalRate(Double totalRate) {
        this.totalRate = totalRate;
        return this;
    }

    public void setTotalRate(Double totalRate) {
        this.totalRate = totalRate;
    }

    public Double getTechRate() {
        return techRate;
    }

    public CompetitiveRates techRate(Double techRate) {
        this.techRate = techRate;
        return this;
    }

    public void setTechRate(Double techRate) {
        this.techRate = techRate;
    }

    public Double getFinanceRate() {
        return financeRate;
    }

    public CompetitiveRates financeRate(Double financeRate) {
        this.financeRate = financeRate;
        return this;
    }

    public void setFinanceRate(Double financeRate) {
        this.financeRate = financeRate;
    }

    public Double getClientsRate() {
        return clientsRate;
    }

    public CompetitiveRates clientsRate(Double clientsRate) {
        this.clientsRate = clientsRate;
        return this;
    }

    public void setClientsRate(Double clientsRate) {
        this.clientsRate = clientsRate;
    }

    public Double getHrRate() {
        return hrRate;
    }

    public CompetitiveRates hrRate(Double hrRate) {
        this.hrRate = hrRate;
        return this;
    }

    public void setHrRate(Double hrRate) {
        this.hrRate = hrRate;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public CompetitiveRates competitor(Competitors competitors) {
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
        if (!(o instanceof CompetitiveRates)) {
            return false;
        }
        return id != null && id.equals(((CompetitiveRates) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetitiveRates{" +
            "id=" + getId() +
            ", totalRate=" + getTotalRate() +
            ", techRate=" + getTechRate() +
            ", financeRate=" + getFinanceRate() +
            ", clientsRate=" + getClientsRate() +
            ", hrRate=" + getHrRate() +
            "}";
    }
}
