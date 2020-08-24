<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.competitors.home.title')" id="competitors-heading">Competitors</span>
            <router-link :to="{name: 'CompetitorsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competitors">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.competitors.home.createLabel')">
                    Create a new Competitors
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
        <div class="alert alert-warning" v-if="!isFetching && competitors && competitors.length === 0">
            <span v-text="$t('kompetitors2App.competitors.home.notFound')">No competitors found</span>
        </div>
        <div class="table-responsive" v-if="competitors && competitors.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.competitors.name')">Name</span></th>
                    <th><span v-text="$t('kompetitors2App.competitors.webSite')">Web Site</span></th>
                    <th><span v-text="$t('kompetitors2App.competitors.countryPhone')">Country Phone</span></th>
                    <th><span v-text="$t('kompetitors2App.competitors.country')">Country</span></th>
                    <th><span v-text="$t('kompetitors2App.competitors.globalGroups')">Global Groups</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitors in competitors"
                    :key="competitors.id">
                    <td>
                        <router-link :to="{name: 'CompetitorsView', params: {competitorsId: competitors.id}}">{{competitors.id}}</router-link>
                    </td>
                    <td>{{competitors.name}}</td>
                    <td>{{competitors.webSite}}</td>
                    <td>{{competitors.countryPhone}}</td>
                    <td>
                        <div v-if="competitors.country">
                            <router-link :to="{name: 'ListCountriesView', params: {countryId: competitors.country.id}}">{{competitors.country.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="competitors.globalGroups">
                            <router-link :to="{name: 'GlobalGroupsView', params: {globalGroupsId: competitors.globalGroups.id}}">{{competitors.globalGroups.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: competitors.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitorsEdit', params: {competitorsId: competitors.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitors)"
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
            <span slot="modal-title"><span id="kompetitors2App.competitors.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitors-heading" v-bind:title="$t('kompetitors2App.competitors.delete.question')">Are you sure you want to delete this Competitors?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitors" v-text="$t('entity.action.delete')" v-on:click="removeCompetitors()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./competitors.component.ts">
</script>
