<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="workforce">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.workforce.detail.title')">Workforce</span> {{workforce.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.workforce.employeeNumber')">Employee Number</span>
                    </dt>
                    <dd>
                        <span>{{workforce.employeeNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.workforce.year')">Year</span>
                    </dt>
                    <dd>
                        <span>{{workforce.year}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.workforce.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="workforce.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: workforce.competitor.id}}">{{workforce.competitor.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="workforce.id" :to="{name: 'WorkforceEdit', params: {workforceId: workforce.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./workforce-details.component.ts">
</script>
