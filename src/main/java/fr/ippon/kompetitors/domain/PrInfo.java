package fr.ippon.kompetitors.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PrInfo.
 */
@Entity
@Table(name = "pr_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "marketing_workforce")
    private Integer marketingWorkforce;

    @Column(name = "marketing_budget")
    private Long marketingBudget;

    @Column(name = "experience_feedback")
    private Integer experienceFeedback;

    @Column(name = "linked_in_subscribers")
    private Long linkedInSubscribers;

    @Column(name = "linked_in_engage_rate")
    private Float linkedInEngageRate;

    @Column(name = "linked_in_post_week")
    private Integer linkedInPostWeek;

    @Column(name = "linked_in_post_day")
    private Integer linkedInPostDay;

    @Column(name = "twitter_followers")
    private Long twitterFollowers;

    @Column(name = "twitter_post_week")
    private Integer twitterPostWeek;

    @Column(name = "twitter_post_day")
    private Integer twitterPostDay;

    @Column(name = "instagram_followers")
    private Long instagramFollowers;

    @ManyToOne
    @JsonIgnoreProperties("prs")
    private Competitors competitors;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public PrInfo date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMarketingWorkforce() {
        return marketingWorkforce;
    }

    public PrInfo marketingWorkforce(Integer marketingWorkforce) {
        this.marketingWorkforce = marketingWorkforce;
        return this;
    }

    public void setMarketingWorkforce(Integer marketingWorkforce) {
        this.marketingWorkforce = marketingWorkforce;
    }

    public Long getMarketingBudget() {
        return marketingBudget;
    }

    public PrInfo marketingBudget(Long marketingBudget) {
        this.marketingBudget = marketingBudget;
        return this;
    }

    public void setMarketingBudget(Long marketingBudget) {
        this.marketingBudget = marketingBudget;
    }

    public Integer getExperienceFeedback() {
        return experienceFeedback;
    }

    public PrInfo experienceFeedback(Integer experienceFeedback) {
        this.experienceFeedback = experienceFeedback;
        return this;
    }

    public void setExperienceFeedback(Integer experienceFeedback) {
        this.experienceFeedback = experienceFeedback;
    }

    public Long getLinkedInSubscribers() {
        return linkedInSubscribers;
    }

    public PrInfo linkedInSubscribers(Long linkedInSubscribers) {
        this.linkedInSubscribers = linkedInSubscribers;
        return this;
    }

    public void setLinkedInSubscribers(Long linkedInSubscribers) {
        this.linkedInSubscribers = linkedInSubscribers;
    }

    public Float getLinkedInEngageRate() {
        return linkedInEngageRate;
    }

    public PrInfo linkedInEngageRate(Float linkedInEngageRate) {
        this.linkedInEngageRate = linkedInEngageRate;
        return this;
    }

    public void setLinkedInEngageRate(Float linkedInEngageRate) {
        this.linkedInEngageRate = linkedInEngageRate;
    }

    public Integer getLinkedInPostWeek() {
        return linkedInPostWeek;
    }

    public PrInfo linkedInPostWeek(Integer linkedInPostWeek) {
        this.linkedInPostWeek = linkedInPostWeek;
        return this;
    }

    public void setLinkedInPostWeek(Integer linkedInPostWeek) {
        this.linkedInPostWeek = linkedInPostWeek;
    }

    public Integer getLinkedInPostDay() {
        return linkedInPostDay;
    }

    public PrInfo linkedInPostDay(Integer linkedInPostDay) {
        this.linkedInPostDay = linkedInPostDay;
        return this;
    }

    public void setLinkedInPostDay(Integer linkedInPostDay) {
        this.linkedInPostDay = linkedInPostDay;
    }

    public Long getTwitterFollowers() {
        return twitterFollowers;
    }

    public PrInfo twitterFollowers(Long twitterFollowers) {
        this.twitterFollowers = twitterFollowers;
        return this;
    }

    public void setTwitterFollowers(Long twitterFollowers) {
        this.twitterFollowers = twitterFollowers;
    }

    public Integer getTwitterPostWeek() {
        return twitterPostWeek;
    }

    public PrInfo twitterPostWeek(Integer twitterPostWeek) {
        this.twitterPostWeek = twitterPostWeek;
        return this;
    }

    public void setTwitterPostWeek(Integer twitterPostWeek) {
        this.twitterPostWeek = twitterPostWeek;
    }

    public Integer getTwitterPostDay() {
        return twitterPostDay;
    }

    public PrInfo twitterPostDay(Integer twitterPostDay) {
        this.twitterPostDay = twitterPostDay;
        return this;
    }

    public void setTwitterPostDay(Integer twitterPostDay) {
        this.twitterPostDay = twitterPostDay;
    }

    public Long getInstagramFollowers() {
        return instagramFollowers;
    }

    public PrInfo instagramFollowers(Long instagramFollowers) {
        this.instagramFollowers = instagramFollowers;
        return this;
    }

    public void setInstagramFollowers(Long instagramFollowers) {
        this.instagramFollowers = instagramFollowers;
    }

    public Competitors getCompetitors() {
        return competitors;
    }

    public PrInfo competitors(Competitors competitors) {
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
        if (!(o instanceof PrInfo)) {
            return false;
        }
        return id != null && id.equals(((PrInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrInfo{" +
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
            "}";
    }
}
