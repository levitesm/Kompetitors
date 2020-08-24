<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.clients.home.title')" id="clients-heading">Clients</span>
            <router-link :to="{name: 'ClientsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-clients">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.clients.home.createLabel')">
                    Create a new Clients
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
        <div class="alert alert-warning" v-if="!isFetching && clients && clients.length === 0">
            <span v-text="$t('kompetitors2App.clients.home.notFound')">No clients found</span>
        </div>
        <div class="table-responsive" v-if="clients && clients.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.officeName')">Office Name</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.name')">Name</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.since')">Since</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.isIppon')">Is Ippon</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.updateDate')">Update Date</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.industry')">Industry</span></th>
                    <th><span v-text="$t('kompetitors2App.clients.offices')">Offices</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="clients in clients"
                    :key="clients.id">
                    <td>
                        <router-link :to="{name: 'ClientsView', params: {clientsId: clients.id}}">{{clients.id}}</router-link>
                    </td>
                    <td>{{clients.officeName}}</td>
                    <td>{{clients.name}}</td>
                    <td>{{clients.since}}</td>
                    <td>{{clients.isIppon}}</td>
                    <td>{{clients.updateDate}}</td>
                    <td>
                        <div v-if="clients.industry">
                            <router-link :to="{name: 'ListIndustriesView', params: {industryId: clients.industry.id}}">{{clients.industry.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="clients.offices">
                            <router-link :to="{name: 'OfficesView', params: {officesId: clients.offices.id}}">{{clients.offices.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ClientsView', params: {clientsId: clients.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ClientsEdit', params: {clientsId: clients.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(clients)"
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
            <span slot="modal-title"><span id="kompetitors2App.clients.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-clients-heading" v-bind:title="$t('kompetitors2App.clients.delete.question')">Are you sure you want to delete this Clients?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-clients" v-text="$t('entity.action.delete')" v-on:click="removeClients()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./clients.component.ts">
</script>
