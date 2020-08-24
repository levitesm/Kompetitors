<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.annualAccountStatistics.home.title')" id="annual-account-statistics-heading">Annual Account Statistics</span>
            <router-link :to="{name: 'AnnualAccountStatisticsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-annual-account-statistics">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.annualAccountStatistics.home.createLabel')">
                    Create a new Annual Account Statistics
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
        <div class="alert alert-warning" v-if="!isFetching && annualAccountStatistics && annualAccountStatistics.length === 0">
            <span v-text="$t('kompetitors2App.annualAccountStatistics.home.notFound')">No annualAccountStatistics found</span>
        </div>
        <div class="table-responsive" v-if="annualAccountStatistics && annualAccountStatistics.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('siren')"><span v-text="$t('kompetitors2App.annualAccountStatistics.siren')">Siren</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('year')"><span v-text="$t('kompetitors2App.annualAccountStatistics.year')">Year</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('kompetitors2App.annualAccountStatistics.code')">Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('message')"><span v-text="$t('kompetitors2App.annualAccountStatistics.message')">Message</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('modified')"><span v-text="$t('kompetitors2App.annualAccountStatistics.modified')">Modified</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="annualAccountStatistics in annualAccountStatistics"
                    :key="annualAccountStatistics.id">
                    <td>
                        <router-link :to="{name: 'AnnualAccountStatisticsView', params: {annualAccountStatisticsId: annualAccountStatistics.id}}">{{annualAccountStatistics.id}}</router-link>
                    </td>
                    <td>{{annualAccountStatistics.siren}}</td>
                    <td>{{annualAccountStatistics.year}}</td>
                    <td>{{annualAccountStatistics.code}}</td>
                    <td>{{annualAccountStatistics.message}}</td>
                    <td>{{annualAccountStatistics.modified | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AnnualAccountStatisticsView', params: {annualAccountStatisticsId: annualAccountStatistics.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AnnualAccountStatisticsEdit', params: {annualAccountStatisticsId: annualAccountStatistics.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(annualAccountStatistics)"
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
            <span slot="modal-title"><span id="kompetitors2App.annualAccountStatistics.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-annualAccountStatistics-heading" v-bind:title="$t('kompetitors2App.annualAccountStatistics.delete.question')">Are you sure you want to delete this Annual Account Statistics?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-annualAccountStatistics" v-text="$t('entity.action.delete')" v-on:click="removeAnnualAccountStatistics()">Delete</button>
            </div>
        </b-modal>
        <div v-show="annualAccountStatistics && annualAccountStatistics.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./annual-account-statistics.component.ts">
</script>
