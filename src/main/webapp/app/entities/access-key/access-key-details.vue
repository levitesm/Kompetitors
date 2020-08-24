<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="accessKey">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.accessKey.detail.title')">AccessKey</span> {{accessKey.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.accessKey.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{accessKey.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.accessKey.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{accessKey.description}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="accessKey.id" :to="{name: 'AccessKeyEdit', params: {accessKeyId: accessKey.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./access-key-details.component.ts">
</script>
