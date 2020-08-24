<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.userGroupRights.home.title')" id="user-group-rights-heading">User Group Rights</span>
            <router-link :to="{name: 'UserGroupRightsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-user-group-rights">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.userGroupRights.home.createLabel')">
                    Create a new User Group Rights
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
        <div class="alert alert-warning" v-if="!isFetching && userGroupRights && userGroupRights.length === 0">
            <span v-text="$t('kompetitors2App.userGroupRights.home.notFound')">No userGroupRights found</span>
        </div>
        <div class="table-responsive" v-if="userGroupRights && userGroupRights.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.userGroupRights.userGroupName')">User Group Name</span></th>
                    <th><span v-text="$t('kompetitors2App.userGroupRights.accessKey')">Access Key</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="userGroupRights in userGroupRights"
                    :key="userGroupRights.id">
                    <td>
                        <router-link :to="{name: 'UserGroupRightsView', params: {userGroupRightsId: userGroupRights.id}}">{{userGroupRights.id}}</router-link>
                    </td>
                    <td>{{userGroupRights.userGroupName}}</td>
                    <td>
                        <div v-if="userGroupRights.accessKey">
                            <router-link :to="{name: 'AccessKeyView', params: {accessKeyId: userGroupRights.accessKey.id}}">{{userGroupRights.accessKey.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'UserGroupRightsView', params: {userGroupRightsId: userGroupRights.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UserGroupRightsEdit', params: {userGroupRightsId: userGroupRights.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(userGroupRights)"
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
            <span slot="modal-title"><span id="kompetitors2App.userGroupRights.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-userGroupRights-heading" v-bind:title="$t('kompetitors2App.userGroupRights.delete.question')">Are you sure you want to delete this User Group Rights?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-userGroupRights" v-text="$t('entity.action.delete')" v-on:click="removeUserGroupRights()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./user-group-rights.component.ts">
</script>
