<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="regionList">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.regionList.detail.title')">RegionList</span> {{regionList.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.regionList.region')">Region</span>
                    </dt>
                    <dd>
                        <span>{{regionList.region}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.regionList.country')">Country</span>
                    </dt>
                    <dd>
                        <span>{{regionList.country}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="regionList.id" :to="{name: 'RegionListEdit', params: {regionListId: regionList.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./region-list-details.component.ts">
</script>
