package fr.ippon.kompetitors.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import fr.ippon.kompetitors.domain.enumeration.EmployeeLevel;
import fr.ippon.kompetitors.domain.enumeration.Currency;
import fr.ippon.kompetitors.domain.enumeration.PaymentType;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.EmployeePricing} entity.
 */
public class EmployeePricingDTO implements Serializable {

    private Long id;

    private EmployeeLevel level;

    private Long price;

    private Currency currency;

    private PaymentType paymentType;

    private Instant modified;


    private Long employeeRoleId;

    private Long competitorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeLevel getLevel() {
        return level;
    }

    public void setLevel(EmployeeLevel level) {
        this.level = level;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public Long getEmployeeRoleId() {
        return employeeRoleId;
    }

    public void setEmployeeRoleId(Long employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorsId) {
        this.competitorId = competitorsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeePricingDTO employeePricingDTO = (EmployeePricingDTO) o;
        if (employeePricingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeePricingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeePricingDTO{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", price=" + getPrice() +
            ", currency='" + getCurrency() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", modified='" + getModified() + "'" +
            ", employeeRole=" + getEmployeeRoleId() +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
