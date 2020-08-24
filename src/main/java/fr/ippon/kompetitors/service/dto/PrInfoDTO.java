package fr.ippon.kompetitors.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.ippon.kompetitors.domain.PrInfo} entity.
 */
public class PrInfoDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Integer marketingWorkforce;

    private Long marketingBudget;

    private Integer experienceFeedback;

    private Long linkedInSubscribers;

    private Float linkedInEngageRate;

    private Integer linkedInPostWeek;

    private Integer linkedInPostDay;

    private Long twitterFollowers;

    private Integer twitterPostWeek;

    private Integer twitterPostDay;

    private Long instagramFollowers;


    private Long competitorsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMarketingWorkforce() {
        return marketingWorkforce;
    }

    public void setMarketingWorkforce(Integer marketingWorkforce) {
        this.marketingWorkforce = marketingWorkforce;
    }

    public Long getMarketingBudget() {
        return marketingBudget;
    }

    public void setMarketingBudget(Long marketingBudget) {
        this.marketingBudget = marketingBudget;
    }

    public Integer getExperienceFeedback() {
        return experienceFeedback;
    }

    public void setExperienceFeedback(Integer experienceFeedback) {
        this.experienceFeedback = experienceFeedback;
    }

    public Long getLinkedInSubscribers() {
        return linkedInSubscribers;
    }

    public void setLinkedInSubscribers(Long linkedInSubscribers) {
        this.linkedInSubscribers = linkedInSubscribers;
    }

    public Float getLinkedInEngageRate() {
        return linkedInEngageRate;
    }

    public void setLinkedInEngageRate(Float linkedInEngageRate) {
        this.linkedInEngageRate = linkedInEngageRate;
    }

    public Integer getLinkedInPostWeek() {
        return linkedInPostWeek;
    }

    public void setLinkedInPostWeek(Integer linkedInPostWeek) {
        this.linkedInPostWeek = linkedInPostWeek;
    }

    public Integer getLinkedInPostDay() {
        return linkedInPostDay;
    }

    public void setLinkedInPostDay(Integer linkedInPostDay) {
        this.linkedInPostDay = linkedInPostDay;
    }

    public Long getTwitterFollowers() {
        return twitterFollowers;
    }

    public void setTwitterFollowers(Long twitterFollowers) {
        this.twitterFollowers = twitterFollowers;
    }

    public Integer getTwitterPostWeek() {
        return twitterPostWeek;
    }

    public void setTwitterPostWeek(Integer twitterPostWeek) {
        this.twitterPostWeek = twitterPostWeek;
    }

    public Integer getTwitterPostDay() {
        return twitterPostDay;
    }

    public void setTwitterPostDay(Integer twitterPostDay) {
        this.twitterPostDay = twitterPostDay;
    }

    public Long getInstagramFollowers() {
        return instagramFollowers;
    }

    public void setInstagramFollowers(Long instagramFollowers) {
        this.instagramFollowers = instagramFollowers;
    }

    public Long getCompetitorsId() {
        return competitorsId;
    }

    public void setCompetitorsId(Long competitorsId) {
        this.competitorsId = competitorsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrInfoDTO prInfoDTO = (PrInfoDTO) o;
        if (prInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrInfoDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", marketingWorkforce=" + getMarketingWorkforce() +
            ", marketingBudget=" + getMarketingBudget() +
            ", experienceFeedback=" + getExperienceFeedback() +
            ", linkedInSubscribers=" + getLinkedInSubscribers() +
            ", linkedInEngageRate=" + getLinkedInEngageRate() +
            ", linkedInPostWeek=" + getLinkedInPostWeek() +
            ", linkedInPostDay=" + getLinkedInPostDay() +
            ", twitterFollowers=" + getTwitterFollowers() +
            ", twitterPostWeek=" + getTwitterPostWeek() +
            ", twitterPostDay=" + getTwitterPostDay() +
            ", instagramFollowers=" + getInstagramFollowers() +
            ", competitors=" + getCompetitorsId() +
            "}";
    }
}
