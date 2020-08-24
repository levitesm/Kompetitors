<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listActivities.home.title')" id="list-activities-heading">List Activities</span>
            <router-link :to="{name: 'ListActivitiesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-activities">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listActivities.home.createLabel')">
                    Create a new List Activities
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
        <div class="alert alert-warning" v-if="!isFetching && listActivities && listActivities.length === 0">
            <span v-text="$t('kompetitors2App.listActivities.home.notFound')">No listActivities found</span>
        </div>
        <div class="table-responsive" v-if="listActivities && listActivities.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listActivities.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listActivities in listActivities"
                    :key="listActivities.id">
                    <td>
                        <router-link :to="{name: 'ListActivitiesView', params: {listActivitiesId: listActivities.id}}">{{listActivities.id}}</router-link>
                    </td>
                    <td>{{listActivities.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListActivitiesView', params: {listActivitiesId: listActivities.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListActivitiesEdit', params: {listActivitiesId: listActivities.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listActivities)"
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
            <span slot="modal-title"><span id="kompetitors2App.listActivities.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listActivities-heading" v-bind:title="$t('kompetitors2App.listActivities.delete.question')">Are you sure you want to delete this List Activities?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listActivities" v-text="$t('entity.action.delete')" v-on:click="removeListActivities()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-activities.component.ts">
</script>
