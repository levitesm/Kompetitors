<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitorIndustry">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.competitorIndustry.detail.title')">CompetitorIndustry</span> {{competitorIndustry.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.competitorIndustry.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="competitorIndustry.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: competitorIndustry.competitorId}}">{{competitorIndustry.competitorId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitorIndustry.industry')">Industry</span>
                    </dt>
                    <dd>
                        <div v-if="competitorIndustry.industryId">
                            <router-link :to="{name: 'ListIndustriesView', params: {industryId: competitorIndustry.industryId}}">{{competitorIndustry.industryId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitorIndustry.id" :to="{name: 'CompetitorIndustryEdit', params: {competitorIndustryId: competitorIndustry.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competitor-industry-details.component.ts">
</script>
