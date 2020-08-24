<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="prInfo">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.prInfo.detail.title')">PrInfo</span> {{prInfo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.date')">Date</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.date}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.marketingWorkforce')">Marketing Workforce</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.marketingWorkforce}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.marketingBudget')">Marketing Budget</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.marketingBudget}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.experienceFeedback')">Experience Feedback</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.experienceFeedback}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.linkedInSubscribers')">Linked In Subscribers</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.linkedInSubscribers}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.linkedInEngageRate')">Linked In Engage Rate</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.linkedInEngageRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.linkedInPostWeek')">Linked In Post Week</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.linkedInPostWeek}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.linkedInPostDay')">Linked In Post Day</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.linkedInPostDay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.twitterFollowers')">Twitter Followers</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.twitterFollowers}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.twitterPostWeek')">Twitter Post Week</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.twitterPostWeek}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.twitterPostDay')">Twitter Post Day</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.twitterPostDay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.instagramFollowers')">Instagram Followers</span>
                    </dt>
                    <dd>
                        <span>{{prInfo.instagramFollowers}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.prInfo.competitors')">Competitors</span>
                    </dt>
                    <dd>
                        <div v-if="prInfo.competitorsId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: prInfo.competitorsId}}">{{prInfo.competitorsId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="prInfo.id" :to="{name: 'PrInfoEdit', params: {prInfoId: prInfo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./pr-info-details.component.ts">
</script>
