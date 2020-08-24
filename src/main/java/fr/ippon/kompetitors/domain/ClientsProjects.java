package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ClientsProjects.
 */
@Entity
@Table(name = "clients_projects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientsProjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("clientsProjects")
    private ListClientsProjectTypes projectType;

    @ManyToOne
    @JsonIgnoreProperties(value = {"projects", "offices", "clients"})
    private Clients clients;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public ClientsProjects status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListClientsProjectTypes getProjectType() {
        return projectType;
    }

    public ClientsProjects projectType(ListClientsProjectTypes listClientsProjectTypes) {
        this.projectType = listClientsProjectTypes;
        return this;
    }

    public void setProjectType(ListClientsProjectTypes listClientsProjectTypes) {
        this.projectType = listClientsProjectTypes;
    }

    public Clients getClients() {
        return clients;
    }

    public ClientsProjects clients(Clients clients) {
        this.clients = clients;
        return this;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientsProjects)) {
            return false;
        }
        return id != null && id.equals(((ClientsProjects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClientsProjects{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
