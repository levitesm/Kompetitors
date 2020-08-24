<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="dashboardFinance">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.dashboardFinance.detail.title')">DashboardFinance</span> {{dashboardFinance.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.grossSales')">Gross Sales</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.grossSales}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.grossSalesPerEmployee')">Gross Sales Per Employee</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.grossSalesPerEmployee}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.ebit')">Ebit</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.ebit}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.netResult')">Net Result</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.netResult}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.workforce')">Workforce</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.workforce}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.year')">Year</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.year}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.grossSalesPerConsultant')">Gross Sales Per Consultant</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.grossSalesPerConsultant}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.averagePay')">Average Pay</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.averagePay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.netResultPercent')">Net Result Percent</span>
                    </dt>
                    <dd>
                        <span>{{dashboardFinance.netResultPercent}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.dashboardFinance.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="dashboardFinance.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: dashboardFinance.competitorId}}">{{dashboardFinance.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="dashboardFinance.id" :to="{name: 'DashboardFinanceEdit', params: {dashboardFinanceId: dashboardFinance.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./dashboard-finance-details.component.ts">
</script>
