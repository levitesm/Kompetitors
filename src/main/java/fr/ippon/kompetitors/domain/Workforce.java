package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Workforce.
 */
@Entity
@Table(name = "workforce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workforce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "employee_number")
    private Integer employeeNumber;

    @Column(name = "year")
    private LocalDate year;

    @ManyToOne(optional = false)
    @NotNull
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public Workforce employeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public LocalDate getYear() {
        return year;
    }

    public Workforce year(LocalDate year) {
        this.year = year;
        return this;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public Workforce competitor(Competitors competitors) {
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
        if (!(o instanceof Workforce)) {
            return false;
        }
        return id != null && id.equals(((Workforce) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Workforce{" +
            "id=" + getId() +
            ", employeeNumber=" + getEmployeeNumber() +
            ", year='" + getYear() + "'" +
            "}";
    }
}
