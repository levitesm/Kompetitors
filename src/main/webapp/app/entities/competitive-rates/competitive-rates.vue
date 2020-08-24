<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.competitiveRates.home.title')" id="competitive-rates-heading">Competitive Rates</span>
            <router-link :to="{name: 'CompetitiveRatesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competitive-rates">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.competitiveRates.home.createLabel')">
                    Create a new Competitive Rates
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
        <div class="alert alert-warning" v-if="!isFetching && competitiveRates && competitiveRates.length === 0">
            <span v-text="$t('kompetitors2App.competitiveRates.home.notFound')">No competitiveRates found</span>
        </div>
        <div class="table-responsive" v-if="competitiveRates && competitiveRates.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.competitiveRates.totalRate')">Total Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.competitiveRates.techRate')">Tech Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.competitiveRates.financeRate')">Finance Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.competitiveRates.clientsRate')">Clients Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.competitiveRates.hrRate')">Hr Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.competitiveRates.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitiveRates in competitiveRates"
                    :key="competitiveRates.id">
                    <td>
                        <router-link :to="{name: 'CompetitiveRatesView', params: {competitiveRatesId: competitiveRates.id}}">{{competitiveRates.id}}</router-link>
                    </td>
                    <td>{{competitiveRates.totalRate}}</td>
                    <td>{{competitiveRates.techRate}}</td>
                    <td>{{competitiveRates.financeRate}}</td>
                    <td>{{competitiveRates.clientsRate}}</td>
                    <td>{{competitiveRates.hrRate}}</td>
                    <td>
                        <div v-if="competitiveRates.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: competitiveRates.competitor.id}}">{{competitiveRates.competitor.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitiveRatesView', params: {competitiveRatesId: competitiveRates.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitiveRatesEdit', params: {competitiveRatesId: competitiveRates.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitiveRates)"
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
            <span slot="modal-title"><span id="kompetitors2App.competitiveRates.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitiveRates-heading" v-bind:title="$t('kompetitors2App.competitiveRates.delete.question')">Are you sure you want to delete this Competitive Rates?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitiveRates" v-text="$t('entity.action.delete')" v-on:click="removeCompetitiveRates()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./competitive-rates.component.ts">
</script>
