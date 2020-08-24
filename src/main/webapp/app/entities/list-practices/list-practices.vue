<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listPractices.home.title')" id="list-practices-heading">List Practices</span>
            <router-link :to="{name: 'ListPracticesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-practices">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listPractices.home.createLabel')">
                    Create a new List Practices
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
        <div class="alert alert-warning" v-if="!isFetching && listPractices && listPractices.length === 0">
            <span v-text="$t('kompetitors2App.listPractices.home.notFound')">No listPractices found</span>
        </div>
        <div class="table-responsive" v-if="listPractices && listPractices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listPractices.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listPractices in listPractices"
                    :key="listPractices.id">
                    <td>
                        <router-link :to="{name: 'ListPracticesView', params: {listPracticesId: listPractices.id}}">{{listPractices.id}}</router-link>
                    </td>
                    <td>{{listPractices.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListPracticesView', params: {listPracticesId: listPractices.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListPracticesEdit', params: {listPracticesId: listPractices.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listPractices)"
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
            <span slot="modal-title"><span id="kompetitors2App.listPractices.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listPractices-heading" v-bind:title="$t('kompetitors2App.listPractices.delete.question')">Are you sure you want to delete this List Practices?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listPractices" v-text="$t('entity.action.delete')" v-on:click="removeListPractices()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-practices.component.ts">
</script>
