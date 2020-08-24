package fr.ippon.kompetitors.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ExternalUrls.
 */
@Entity
@Table(name = "external_urls")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalUrls implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "blog_feed")
    private String blogFeed;

    @Column(name = "google_alerts_feed")
    private String googleAlertsFeed;

    @OneToOne
    @JoinColumn(unique = true)
    private Competitors competitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public ExternalUrls facebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
        return this;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public ExternalUrls twitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
        return this;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public ExternalUrls instagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
        return this;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public ExternalUrls youtubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
        return this;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public ExternalUrls linkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
        return this;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public ExternalUrls githubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
        return this;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBlogFeed() {
        return blogFeed;
    }

    public ExternalUrls blogFeed(String blogFeed) {
        this.blogFeed = blogFeed;
        return this;
    }

    public void setBlogFeed(String blogFeed) {
        this.blogFeed = blogFeed;
    }

    public String getGoogleAlertsFeed() {
        return googleAlertsFeed;
    }

    public ExternalUrls googleAlertsFeed(String googleAlertsFeed) {
        this.googleAlertsFeed = googleAlertsFeed;
        return this;
    }

    public void setGoogleAlertsFeed(String googleAlertsFeed) {
        this.googleAlertsFeed = googleAlertsFeed;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    public ExternalUrls competitor(Competitors competitors) {
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
        if (!(o instanceof ExternalUrls)) {
            return false;
        }
        return id != null && id.equals(((ExternalUrls) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExternalUrls{" +
            "id=" + getId() +
            ", facebookUrl='" + getFacebookUrl() + "'" +
            ", twitterUrl='" + getTwitterUrl() + "'" +
            ", instagramUrl='" + getInstagramUrl() + "'" +
            ", youtubeUrl='" + getYoutubeUrl() + "'" +
            ", linkedinUrl='" + getLinkedinUrl() + "'" +
            ", githubUrl='" + getGithubUrl() + "'" +
            ", blogFeed='" + getBlogFeed() + "'" +
            ", googleAlertsFeed='" + getGoogleAlertsFeed() + "'" +
            "}";
    }
}
