package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GlobalGroups.
 */
@Entity
@Table(name = "global_groups")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GlobalGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "reference")
    private Boolean reference;

    @OneToMany(mappedBy = "globalGroups")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Competitors> competitors = new HashSet<>();

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

    public GlobalGroups name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public GlobalGroups logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public GlobalGroups logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getWebSite() {
        return webSite;
    }

    public GlobalGroups webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Boolean isReference() {
        return reference;
    }

    public GlobalGroups reference(Boolean reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(Boolean reference) {
        this.reference = reference;
    }

    public Set<Competitors> getCompetitors() {
        return competitors;
    }

    public GlobalGroups competitors(Set<Competitors> competitors) {
        this.competitors = competitors;
        return this;
    }

    public GlobalGroups addCompetitors(Competitors competitors) {
        this.competitors.add(competitors);
        competitors.setGlobalGroups(this);
        return this;
    }

    public GlobalGroups removeCompetitors(Competitors competitors) {
        this.competitors.remove(competitors);
        competitors.setGlobalGroups(null);
        return this;
    }

    public void setCompetitors(Set<Competitors> competitors) {
        this.competitors = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GlobalGroups)) {
            return false;
        }
        return id != null && id.equals(((GlobalGroups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GlobalGroups{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", reference='" + isReference() + "'" +
            "}";
    }
}
