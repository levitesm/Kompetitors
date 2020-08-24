package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AnnualAccountStatistics.
 */
@Entity
@Table(name = "annual_account_statistics")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnnualAccountStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "siren", nullable = false)
    private String siren;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "code")
    private Integer code;

    @Column(name = "message")
    private String message;

    @Column(name = "modified")
    private Instant modified;

    public AnnualAccountStatistics() {
    }

    public AnnualAccountStatistics(@NotNull String siren, @NotNull Integer year) {
        this.siren = siren;
        this.year = year;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiren() {
        return siren;
    }

    public AnnualAccountStatistics siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public Integer getYear() {
        return year;
    }

    public AnnualAccountStatistics year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCode() {
        return code;
    }

    public AnnualAccountStatistics code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public AnnualAccountStatistics message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getModified() {
        return modified;
    }

    public AnnualAccountStatistics modified(Instant modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnnualAccountStatistics)) return false;

        AnnualAccountStatistics that = (AnnualAccountStatistics) o;

        if (!getSiren().equals(that.getSiren())) return false;
        return getYear().equals(that.getYear());
    }

    @Override
    public int hashCode() {
        int result = getSiren().hashCode();
        result = 31 * result + getYear().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnnualAccountStatistics{" +
            "id=" + getId() +
            ", siren='" + getSiren() + "'" +
            ", year=" + getYear() +
            ", code=" + getCode() +
            ", message='" + getMessage() + "'" +
            ", modified='" + getModified() + "'" +
            "}";
    }
}
