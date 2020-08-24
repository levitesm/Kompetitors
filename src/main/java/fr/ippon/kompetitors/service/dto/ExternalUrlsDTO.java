package fr.ippon.kompetitors.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.ExternalUrls} entity.
 */
public class ExternalUrlsDTO implements Serializable {

    private Long id;

    private String facebookUrl;

    private String twitterUrl;

    private String instagramUrl;

    private String youtubeUrl;

    private String linkedinUrl;

    private String githubUrl;

    private String blogFeed;

    private String googleAlertsFeed;


    private Long competitorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBlogFeed() {
        return blogFeed;
    }

    public void setBlogFeed(String blogFeed) {
        this.blogFeed = blogFeed;
    }

    public String getGoogleAlertsFeed() {
        return googleAlertsFeed;
    }

    public void setGoogleAlertsFeed(String googleAlertsFeed) {
        this.googleAlertsFeed = googleAlertsFeed;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorsId) {
        this.competitorId = competitorsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExternalUrlsDTO externalUrlsDTO = (ExternalUrlsDTO) o;
        if (externalUrlsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), externalUrlsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExternalUrlsDTO{" +
            "id=" + getId() +
            ", facebookUrl='" + getFacebookUrl() + "'" +
            ", twitterUrl='" + getTwitterUrl() + "'" +
            ", instagramUrl='" + getInstagramUrl() + "'" +
            ", youtubeUrl='" + getYoutubeUrl() + "'" +
            ", linkedinUrl='" + getLinkedinUrl() + "'" +
            ", githubUrl='" + getGithubUrl() + "'" +
            ", blogFeed='" + getBlogFeed() + "'" +
            ", googleAlertsFeed='" + getGoogleAlertsFeed() + "'" +
            ", competitor=" + getCompetitorId() +
            "}";
    }
}
