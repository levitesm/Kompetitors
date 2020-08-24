<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.techCompetancies.home.title')" id="tech-competancies-heading">Tech Competancies</span>
            <router-link :to="{name: 'TechCompetanciesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tech-competancies">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.techCompetancies.home.createLabel')">
                    Create a new Tech Competancies
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
        <div class="alert alert-warning" v-if="!isFetching && techCompetancies && techCompetancies.length === 0">
            <span v-text="$t('kompetitors2App.techCompetancies.home.notFound')">No techCompetancies found</span>
        </div>
        <div class="table-responsive" v-if="techCompetancies && techCompetancies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.techCompetancies.value')">Value</span></th>
                    <th><span v-text="$t('kompetitors2App.techCompetancies.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="techCompetancies in techCompetancies"
                    :key="techCompetancies.id">
                    <td>
                        <router-link :to="{name: 'TechCompetanciesView', params: {techCompetanciesId: techCompetancies.id}}">{{techCompetancies.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="techCompetancies.valueId">
                            <router-link :to="{name: 'ListCompetanciesView', params: {valueId: techCompetancies.valueId}}">{{techCompetancies.valueId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="techCompetancies.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techCompetancies.competitorId}}">{{techCompetancies.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TechCompetanciesView', params: {techCompetanciesId: techCompetancies.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TechCompetanciesEdit', params: {techCompetanciesId: techCompetancies.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(techCompetancies)"
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
            <span slot="modal-title"><span id="kompetitors2App.techCompetancies.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-techCompetancies-heading" v-bind:title="$t('kompetitors2App.techCompetancies.delete.question')">Are you sure you want to delete this Tech Competancies?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-techCompetancies" v-text="$t('entity.action.delete')" v-on:click="removeTechCompetancies()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./tech-competancies.component.ts">
</script>
