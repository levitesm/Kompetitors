<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.clientsProjects.home.title')" id="clients-projects-heading">Clients Projects</span>
            <router-link :to="{name: 'ClientsProjectsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-clients-projects">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.clientsProjects.home.createLabel')">
                    Create a new Clients Projects
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && clientsProjects && clientsProjects.length === 0">
            <span v-text="$t('kompetitors2App.clientsProjects.home.notFound')">No clientsProjects found</span>
        </div>
        <div class="table-responsive" v-if="clientsProjects && clientsProjects.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.clientsProjects.status')">Status</span></th>
                    <th><span v-text="$t('kompetitors2App.clientsProjects.projectType')">Project Type</span></th>
                    <th><span v-text="$t('kompetitors2App.clientsProjects.clients')">Clients</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="clientsProjects in clientsProjects"
                    :key="clientsProjects.id">
                    <td>
                        <router-link :to="{name: 'ClientsProjectsView', params: {clientsProjectsId: clientsProjects.id}}">{{clientsProjects.id}}</router-link>
                    </td>
                    <td>{{clientsProjects.status}}</td>
                    <td>
                        <div v-if="clientsProjects.projectType">
                            <router-link :to="{name: 'ListClientsProjectTypesView', params: {projectTypeId: clientsProjects.projectType.id}}">{{clientsProjects.projectType.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="clientsProjects.clients">
                            <router-link :to="{name: 'ClientsView', params: {clientsId: clientsProjects.clients.id}}">{{clientsProjects.clients.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ClientsProjectsView', params: {clientsProjectsId: clientsProjects.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ClientsProjectsEdit', params: {clientsProjectsId: clientsProjects.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(clientsProjects)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="kompetitors2App.clientsProjects.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-clientsProjects-heading" v-bind:title="$t('kompetitors2App.clientsProjects.delete.question')">Are you sure you want to delete this Clients Projects?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-clientsProjects" v-text="$t('entity.action.delete')" v-on:click="removeClientsProjects()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./clients-projects.component.ts">
</script>
