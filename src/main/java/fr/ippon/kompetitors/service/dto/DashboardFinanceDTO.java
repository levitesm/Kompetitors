package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.DashboardFinance} entity.
 */
public class DashboardFinanceDTO implements Serializable {

    private Long id;

    private Long grossSales;

    private Double grossSalesGrowth;

    private Long grossSalesPerEmployee;

    private Long ebit;

    private Long netResult;

    private Double netResultGrowth;

    private Long workforce;

    private Integer year;

    private Long grossSalesPerConsultant;

    private Long averagePay;

    private Integer netResultPercent;


    private Long competitorId;

    private String competitorName;

    private byte[] groupLogo;

    private String groupLogoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(Long grossSales) {
        this.grossSales = grossSales;
    }

    public Long getGrossSalesPerEmployee() {
        return grossSalesPerEmployee;
    }

    public void setGrossSalesPerEmployee(Long grossSalesPerEmployee) {
        this.grossSalesPerEmployee = grossSalesPerEmployee;
    }

    public Long getEbit() {
        return ebit;
    }

    public void setEbit(Long ebit) {
        this.ebit = ebit;
    }

    public Long getNetResult() {
        return netResult;
    }

    public void setNetResult(Long netResult) {
        this.netResult = netResult;
    }

    public Long getWorkforce() {
        return workforce;
    }

    public void setWorkforce(Long workforce) {
        this.workforce = workforce;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getGrossSalesPerConsultant() {
        return grossSalesPerConsultant;
    }

    public void setGrossSalesPerConsultant(Long grossSalesPerConsultant) {
        this.grossSalesPerConsultant = grossSalesPerConsultant;
    }

    public Long getAveragePay() {
        return averagePay;
    }

    public void setAveragePay(Long averagePay) {
        this.averagePay = averagePay;
    }

    public Integer getNetResultPercent() {
        return netResultPercent;
    }

    public void setNetResultPercent(Integer netResultPercent) {
        this.netResultPercent = netResultPercent;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorsId) {
        this.competitorId = competitorsId;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public byte[] getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(byte[] groupLogo) {
        this.groupLogo = groupLogo;
    }

    public String getGroupLogoContentType() {
        return groupLogoContentType;
    }

    public void setGroupLogoContentType(String groupLogoContentType) {
        this.groupLogoContentType = groupLogoContentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DashboardFinanceDTO dashboardFinanceDTO = (DashboardFinanceDTO) o;
        if (dashboardFinanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dashboardFinanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DashboardFinanceDTO{" +
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
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
