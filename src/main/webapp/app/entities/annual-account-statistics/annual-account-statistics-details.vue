<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="annualAccountStatistics">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.annualAccountStatistics.detail.title')">AnnualAccountStatistics</span> {{annualAccountStatistics.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccountStatistics.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{annualAccountStatistics.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccountStatistics.year')">Year</span>
                    </dt>
                    <dd>
                        <span>{{annualAccountStatistics.year}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccountStatistics.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{annualAccountStatistics.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccountStatistics.message')">Message</span>
                    </dt>
                    <dd>
                        <span>{{annualAccountStatistics.message}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccountStatistics.modified')">Modified</span>
                    </dt>
                    <dd>
                        <span>{{annualAccountStatistics.modified | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="annualAccountStatistics.id" :to="{name: 'AnnualAccountStatisticsEdit', params: {annualAccountStatisticsId: annualAccountStatistics.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./annual-account-statistics-details.component.ts">
</script>
