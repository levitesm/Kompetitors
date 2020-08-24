<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.dialogs.home.title')" id="dialogs-heading">Dialogs</span>
            <router-link :to="{name: 'DialogsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-dialogs">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.dialogs.home.createLabel')">
                    Create a new Dialogs
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
        <div class="alert alert-warning" v-if="!isFetching && dialogs && dialogs.length === 0">
            <span v-text="$t('kompetitors2App.dialogs.home.notFound')">No dialogs found</span>
        </div>
        <div class="table-responsive" v-if="dialogs && dialogs.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.dialogs.section')">Section</span></th>
                    <th><span v-text="$t('kompetitors2App.dialogs.topic')">Topic</span></th>
                    <th><span v-text="$t('kompetitors2App.dialogs.message')">Message</span></th>
                    <th><span v-text="$t('kompetitors2App.dialogs.author')">Author</span></th>
                    <th><span v-text="$t('kompetitors2App.dialogs.date')">Date</span></th>
                    <th><span v-text="$t('kompetitors2App.dialogs.competitors')">Competitors</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="dialogs in dialogs"
                    :key="dialogs.id">
                    <td>
                        <router-link :to="{name: 'DialogsView', params: {dialogsId: dialogs.id}}">{{dialogs.id}}</router-link>
                    </td>
                    <td>{{dialogs.section}}</td>
                    <td>{{dialogs.topic}}</td>
                    <td>{{dialogs.message}}</td>
                    <td>{{dialogs.author}}</td>
                    <td>{{dialogs.date}}</td>
                    <td>
                        <div v-if="dialogs.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: dialogs.competitors.id}}">{{dialogs.competitors.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DialogsView', params: {dialogsId: dialogs.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DialogsEdit', params: {dialogsId: dialogs.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(dialogs)"
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
            <span slot="modal-title"><span id="kompetitors2App.dialogs.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-dialogs-heading" v-bind:title="$t('kompetitors2App.dialogs.delete.question')">Are you sure you want to delete this Dialogs?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-dialogs" v-text="$t('entity.action.delete')" v-on:click="removeDialogs()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./dialogs.component.ts">
</script>
