package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Offices.
 */
@Entity
@Table(name = "offices")
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE)
public class Offices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "post")
    private String post;

    @Column(name = "city_as_text")
    private String cityAsText;

    @Column(name = "number_employees")
    private Integer numberEmployees;

    @Column(name = "number_consultants")
    private Integer numberConsultants;

    @Column(name = "number_technicals")
    private Integer numberTechnicals;

    @Column(name = "number_hr")
    private Integer numberHR;

    @Column(name = "number_clients")
    private Integer numberClients;

    @Column(name = "established")
    private LocalDate established;

    @Column(name = "is_main_office")
    private Boolean mainOffice;

    @Column(name = "number_recruiters")
    private Integer numberRecruiters;

    @Column(name = "recruiters_update")
    private LocalDate recruitersUpdate;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value={"offices", "prinfo"})
    private Competitors competitors;

    @OneToMany(mappedBy = "offices", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties(value={"offices"})
    private Set<Clients> clients = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("offices")
    private ListCities city;

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

    public Offices name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Offices address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public Offices phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public Offices post(String post) {
        this.post = post;
        return this;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCityAsText() {
        return cityAsText;
    }

    public Offices cityAsText(String cityAsText) {
        this.cityAsText = cityAsText;
        return this;
    }

    public void setCityAsText(String cityAsText) {
        this.cityAsText = cityAsText;
    }

    public Integer getNumberEmployees() {
        return numberEmployees;
    }

    public Offices numberEmployees(Integer numberEmployees) {
        this.numberEmployees = numberEmployees;
        return this;
    }

    public void setNumberEmployees(Integer numberEmployees) {
        this.numberEmployees = numberEmployees;
    }

    public Integer getNumberConsultants() {
        return numberConsultants;
    }

    public Offices numberConsultants(Integer numberConsultants) {
        this.numberConsultants = numberConsultants;
        return this;
    }

    public void setNumberConsultants(Integer numberConsultants) {
        this.numberConsultants = numberConsultants;
    }

    public Integer getNumberTechnicals() {
        return numberTechnicals;
    }

    public Offices numberTechnicals(Integer numberTechnicals) {
        this.numberTechnicals = numberTechnicals;
        return this;
    }

    public void setNumberTechnicals(Integer numberTechnicals) {
        this.numberTechnicals = numberTechnicals;
    }

    public Integer getNumberHR() {
        return numberHR;
    }

    public Offices numberHR(Integer numberHR) {
        this.numberHR = numberHR;
        return this;
    }

    public void setNumberHR(Integer numberHR) {
        this.numberHR = numberHR;
    }

    public Integer getNumberClients() {
        return numberClients;
    }

    public Offices numberClients(Integer numberClients) {
        this.numberClients = numberClients;
        return this;
    }

    public void setNumberClients(Integer numberClients) {
        this.numberClients = numberClients;
    }

    public LocalDate getEstablished() {
        return established;
    }

    public void setEstablished(LocalDate established) {
        this.established = established;
    }

    public Offices established(LocalDate established) {
        this.established = established;
        return this;
    }

    public Offices mainOffice(Boolean mainOffice) {
        this.mainOffice = mainOffice;
        return this;
    }

    public void setMainOffice(Boolean mainOffice) {
        this.mainOffice = mainOffice;
    }

    public Boolean getMainOffice() {
        return mainOffice;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Offices latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Offices longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Clients> getClients() {
        return clients;
    }

    public Offices clients(Set<Clients> clients) {
        this.clients = clients;
        return this;
    }

    public Offices addClients(Clients clients) {
        this.clients.add(clients);
        clients.setOffices(this);
        return this;
    }

    public Offices removeClients(Clients clients) {
        this.clients.remove(clients);
        clients.setOffices(null);
        return this;
    }

    public Integer getNumberRecruiters() {
        return numberRecruiters;
    }

    public void setNumberRecruiters(Integer numberRecruiters) {
        this.numberRecruiters = numberRecruiters;
    }

    public LocalDate getRecruitersUpdate() {
        return recruitersUpdate;
    }

    public void setRecruitersUpdate(LocalDate recruitersUpdate) {
        this.recruitersUpdate = recruitersUpdate;
    }

    public void setClients(Set<Clients> clients) {
        this.clients = clients;
    }

    public ListCities getCity() {
        return city;
    }

    public Offices city(ListCities listCities) {
        this.city = listCities;
        return this;
    }

    public void setCity(ListCities listCities) {
        this.city = listCities;
    }

    public Competitors getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Competitors competitors) {
        this.competitors = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offices)) {
            return false;
        }
        return id != null && id.equals(((Offices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offices{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", post='" + getPost() + "'" +
            ", cityAsText='" + getCityAsText() + "'" +
            ", numberEmployees=" + getNumberEmployees() +
            ", numberConsultants=" + getNumberConsultants() +
            ", numberTechnicals=" + getNumberTechnicals() +
            ", numberHR=" + getNumberHR() +
            ", numberClients=" + getNumberClients() +
            ", established='" + getEstablished() + "'" +
            ", isMainOffice='" + getMainOffice() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
