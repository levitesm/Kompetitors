<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listOwnerships.home.title')" id="list-ownerships-heading">List Ownerships</span>
            <router-link :to="{name: 'ListOwnershipsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-ownerships">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listOwnerships.home.createLabel')">
                    Create a new List Ownerships
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
        <div class="alert alert-warning" v-if="!isFetching && listOwnerships && listOwnerships.length === 0">
            <span v-text="$t('kompetitors2App.listOwnerships.home.notFound')">No listOwnerships found</span>
        </div>
        <div class="table-responsive" v-if="listOwnerships && listOwnerships.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listOwnerships.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listOwnerships in listOwnerships"
                    :key="listOwnerships.id">
                    <td>
                        <router-link :to="{name: 'ListOwnershipsView', params: {listOwnershipsId: listOwnerships.id}}">{{listOwnerships.id}}</router-link>
                    </td>
                    <td>{{listOwnerships.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListOwnershipsView', params: {listOwnershipsId: listOwnerships.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListOwnershipsEdit', params: {listOwnershipsId: listOwnerships.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listOwnerships)"
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
            <span slot="modal-title"><span id="kompetitors2App.listOwnerships.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listOwnerships-heading" v-bind:title="$t('kompetitors2App.listOwnerships.delete.question')">Are you sure you want to delete this List Ownerships?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listOwnerships" v-text="$t('entity.action.delete')" v-on:click="removeListOwnerships()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-ownerships.component.ts">
</script>
