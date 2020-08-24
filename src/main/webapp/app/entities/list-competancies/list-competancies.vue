<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listCompetancies.home.title')" id="list-competancies-heading">List Competancies</span>
            <router-link :to="{name: 'ListCompetanciesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-competancies">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listCompetancies.home.createLabel')">
                    Create a new List Competancies
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
        <div class="alert alert-warning" v-if="!isFetching && listCompetancies && listCompetancies.length === 0">
            <span v-text="$t('kompetitors2App.listCompetancies.home.notFound')">No listCompetancies found</span>
        </div>
        <div class="table-responsive" v-if="listCompetancies && listCompetancies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listCompetancies.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listCompetancies in listCompetancies"
                    :key="listCompetancies.id">
                    <td>
                        <router-link :to="{name: 'ListCompetanciesView', params: {listCompetanciesId: listCompetancies.id}}">{{listCompetancies.id}}</router-link>
                    </td>
                    <td>{{listCompetancies.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListCompetanciesView', params: {listCompetanciesId: listCompetancies.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListCompetanciesEdit', params: {listCompetanciesId: listCompetancies.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listCompetancies)"
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
            <span slot="modal-title"><span id="kompetitors2App.listCompetancies.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listCompetancies-heading" v-bind:title="$t('kompetitors2App.listCompetancies.delete.question')">Are you sure you want to delete this List Competancies?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listCompetancies" v-text="$t('entity.action.delete')" v-on:click="removeListCompetancies()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-competancies.component.ts">
</script>
