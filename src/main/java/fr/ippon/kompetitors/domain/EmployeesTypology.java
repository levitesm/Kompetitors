package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EmployeesTypology.
 */
@Entity
@Table(name = "employees_typology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeesTypology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private Integer value;

    @Column(name = "year")
    private Integer year;

    @ManyToOne
    private EmployeeType employeeType;

    @ManyToOne
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public EmployeesTypology value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getYear() {
        return year;
    }

    public EmployeesTypology year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public EmployeesTypology employeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public EmployeesTypology competitor(Competitors competitors) {
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
        if (!(o instanceof EmployeesTypology)) {
            return false;
        }
        return id != null && id.equals(((EmployeesTypology) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeesTypology{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", year=" + getYear() +
            "}";
    }
}
