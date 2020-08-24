package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DashboardFinance.
 */
@Entity
@Table(name = "dashboard_finance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DashboardFinance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "gross_sales")
    private Long grossSales;

    @Column(name = "gross_sales_growth")
    private Double grossSalesGrowth;

    @Column(name = "gross_sales_per_employee")
    private Long grossSalesPerEmployee;

    @Column(name = "ebit")
    private Long ebit;

    @Column(name = "net_result")
    private Long netResult;

    @Column(name = "net_result_growth")
    private Double netResultGrowth;

    @Column(name = "workforce")
    private Long workforce;

    @Column(name = "year")
    private Integer year;

    @Column(name = "gross_sales_per_consultant")
    private Long grossSalesPerConsultant;

    @Column(name = "average_pay")
    private Long averagePay;

    @Column(name = "net_result_percent")
    private Integer netResultPercent;

    @ManyToOne (fetch = FetchType.LAZY)
    @JsonIgnoreProperties("dashboardFinances")
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrossSales() {
        return grossSales;
    }

    public DashboardFinance grossSales(Long grossSales) {
        this.grossSales = grossSales;
        return this;
    }

    public void setGrossSales(Long grossSales) {
        this.grossSales = grossSales;
    }

    public Long getGrossSalesPerEmployee() {
        return grossSalesPerEmployee;
    }

    public DashboardFinance grossSalesPerEmployee(Long grossSalesPerEmployee) {
        this.grossSalesPerEmployee = grossSalesPerEmployee;
        return this;
    }

    public void setGrossSalesPerEmployee(Long grossSalesPerEmployee) {
        this.grossSalesPerEmployee = grossSalesPerEmployee;
    }

    public Long getEbit() {
        return ebit;
    }

    public DashboardFinance ebit(Long ebit) {
        this.ebit = ebit;
        return this;
    }

    public void setEbit(Long ebit) {
        this.ebit = ebit;
    }

    public Long getNetResult() {
        return netResult;
    }

    public DashboardFinance netResult(Long netResult) {
        this.netResult = netResult;
        return this;
    }

    public void setNetResult(Long netResult) {
        this.netResult = netResult;
    }

    public Long getWorkforce() {
        return workforce;
    }

    public DashboardFinance workforce(Long workforce) {
        this.workforce = workforce;
        return this;
    }

    public void setWorkforce(Long workforce) {
        this.workforce = workforce;
    }

    public Integer getYear() {
        return year;
    }

    public DashboardFinance year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getGrossSalesPerConsultant() {
        return grossSalesPerConsultant;
    }

    public DashboardFinance grossSalesPerConsultant(Long grossSalesPerConsultant) {
        this.grossSalesPerConsultant = grossSalesPerConsultant;
        return this;
    }

    public void setGrossSalesPerConsultant(Long grossSalesPerConsultant) {
        this.grossSalesPerConsultant = grossSalesPerConsultant;
    }

    public Long getAveragePay() {
        return averagePay;
    }

    public DashboardFinance averagePay(Long averagePay) {
        this.averagePay = averagePay;
        return this;
    }

    public void setAveragePay(Long averagePay) {
        this.averagePay = averagePay;
    }

    public Integer getNetResultPercent() {
        return netResultPercent;
    }

    public DashboardFinance netResultPercent(Integer netResultPercent) {
        this.netResultPercent = netResultPercent;
        return this;
    }

    public Double getGrossSalesGrowth() {
        return grossSalesGrowth;
    }

    public void setGrossSalesGrowth(Double grossSalesGrowth) {
        this.grossSalesGrowth = grossSalesGrowth;
    }

    public Double getNetResultGrowth() {
        return netResultGrowth;
    }

    public void setNetResultGrowth(Double netResultGrowth) {
        this.netResultGrowth = netResultGrowth;
    }

    public void setNetResultPercent(Integer netResultPercent) {
        this.netResultPercent = netResultPercent;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public DashboardFinance competitor(Competitors competitors) {
        this.competitor = competitors;
        return this;
    }

    public void setCompetitor(Competitors competitors) {
        this.competitor = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Long getCompetitorId() {
      return competitor != null ? competitor.getId() : null;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardFinance)) {
            return false;
        }
        return id != null && id.equals(((DashboardFinance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DashboardFinance{" +
            "id=" + getId() +
            ", grossSales=" + getGrossSales() +
            ", grossSalesPerEmployee=" + getGrossSalesPerEmployee() +
            ", ebit=" + getEbit() +
            ", netResult=" + getNetResult() +
            ", workforce=" + getWorkforce() +
            ", year=" + getYear() +
            ", grossSalesPerConsultant=" + getGrossSalesPerConsultant() +
            ", averagePay=" + getAveragePay() +
            ", netResultPercent=" + getNetResultPercent() +
            "}";
    }
}
