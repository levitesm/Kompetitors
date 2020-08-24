<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listPricings">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listPricings.detail.title')">ListPricings</span> {{listPricings.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listPricings.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listPricings.value}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listPricings.id" :to="{name: 'ListPricingsEdit', params: {listPricingsId: listPricings.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-pricings-details.component.ts">
</script>
