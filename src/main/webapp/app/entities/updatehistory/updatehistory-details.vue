<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="updatehistory">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.updatehistory.detail.title')">Updatehistory</span> {{updatehistory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.updatehistory.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{updatehistory.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.updatehistory.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{updatehistory.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.updatehistory.date')">Date</span>
                    </dt>
                    <dd>
                        <span>{{updatehistory.date}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.updatehistory.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{updatehistory.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.updatehistory.responce')">Responce</span>
                    </dt>
                    <dd>
                        <span>{{updatehistory.responce}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="updatehistory.id" :to="{name: 'UpdatehistoryEdit', params: {updatehistoryId: updatehistory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./updatehistory-details.component.ts">
</script>
