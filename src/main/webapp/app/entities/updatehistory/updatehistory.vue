<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.updatehistory.home.title')" id="updatehistory-heading">Updatehistories</span>
            <router-link :to="{name: 'UpdatehistoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-updatehistory">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.updatehistory.home.createLabel')">
                    Create a new Updatehistory
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
        <div class="alert alert-warning" v-if="!isFetching && updatehistories && updatehistories.length === 0">
            <span v-text="$t('kompetitors2App.updatehistory.home.notFound')">No updatehistories found</span>
        </div>
        <div class="table-responsive" v-if="updatehistories && updatehistories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.updatehistory.type')">Type</span></th>
                    <th><span v-text="$t('kompetitors2App.updatehistory.siren')">Siren</span></th>
                    <th><span v-text="$t('kompetitors2App.updatehistory.date')">Date</span></th>
                    <th><span v-text="$t('kompetitors2App.updatehistory.status')">Status</span></th>
                    <th><span v-text="$t('kompetitors2App.updatehistory.responce')">Responce</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="updatehistory in updatehistories"
                    :key="updatehistory.id">
                    <td>
                        <router-link :to="{name: 'UpdatehistoryView', params: {updatehistoryId: updatehistory.id}}">{{updatehistory.id}}</router-link>
                    </td>
                    <td>{{updatehistory.type}}</td>
                    <td>{{updatehistory.siren}}</td>
                    <td>{{updatehistory.date}}</td>
                    <td>{{updatehistory.status}}</td>
                    <td>{{updatehistory.responce}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'UpdatehistoryView', params: {updatehistoryId: updatehistory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UpdatehistoryEdit', params: {updatehistoryId: updatehistory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(updatehistory)"
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
            <span slot="modal-title"><span id="kompetitors2App.updatehistory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-updatehistory-heading" v-bind:title="$t('kompetitors2App.updatehistory.delete.question')">Are you sure you want to delete this Updatehistory?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-updatehistory" v-text="$t('entity.action.delete')" v-on:click="removeUpdatehistory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./updatehistory.component.ts">
</script>
