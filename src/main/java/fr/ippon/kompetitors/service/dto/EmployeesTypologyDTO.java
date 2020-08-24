package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.EmployeesTypology} entity.
 */
public class EmployeesTypologyDTO implements Serializable {

    private Long id;

    private Integer value;

    private Integer year;


    private Long employeeTypeId;

    private String employeeTypeName;

    private Long competitorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Long employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorsId) {
        this.competitorId = competitorsId;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeesTypologyDTO employeesTypologyDTO = (EmployeesTypologyDTO) o;
        if (employeesTypologyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeesTypologyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeesTypologyDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", year=" + getYear() +
            ", employeeTypeId=" + getEmployeeTypeId() +
            ", employeeTypeName=" + getEmployeeTypeName() +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
