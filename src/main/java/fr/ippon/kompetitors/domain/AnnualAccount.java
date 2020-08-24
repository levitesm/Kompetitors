package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AnnualAccount.
 */
@Entity
@Table(name = "annual_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnnualAccount implements Serializable {

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

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "value")
    private Long value;

    public AnnualAccount() {
    }

    public AnnualAccount(@NotNull String siren, @NotNull Integer year, @NotNull String code, Long value) {
        this.siren = siren;
        this.year = year;
        this.code = code;
        this.value = value;
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

    public AnnualAccount siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public Integer getYear() {
        return year;
    }

    public AnnualAccount year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public AnnualAccount code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getValue() {
        return value;
    }

    public AnnualAccount value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnnualAccount)) return false;

        AnnualAccount that = (AnnualAccount) o;

        if (!getSiren().equals(that.getSiren())) return false;
        if (!getYear().equals(that.getYear())) return false;
        if (!getCode().equals(that.getCode())) return false;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        int result = getSiren().hashCode();
        result = 31 * result + getYear().hashCode();
        result = 31 * result + getCode().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnnualAccount{" +
            "id=" + getId() +
            ", siren='" + getSiren() + "'" +
            ", year=" + getYear() +
            ", code='" + getCode() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
