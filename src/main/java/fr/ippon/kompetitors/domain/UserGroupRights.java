package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserGroupRights.
 */
@Entity
@Table(name = "user_group_rights")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserGroupRights implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_group_name", nullable = false)
    private String userGroupName;

    @ManyToOne
    @JsonIgnoreProperties("userGroupRights")
    private AccessKey accessKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public UserGroupRights userGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
        return this;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public AccessKey getAccessKey() {
        return accessKey;
    }

    public UserGroupRights accessKey(AccessKey accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public void setAccessKey(AccessKey accessKey) {
        this.accessKey = accessKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroupRights)) {
            return false;
        }
        return id != null && id.equals(((UserGroupRights) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserGroupRights{" +
            "id=" + getId() +
            ", userGroupName='" + getUserGroupName() + "'" +
            "}";
    }
}
