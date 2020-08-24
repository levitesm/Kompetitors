<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="techServices">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.techServices.detail.title')">TechServices</span> {{techServices.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.techServices.value')">Value</span>
                    </dt>
                    <dd>
                        <div v-if="techServices.valueId">
                            <router-link :to="{name: 'ListServicesView', params: {valueId: techServices.valueId}}">{{techServices.valueId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.techServices.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="techServices.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techServices.competitorId}}">{{techServices.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="techServices.id" :to="{name: 'TechServicesEdit', params: {techServicesId: techServices.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tech-services-details.component.ts">
</script>
