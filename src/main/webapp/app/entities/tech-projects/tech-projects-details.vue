<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="techProjects">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.techProjects.detail.title')">TechProjects</span> {{techProjects.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.techProjects.value')">Value</span>
                    </dt>
                    <dd>
                        <div v-if="techProjects.valueId">
                            <router-link :to="{name: 'ListProjectTypesView', params: {valueId: techProjects.valueId}}">{{techProjects.valueId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.techProjects.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="techProjects.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techProjects.competitorId}}">{{techProjects.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="techProjects.id" :to="{name: 'TechProjectsEdit', params: {techProjectsId: techProjects.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tech-projects-details.component.ts">
</script>
