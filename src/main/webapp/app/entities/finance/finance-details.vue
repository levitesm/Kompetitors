<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="finance">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.finance.detail.title')">Finance</span> {{finance.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.finance.margin')">Margin</span>
                    </dt>
                    <dd>
                        <span>{{finance.margin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.finance.ebitda')">Ebitda</span>
                    </dt>
                    <dd>
                        <span>{{finance.ebitda}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.finance.occupationRate')">Occupation Rate</span>
                    </dt>
                    <dd>
                        <span>{{finance.occupationRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.finance.revenue')">Revenue</span>
                    </dt>
                    <dd>
                        <span>{{finance.revenue}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.finance.year')">Year</span>
                    </dt>
                    <dd>
                        <span>{{finance.year}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.finance.competitors')">Competitors</span>
                    </dt>
                    <dd>
                        <div v-if="finance.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: finance.competitors.id}}">{{finance.competitors.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="finance.id" :to="{name: 'FinanceEdit', params: {financeId: finance.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./finance-details.component.ts">
</script>
