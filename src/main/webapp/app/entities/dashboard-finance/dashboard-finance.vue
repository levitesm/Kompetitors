<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.dashboardFinance.home.title')" id="dashboard-finance-heading">Dashboard Finances</span>
            <router-link :to="{name: 'DashboardFinanceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-dashboard-finance">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.dashboardFinance.home.createLabel')">
                    Create a new Dashboard Finance
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
        <div class="alert alert-warning" v-if="!isFetching && dashboardFinances && dashboardFinances.length === 0">
            <span v-text="$t('kompetitors2App.dashboardFinance.home.notFound')">No dashboardFinances found</span>
        </div>
        <div class="table-responsive" v-if="dashboardFinances && dashboardFinances.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.grossSales')">Gross Sales</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.grossSalesPerEmployee')">Gross Sales Per Employee</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.ebit')">Ebit</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.netResult')">Net Result</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.workforce')">Workforce</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.year')">Year</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.grossSalesPerConsultant')">Gross Sales Per Consultant</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.averagePay')">Average Pay</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.netResultPercent')">Net Result Percent</span></th>
                    <th><span v-text="$t('kompetitors2App.dashboardFinance.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="dashboardFinance in dashboardFinances"
                    :key="dashboardFinance.id">
                    <td>
                        <router-link :to="{name: 'DashboardFinanceView', params: {dashboardFinanceId: dashboardFinance.id}}">{{dashboardFinance.id}}</router-link>
                    </td>
                    <td>{{dashboardFinance.grossSales}}</td>
                    <td>{{dashboardFinance.grossSalesPerEmployee}}</td>
                    <td>{{dashboardFinance.ebit}}</td>
                    <td>{{dashboardFinance.netResult}}</td>
                    <td>{{dashboardFinance.workforce}}</td>
                    <td>{{dashboardFinance.year}}</td>
                    <td>{{dashboardFinance.grossSalesPerConsultant}}</td>
                    <td>{{dashboardFinance.averagePay}}</td>
                    <td>{{dashboardFinance.netResultPercent}}</td>
                    <td>
                        <div v-if="dashboardFinance.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: dashboardFinance.competitorId}}">{{dashboardFinance.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DashboardFinanceView', params: {dashboardFinanceId: dashboardFinance.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DashboardFinanceEdit', params: {dashboardFinanceId: dashboardFinance.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(dashboardFinance)"
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
            <span slot="modal-title"><span id="kompetitors2App.dashboardFinance.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-dashboardFinance-heading" v-bind:title="$t('kompetitors2App.dashboardFinance.delete.question')">Are you sure you want to delete this Dashboard Finance?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-dashboardFinance" v-text="$t('entity.action.delete')" v-on:click="removeDashboardFinance()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./dashboard-finance.component.ts">
</script>
