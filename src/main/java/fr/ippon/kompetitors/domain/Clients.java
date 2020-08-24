package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "since")
    private String since;

    @Column(name = "is_ippon")
    private Boolean isIppon;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @OneToMany(mappedBy = "clients", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    private Set<ClientsProjects> projects = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clients")
    private ListIndustries industry;

    @ManyToOne
    @JsonIgnoreProperties("clients")
    private Offices offices;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Clients name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSince() {
        return since;
    }

    public Clients since(String since) {
        this.since = since;
        return this;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public Boolean isIsIppon() {
        return isIppon;
    }

    public Clients isIppon(Boolean isIppon) {
        this.isIppon = isIppon;
        return this;
    }

    public void setIsIppon(Boolean isIppon) {
        this.isIppon = isIppon;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Clients updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Set<ClientsProjects> getProjects() {
        return projects;
    }

    public Clients projects(Set<ClientsProjects> clientsProjects) {
        this.projects = clientsProjects;
        return this;
    }

    public Clients addProjects(ClientsProjects clientsProjects) {
        this.projects.add(clientsProjects);
        clientsProjects.setClients(this);
        return this;
    }

    public Clients removeProjects(ClientsProjects clientsProjects) {
        this.projects.remove(clientsProjects);
        clientsProjects.setClients(null);
        return this;
    }

    public void setProjects(Set<ClientsProjects> clientsProjects) {
        this.projects = clientsProjects;
    }

    public ListIndustries getIndustry() {
        return industry;
    }

    public Clients industry(ListIndustries listIndustries) {
        this.industry = listIndustries;
        return this;
    }

    public void setIndustry(ListIndustries listIndustries) {
        this.industry = listIndustries;
    }

    public Offices getOffices() {
        return offices;
    }

    public Clients offices(Offices offices) {
        this.offices = offices;
        return this;
    }

    public void setOffices(Offices offices) {
        this.offices = offices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clients)) {
            return false;
        }
        return id != null && id.equals(((Clients) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Clients{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", since='" + getSince() + "'" +
            ", isIppon='" + isIsIppon() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
