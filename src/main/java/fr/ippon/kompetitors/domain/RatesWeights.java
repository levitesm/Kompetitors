package fr.ippon.kompetitors.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A CompetitiveRates.
 */
@Entity
@Table(name = "rates_weights")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RatesWeights implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "finance_weight")
    private Double financeWeight = 0D;

    @Column(name = "clients_weight")
    private Double clientsWeight = 0D;

    @Column(name = "technologies_weight")
    private Double technologiesWeight = 0D;

    @Column(name = "hr_weight")
    private Double hrWeight = 0D;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinanceWeight() {
        return financeWeight;
    }

    public void setFinanceWeight(Double financeWeight) {
        this.financeWeight = financeWeight;
    }

    public Double getClientsWeight() {
        return clientsWeight;
    }

    public void setClientsWeight(Double clientsWeight) {
        this.clientsWeight = clientsWeight;
    }

    public Double getTechnologiesWeight() {
        return technologiesWeight;
    }

    public void setTechnologiesWeight(Double technologiesWeight) {
        this.technologiesWeight = technologiesWeight;
    }

    public Double getHrWeight() {
        return hrWeight;
    }

    public void setHrWeight(Double hrWeight) {
        this.hrWeight = hrWeight;
    }
}
