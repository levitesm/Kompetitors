<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.competitorIndustry.home.title')" id="competitor-industry-heading">Competitor Industries</span>
            <router-link :to="{name: 'CompetitorIndustryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competitor-industry">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.competitorIndustry.home.createLabel')">
                    Create a new Competitor Industry
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
        <div class="alert alert-warning" v-if="!isFetching && competitorIndustries && competitorIndustries.length === 0">
            <span v-text="$t('kompetitors2App.competitorIndustry.home.notFound')">No competitorIndustries found</span>
        </div>
        <div class="table-responsive" v-if="competitorIndustries && competitorIndustries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.competitorIndustry.competitor')">Competitor</span></th>
                    <th><span v-text="$t('kompetitors2App.competitorIndustry.industry')">Industry</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitorIndustry in competitorIndustries"
                    :key="competitorIndustry.id">
                    <td>
                        <router-link :to="{name: 'CompetitorIndustryView', params: {competitorIndustryId: competitorIndustry.id}}">{{competitorIndustry.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="competitorIndustry.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: competitorIndustry.competitorId}}">{{competitorIndustry.competitorId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="competitorIndustry.industryId">
                            <router-link :to="{name: 'ListIndustriesView', params: {industryId: competitorIndustry.industryId}}">{{competitorIndustry.industryId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitorIndustryView', params: {competitorIndustryId: competitorIndustry.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitorIndustryEdit', params: {competitorIndustryId: competitorIndustry.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitorIndustry)"
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
            <span slot="modal-title"><span id="kompetitors2App.competitorIndustry.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitorIndustry-heading" v-bind:title="$t('kompetitors2App.competitorIndustry.delete.question')">Are you sure you want to delete this Competitor Industry?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitorIndustry" v-text="$t('entity.action.delete')" v-on:click="removeCompetitorIndustry()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./competitor-industry.component.ts">
</script>
