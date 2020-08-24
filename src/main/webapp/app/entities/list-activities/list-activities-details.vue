<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listActivities">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listActivities.detail.title')">ListActivities</span> {{listActivities.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listActivities.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listActivities.value}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listActivities.id" :to="{name: 'ListActivitiesEdit', params: {listActivitiesId: listActivities.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-activities-details.component.ts">
</script>
