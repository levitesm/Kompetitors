<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.accessKey.home.title')" id="access-key-heading">Access Keys</span>
            <router-link :to="{name: 'AccessKeyCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-access-key">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.accessKey.home.createLabel')">
                    Create a new Access Key
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
        <div class="alert alert-warning" v-if="!isFetching && accessKeys && accessKeys.length === 0">
            <span v-text="$t('kompetitors2App.accessKey.home.notFound')">No accessKeys found</span>
        </div>
        <div class="table-responsive" v-if="accessKeys && accessKeys.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.accessKey.name')">Name</span></th>
                    <th><span v-text="$t('kompetitors2App.accessKey.description')">Description</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="accessKey in accessKeys"
                    :key="accessKey.id">
                    <td>
                        <router-link :to="{name: 'AccessKeyView', params: {accessKeyId: accessKey.id}}">{{accessKey.id}}</router-link>
                    </td>
                    <td>{{accessKey.name}}</td>
                    <td>{{accessKey.description}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AccessKeyView', params: {accessKeyId: accessKey.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AccessKeyEdit', params: {accessKeyId: accessKey.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(accessKey)"
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
            <span slot="modal-title"><span id="kompetitors2App.accessKey.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-accessKey-heading" v-bind:title="$t('kompetitors2App.accessKey.delete.question')">Are you sure you want to delete this Access Key?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-accessKey" v-text="$t('entity.action.delete')" v-on:click="removeAccessKey()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./access-key.component.ts">
</script>
