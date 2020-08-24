package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Updatehistory.
 */
@Entity
@Table(name = "updatehistory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Updatehistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "siren")
    private String siren;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private String status;

    @Column(name = "responce")
    private String responce;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Updatehistory type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSiren() {
        return siren;
    }

    public Updatehistory siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public LocalDate getDate() {
        return date;
    }

    public Updatehistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public Updatehistory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponce() {
        return responce;
    }

    public Updatehistory responce(String responce) {
        this.responce = responce;
        return this;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Updatehistory)) {
            return false;
        }
        return id != null && id.equals(((Updatehistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Updatehistory{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", siren='" + getSiren() + "'" +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", responce='" + getResponce() + "'" +
            "}";
    }
}
