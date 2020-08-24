<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listTools.home.title')" id="list-tools-heading">List Tools</span>
            <router-link :to="{name: 'ListToolsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-tools">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listTools.home.createLabel')">
                    Create a new List Tools
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
        <div class="alert alert-warning" v-if="!isFetching && listTools && listTools.length === 0">
            <span v-text="$t('kompetitors2App.listTools.home.notFound')">No listTools found</span>
        </div>
        <div class="table-responsive" v-if="listTools && listTools.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listTools.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listTools in listTools"
                    :key="listTools.id">
                    <td>
                        <router-link :to="{name: 'ListToolsView', params: {listToolsId: listTools.id}}">{{listTools.id}}</router-link>
                    </td>
                    <td>{{listTools.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListToolsView', params: {listToolsId: listTools.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListToolsEdit', params: {listToolsId: listTools.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listTools)"
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
            <span slot="modal-title"><span id="kompetitors2App.listTools.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listTools-heading" v-bind:title="$t('kompetitors2App.listTools.delete.question')">Are you sure you want to delete this List Tools?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listTools" v-text="$t('entity.action.delete')" v-on:click="removeListTools()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-tools.component.ts">
</script>
