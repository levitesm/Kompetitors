<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="techCompetancies">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.techCompetancies.detail.title')">TechCompetancies</span> {{techCompetancies.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.techCompetancies.value')">Value</span>
                    </dt>
                    <dd>
                        <div v-if="techCompetancies.valueId">
                            <router-link :to="{name: 'ListCompetanciesView', params: {valueId: techCompetancies.valueId}}">{{techCompetancies.valueId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.techCompetancies.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="techCompetancies.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techCompetancies.competitorId}}">{{techCompetancies.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="techCompetancies.id" :to="{name: 'TechCompetanciesEdit', params: {techCompetanciesId: techCompetancies.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tech-competancies-details.component.ts">
</script>
