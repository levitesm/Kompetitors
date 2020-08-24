package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeSalaries.
 */
@Entity
@Table(name = "employee_salaries")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeeSalaries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "seniority")
    private String seniority;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties("salaries")
    private Competitors competitor;

    public Competitors getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitors competitor) {
        this.competitor = competitor;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeniority() {
        return seniority;
    }

    public EmployeeSalaries seniority(String seniority) {
        this.seniority = seniority;
        return this;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public Double getSalary() {
        return salary;
    }

    public EmployeeSalaries salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public EmployeeSalaries updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public EmployeeSalaries updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeSalaries)) {
            return false;
        }
        return id != null && id.equals(((EmployeeSalaries) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeSalaries{" +
            "id=" + getId() +
            ", seniority='" + getSeniority() + "'" +
            ", salary=" + getSalary() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
