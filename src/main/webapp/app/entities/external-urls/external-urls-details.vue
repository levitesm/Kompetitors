<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="externalUrls">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.externalUrls.detail.title')">ExternalUrls</span> {{externalUrls.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.facebookUrl')">Facebook Url</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.facebookUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.twitterUrl')">Twitter Url</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.twitterUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.instagramUrl')">Instagram Url</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.instagramUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.youtubeUrl')">Youtube Url</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.youtubeUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.linkedinUrl')">Linkedin Url</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.linkedinUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.githubUrl')">Github Url</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.githubUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.blogFeed')">Blog Feed</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.blogFeed}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.googleAlertsFeed')">Google Alerts Feed</span>
                    </dt>
                    <dd>
                        <span>{{externalUrls.googleAlertsFeed}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.externalUrls.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="externalUrls.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: externalUrls.competitorId}}">{{externalUrls.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="externalUrls.id" :to="{name: 'ExternalUrlsEdit', params: {externalUrlsId: externalUrls.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./external-urls-details.component.ts">
</script>
