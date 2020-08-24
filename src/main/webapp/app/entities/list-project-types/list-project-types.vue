<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listProjectTypes.home.title')" id="list-project-types-heading">List Project Types</span>
            <router-link :to="{name: 'ListProjectTypesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-project-types">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listProjectTypes.home.createLabel')">
                    Create a new List Project Types
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
        <div class="alert alert-warning" v-if="!isFetching && listProjectTypes && listProjectTypes.length === 0">
            <span v-text="$t('kompetitors2App.listProjectTypes.home.notFound')">No listProjectTypes found</span>
        </div>
        <div class="table-responsive" v-if="listProjectTypes && listProjectTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listProjectTypes.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listProjectTypes in listProjectTypes"
                    :key="listProjectTypes.id">
                    <td>
                        <router-link :to="{name: 'ListProjectTypesView', params: {listProjectTypesId: listProjectTypes.id}}">{{listProjectTypes.id}}</router-link>
                    </td>
                    <td>{{listProjectTypes.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListProjectTypesView', params: {listProjectTypesId: listProjectTypes.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListProjectTypesEdit', params: {listProjectTypesId: listProjectTypes.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listProjectTypes)"
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
            <span slot="modal-title"><span id="kompetitors2App.listProjectTypes.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listProjectTypes-heading" v-bind:title="$t('kompetitors2App.listProjectTypes.delete.question')">Are you sure you want to delete this List Project Types?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listProjectTypes" v-text="$t('entity.action.delete')" v-on:click="removeListProjectTypes()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-project-types.component.ts">
</script>
