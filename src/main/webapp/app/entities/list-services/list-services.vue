<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listServices.home.title')" id="list-services-heading">List Services</span>
            <router-link :to="{name: 'ListServicesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-services">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listServices.home.createLabel')">
                    Create a new List Services
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
        <div class="alert alert-warning" v-if="!isFetching && listServices && listServices.length === 0">
            <span v-text="$t('kompetitors2App.listServices.home.notFound')">No listServices found</span>
        </div>
        <div class="table-responsive" v-if="listServices && listServices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listServices.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listServices in listServices"
                    :key="listServices.id">
                    <td>
                        <router-link :to="{name: 'ListServicesView', params: {listServicesId: listServices.id}}">{{listServices.id}}</router-link>
                    </td>
                    <td>{{listServices.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListServicesView', params: {listServicesId: listServices.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListServicesEdit', params: {listServicesId: listServices.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listServices)"
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
            <span slot="modal-title"><span id="kompetitors2App.listServices.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listServices-heading" v-bind:title="$t('kompetitors2App.listServices.delete.question')">Are you sure you want to delete this List Services?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listServices" v-text="$t('entity.action.delete')" v-on:click="removeListServices()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-services.component.ts">
</script>
