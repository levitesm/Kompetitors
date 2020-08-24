<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listTechPartners">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listTechPartners.detail.title')">ListTechPartners</span> {{listTechPartners.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listTechPartners.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listTechPartners.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.listTechPartners.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="listTechPartners.image">
                            <a v-on:click="openFile(listTechPartners.imageContentType, listTechPartners.image)">
                                <img v-bind:src="'data:' + listTechPartners.imageContentType + ';base64,' + listTechPartners.image" style="max-width: 100%;" alt="listTechPartners image"/>
                            </a>
                            {{listTechPartners.imageContentType}}, {{byteSize(listTechPartners.image)}}
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listTechPartners.id" :to="{name: 'ListTechPartnersEdit', params: {listTechPartnersId: listTechPartners.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-tech-partners-details.component.ts">
</script>
