<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="clients">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.clients.detail.title')">Clients</span> {{clients.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.officeName')">Office Name</span>
                    </dt>
                    <dd>
                        <span>{{clients.officeName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{clients.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.since')">Since</span>
                    </dt>
                    <dd>
                        <span>{{clients.since}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.isIppon')">Is Ippon</span>
                    </dt>
                    <dd>
                        <span>{{clients.isIppon}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.updateDate')">Update Date</span>
                    </dt>
                    <dd>
                        <span>{{clients.updateDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.industry')">Industry</span>
                    </dt>
                    <dd>
                        <div v-if="clients.industry">
                            <router-link :to="{name: 'ListIndustriesView', params: {industryId: clients.industry.id}}">{{clients.industry.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clients.offices')">Offices</span>
                    </dt>
                    <dd>
                        <div v-if="clients.offices">
                            <router-link :to="{name: 'OfficesView', params: {officesId: clients.offices.id}}">{{clients.offices.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="clients.id" :to="{name: 'ClientsEdit', params: {clientsId: clients.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./clients-details.component.ts">
</script>
