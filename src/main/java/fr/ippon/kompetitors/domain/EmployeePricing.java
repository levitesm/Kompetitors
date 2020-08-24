package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.Instant;

import fr.ippon.kompetitors.domain.enumeration.EmployeeLevel;

import fr.ippon.kompetitors.domain.enumeration.Currency;

import fr.ippon.kompetitors.domain.enumeration.PaymentType;
import org.hibernate.annotations.Cache;

/**
 * A EmployeePricing.
 */
@Entity
@Table(name = "employee_pricing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeePricing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private EmployeeLevel level;

    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "modified")
    @UpdateTimestamp
    private Instant modified;

    @ManyToOne
    @JsonIgnoreProperties("employeePricings")
    private EmployeeRole employeeRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employeePricings")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeLevel getLevel() {
        return level;
    }

    public EmployeePricing level(EmployeeLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(EmployeeLevel level) {
        this.level = level;
    }

    public Long getPrice() {
        return price;
    }

    public EmployeePricing price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public EmployeePricing currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public EmployeePricing paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Instant getModified() {
        return modified;
    }

    public EmployeePricing modified(Instant modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public EmployeePricing employeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
        return this;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public EmployeePricing competitor(Competitors competitors) {
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
        if (!(o instanceof EmployeePricing)) {
            return false;
        }
        return id != null && id.equals(((EmployeePricing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeePricing{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", price=" + getPrice() +
            ", currency='" + getCurrency() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", modified='" + getModified() + "'" +
            "}";
    }
}
