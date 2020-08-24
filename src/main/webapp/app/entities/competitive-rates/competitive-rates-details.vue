<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitiveRates">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.competitiveRates.detail.title')">CompetitiveRates</span> {{competitiveRates.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.competitiveRates.totalRate')">Total Rate</span>
                    </dt>
                    <dd>
                        <span>{{competitiveRates.totalRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitiveRates.techRate')">Tech Rate</span>
                    </dt>
                    <dd>
                        <span>{{competitiveRates.techRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitiveRates.financeRate')">Finance Rate</span>
                    </dt>
                    <dd>
                        <span>{{competitiveRates.financeRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitiveRates.clientsRate')">Clients Rate</span>
                    </dt>
                    <dd>
                        <span>{{competitiveRates.clientsRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitiveRates.hrRate')">Hr Rate</span>
                    </dt>
                    <dd>
                        <span>{{competitiveRates.hrRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitiveRates.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="competitiveRates.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: competitiveRates.competitor.id}}">{{competitiveRates.competitor.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitiveRates.id" :to="{name: 'CompetitiveRatesEdit', params: {competitiveRatesId: competitiveRates.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competitive-rates-details.component.ts">
</script>
