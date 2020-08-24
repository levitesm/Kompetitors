<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listClientsProjectTypes.home.title')" id="list-clients-project-types-heading">List Clients Project Types</span>
            <router-link :to="{name: 'ListClientsProjectTypesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-clients-project-types">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listClientsProjectTypes.home.createLabel')">
                    Create a new List Clients Project Types
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
        <div class="alert alert-warning" v-if="!isFetching && listClientsProjectTypes && listClientsProjectTypes.length === 0">
            <span v-text="$t('kompetitors2App.listClientsProjectTypes.home.notFound')">No listClientsProjectTypes found</span>
        </div>
        <div class="table-responsive" v-if="listClientsProjectTypes && listClientsProjectTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listClientsProjectTypes.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listClientsProjectTypes in listClientsProjectTypes"
                    :key="listClientsProjectTypes.id">
                    <td>
                        <router-link :to="{name: 'ListClientsProjectTypesView', params: {listClientsProjectTypesId: listClientsProjectTypes.id}}">{{listClientsProjectTypes.id}}</router-link>
                    </td>
                    <td>{{listClientsProjectTypes.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListClientsProjectTypesView', params: {listClientsProjectTypesId: listClientsProjectTypes.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListClientsProjectTypesEdit', params: {listClientsProjectTypesId: listClientsProjectTypes.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listClientsProjectTypes)"
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
            <span slot="modal-title"><span id="kompetitors2App.listClientsProjectTypes.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listClientsProjectTypes-heading" v-bind:title="$t('kompetitors2App.listClientsProjectTypes.delete.question')">Are you sure you want to delete this List Clients Project Types?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listClientsProjectTypes" v-text="$t('entity.action.delete')" v-on:click="removeListClientsProjectTypes()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-clients-project-types.component.ts">
</script>
