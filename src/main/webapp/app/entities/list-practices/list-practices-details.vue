<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listPractices">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listPractices.detail.title')">ListPractices</span> {{listPractices.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listPractices.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listPractices.value}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listPractices.id" :to="{name: 'ListPracticesEdit', params: {listPracticesId: listPractices.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-practices-details.component.ts">
</script>
