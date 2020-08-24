<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="techPractices">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.techPractices.detail.title')">TechPractices</span> {{techPractices.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.techPractices.value')">Value</span>
                    </dt>
                    <dd>
                        <div v-if="techPractices.valueId">
                            <router-link :to="{name: 'ListPracticesView', params: {valueId: techPractices.valueId}}">{{techPractices.valueId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.techPractices.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="techPractices.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techPractices.competitorId}}">{{techPractices.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="techPractices.id" :to="{name: 'TechPracticesEdit', params: {techPracticesId: techPractices.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tech-practices-details.component.ts">
</script>
