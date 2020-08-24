<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listOwnerships">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listOwnerships.detail.title')">ListOwnerships</span> {{listOwnerships.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listOwnerships.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listOwnerships.value}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listOwnerships.id" :to="{name: 'ListOwnershipsEdit', params: {listOwnershipsId: listOwnerships.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-ownerships-details.component.ts">
</script>
