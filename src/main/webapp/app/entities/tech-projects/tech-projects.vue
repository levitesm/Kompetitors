<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.techProjects.home.title')" id="tech-projects-heading">Tech Projects</span>
            <router-link :to="{name: 'TechProjectsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tech-projects">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.techProjects.home.createLabel')">
                    Create a new Tech Projects
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
        <div class="alert alert-warning" v-if="!isFetching && techProjects && techProjects.length === 0">
            <span v-text="$t('kompetitors2App.techProjects.home.notFound')">No techProjects found</span>
        </div>
        <div class="table-responsive" v-if="techProjects && techProjects.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.techProjects.value')">Value</span></th>
                    <th><span v-text="$t('kompetitors2App.techProjects.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="techProjects in techProjects"
                    :key="techProjects.id">
                    <td>
                        <router-link :to="{name: 'TechProjectsView', params: {techProjectsId: techProjects.id}}">{{techProjects.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="techProjects.valueId">
                            <router-link :to="{name: 'ListProjectTypesView', params: {valueId: techProjects.valueId}}">{{techProjects.valueId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="techProjects.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techProjects.competitorId}}">{{techProjects.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TechProjectsView', params: {techProjectsId: techProjects.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TechProjectsEdit', params: {techProjectsId: techProjects.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(techProjects)"
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
            <span slot="modal-title"><span id="kompetitors2App.techProjects.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-techProjects-heading" v-bind:title="$t('kompetitors2App.techProjects.delete.question')">Are you sure you want to delete this Tech Projects?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-techProjects" v-text="$t('entity.action.delete')" v-on:click="removeTechProjects()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./tech-projects.component.ts">
</script>
