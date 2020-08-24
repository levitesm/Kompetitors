<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.globalGroups.home.title')" id="global-groups-heading">Global Groups</span>
            <router-link :to="{name: 'GlobalGroupsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-global-groups">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.globalGroups.home.createLabel')">
                    Create a new Global Groups
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
        <div class="alert alert-warning" v-if="!isFetching && globalGroups && globalGroups.length === 0">
            <span v-text="$t('kompetitors2App.globalGroups.home.notFound')">No globalGroups found</span>
        </div>
        <div class="table-responsive" v-if="globalGroups && globalGroups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.globalGroups.name')">Name</span></th>
                    <th><span v-text="$t('kompetitors2App.globalGroups.logo')">Logo</span></th>
                    <th><span v-text="$t('kompetitors2App.globalGroups.webSite')">Web Site</span></th>
                    <th><span v-text="$t('kompetitors2App.globalGroups.reference')">Reference</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="globalGroups in globalGroups"
                    :key="globalGroups.id">
                    <td>
                        <router-link :to="{name: 'GlobalGroupsView', params: {globalGroupsId: globalGroups.id}}">{{globalGroups.id}}</router-link>
                    </td>
                    <td>{{globalGroups.name}}</td>
                    <td>
                        <a v-if="globalGroups.logo" v-on:click="openFile(globalGroups.logoContentType, globalGroups.logo)" v-text="$t('entity.action.open')">open</a>
                        <span v-if="globalGroups.logo">{{globalGroups.logoContentType}}, {{byteSize(globalGroups.logo)}}</span>
                    </td>
                    <td>{{globalGroups.webSite}}</td>
                    <td>{{globalGroups.reference}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'GlobalGroupsView', params: {globalGroupsId: globalGroups.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'GlobalGroupsEdit', params: {globalGroupsId: globalGroups.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(globalGroups)"
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
            <span slot="modal-title"><span id="kompetitors2App.globalGroups.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-globalGroups-heading" v-bind:title="$t('kompetitors2App.globalGroups.delete.question')">Are you sure you want to delete this Global Groups?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-globalGroups" v-text="$t('entity.action.delete')" v-on:click="removeGlobalGroups()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./global-groups.component.ts">
</script>
