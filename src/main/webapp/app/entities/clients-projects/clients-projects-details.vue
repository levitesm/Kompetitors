<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="clientsProjects">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.clientsProjects.detail.title')">ClientsProjects</span> {{clientsProjects.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.clientsProjects.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{clientsProjects.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clientsProjects.projectType')">Project Type</span>
                    </dt>
                    <dd>
                        <div v-if="clientsProjects.projectType">
                            <router-link :to="{name: 'ListClientsProjectTypesView', params: {projectTypeId: clientsProjects.projectType.id}}">{{clientsProjects.projectType.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.clientsProjects.clients')">Clients</span>
                    </dt>
                    <dd>
                        <div v-if="clientsProjects.clients">
                            <router-link :to="{name: 'ClientsView', params: {clientsId: clientsProjects.clients.id}}">{{clientsProjects.clients.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="clientsProjects.id" :to="{name: 'ClientsProjectsEdit', params: {clientsProjectsId: clientsProjects.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./clients-projects-details.component.ts">
</script>
