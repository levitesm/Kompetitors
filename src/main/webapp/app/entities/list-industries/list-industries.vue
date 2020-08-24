<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listIndustries.home.title')" id="list-industries-heading">List Industries</span>
            <router-link :to="{name: 'ListIndustriesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-industries">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listIndustries.home.createLabel')">
                    Create a new List Industries
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
        <div class="alert alert-warning" v-if="!isFetching && listIndustries && listIndustries.length === 0">
            <span v-text="$t('kompetitors2App.listIndustries.home.notFound')">No listIndustries found</span>
        </div>
        <div class="table-responsive" v-if="listIndustries && listIndustries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listIndustries.value')">Value</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listIndustries in listIndustries"
                    :key="listIndustries.id">
                    <td>
                        <router-link :to="{name: 'ListIndustriesView', params: {listIndustriesId: listIndustries.id}}">{{listIndustries.id}}</router-link>
                    </td>
                    <td>{{listIndustries.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListIndustriesView', params: {listIndustriesId: listIndustries.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListIndustriesEdit', params: {listIndustriesId: listIndustries.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listIndustries)"
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
            <span slot="modal-title"><span id="kompetitors2App.listIndustries.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listIndustries-heading" v-bind:title="$t('kompetitors2App.listIndustries.delete.question')">Are you sure you want to delete this List Industries?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listIndustries" v-text="$t('entity.action.delete')" v-on:click="removeListIndustries()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-industries.component.ts">
</script>
