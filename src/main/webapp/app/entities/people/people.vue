<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.people.home.title')" id="people-heading">People</span>
            <router-link :to="{name: 'PeopleCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-people">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.people.home.createLabel')">
                    Create a new People
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
        <div class="alert alert-warning" v-if="!isFetching && people && people.length === 0">
            <span v-text="$t('kompetitors2App.people.home.notFound')">No people found</span>
        </div>
        <div class="table-responsive" v-if="people && people.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.people.fName')">F Name</span></th>
                    <th><span v-text="$t('kompetitors2App.people.lName')">L Name</span></th>
                    <th><span v-text="$t('kompetitors2App.people.title')">Title</span></th>
                    <th><span v-text="$t('kompetitors2App.people.linkedPage')">Linked Page</span></th>
                    <th><span v-text="$t('kompetitors2App.people.isKey')">Is Key</span></th>
                    <th><span v-text="$t('kompetitors2App.people.competitors')">Competitors</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="people in people"
                    :key="people.id">
                    <td>
                        <router-link :to="{name: 'PeopleView', params: {peopleId: people.id}}">{{people.id}}</router-link>
                    </td>
                    <td>{{people.fName}}</td>
                    <td>{{people.lName}}</td>
                    <td>{{people.title}}</td>
                    <td>{{people.linkedPage}}</td>
                    <td>{{people.isKey}}</td>
                    <td>
                        <div v-if="people.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: people.competitors.id}}">{{people.competitors.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PeopleView', params: {peopleId: people.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PeopleEdit', params: {peopleId: people.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(people)"
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
            <span slot="modal-title"><span id="kompetitors2App.people.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-people-heading" v-bind:title="$t('kompetitors2App.people.delete.question')">Are you sure you want to delete this People?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-people" v-text="$t('entity.action.delete')" v-on:click="removePeople()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./people.component.ts">
</script>
