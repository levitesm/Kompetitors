package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Dialogs.
 */
@Entity
@Table(name = "dialogs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dialogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "section", nullable = false)
    private String section;

    @NotNull
    @Column(name = "topic", nullable = false)
    private String topic;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "attachment_url")
    private String attachmentURL ;

    @ManyToOne
    @JsonIgnoreProperties("dialogs")
    private Competitors competitors;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public Dialogs section(String section) {
        this.section = section;
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTopic() {
        return topic;
    }

    public Dialogs topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public Dialogs message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public Dialogs author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Dialogs date(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }

    public Competitors getCompetitors() {
        return competitors;
    }

    public Dialogs competitors(Competitors competitors) {
        this.competitors = competitors;
        return this;
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
        if (!(o instanceof Dialogs)) {
            return false;
        }
        return id != null && id.equals(((Dialogs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dialogs{" +
            "id=" + getId() +
            ", section='" + getSection() + "'" +
            ", topic='" + getTopic() + "'" +
            ", message='" + getMessage() + "'" +
            ", author='" + getAuthor() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
