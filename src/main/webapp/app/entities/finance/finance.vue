<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.finance.home.title')" id="finance-heading">Finances</span>
            <router-link :to="{name: 'FinanceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-finance">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.finance.home.createLabel')">
                    Create a new Finance
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
        <div class="alert alert-warning" v-if="!isFetching && finances && finances.length === 0">
            <span v-text="$t('kompetitors2App.finance.home.notFound')">No finances found</span>
        </div>
        <div class="table-responsive" v-if="finances && finances.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.finance.margin')">Margin</span></th>
                    <th><span v-text="$t('kompetitors2App.finance.ebitda')">Ebitda</span></th>
                    <th><span v-text="$t('kompetitors2App.finance.occupationRate')">Occupation Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.finance.revenue')">Revenue</span></th>
                    <th><span v-text="$t('kompetitors2App.finance.year')">Year</span></th>
                    <th><span v-text="$t('kompetitors2App.finance.competitors')">Competitors</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="finance in finances"
                    :key="finance.id">
                    <td>
                        <router-link :to="{name: 'FinanceView', params: {financeId: finance.id}}">{{finance.id}}</router-link>
                    </td>
                    <td>{{finance.margin}}</td>
                    <td>{{finance.ebitda}}</td>
                    <td>{{finance.occupationRate}}</td>
                    <td>{{finance.revenue}}</td>
                    <td>{{finance.year}}</td>
                    <td>
                        <div v-if="finance.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: finance.competitors.id}}">{{finance.competitors.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FinanceView', params: {financeId: finance.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FinanceEdit', params: {financeId: finance.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(finance)"
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
            <span slot="modal-title"><span id="kompetitors2App.finance.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-finance-heading" v-bind:title="$t('kompetitors2App.finance.delete.question')">Are you sure you want to delete this Finance?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-finance" v-text="$t('entity.action.delete')" v-on:click="removeFinance()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./finance.component.ts">
</script>
