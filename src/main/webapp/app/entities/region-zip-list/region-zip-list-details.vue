<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="regionZipList">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.regionZipList.detail.title')">RegionZipList</span> {{regionZipList.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.regionZipList.region')">Region</span>
                    </dt>
                    <dd>
                        <span>{{regionZipList.region}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.regionZipList.zip')">Zip</span>
                    </dt>
                    <dd>
                        <span>{{regionZipList.zip}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="regionZipList.id" :to="{name: 'RegionZipListEdit', params: {regionZipListId: regionZipList.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./region-zip-list-details.component.ts">
</script>
