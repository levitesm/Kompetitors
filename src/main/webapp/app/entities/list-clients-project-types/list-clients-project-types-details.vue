<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listClientsProjectTypes">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listClientsProjectTypes.detail.title')">ListClientsProjectTypes</span> {{listClientsProjectTypes.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listClientsProjectTypes.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listClientsProjectTypes.value}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listClientsProjectTypes.id" :to="{name: 'ListClientsProjectTypesEdit', params: {listClientsProjectTypesId: listClientsProjectTypes.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-clients-project-types-details.component.ts">
</script>
