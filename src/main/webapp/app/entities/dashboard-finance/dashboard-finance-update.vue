<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.dashboardFinance.home.createOrEditLabel" v-text="$t('kompetitors2App.dashboardFinance.home.createOrEditLabel')">Create or edit a DashboardFinance</h2>
                <div>
                    <div class="form-group" v-if="dashboardFinance.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="dashboardFinance.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.grossSales')" for="dashboard-finance-grossSales">Gross Sales</label>
                        <input type="number" class="form-control" name="grossSales" id="dashboard-finance-grossSales"
                            :class="{'valid': !$v.dashboardFinance.grossSales.$invalid, 'invalid': $v.dashboardFinance.grossSales.$invalid }" v-model.number="$v.dashboardFinance.grossSales.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.grossSalesPerEmployee')" for="dashboard-finance-grossSalesPerEmployee">Gross Sales Per Employee</label>
                        <input type="number" class="form-control" name="grossSalesPerEmployee" id="dashboard-finance-grossSalesPerEmployee"
                            :class="{'valid': !$v.dashboardFinance.grossSalesPerEmployee.$invalid, 'invalid': $v.dashboardFinance.grossSalesPerEmployee.$invalid }" v-model.number="$v.dashboardFinance.grossSalesPerEmployee.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.ebit')" for="dashboard-finance-ebit">Ebit</label>
                        <input type="number" class="form-control" name="ebit" id="dashboard-finance-ebit"
                            :class="{'valid': !$v.dashboardFinance.ebit.$invalid, 'invalid': $v.dashboardFinance.ebit.$invalid }" v-model.number="$v.dashboardFinance.ebit.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.netResult')" for="dashboard-finance-netResult">Net Result</label>
                        <input type="number" class="form-control" name="netResult" id="dashboard-finance-netResult"
                            :class="{'valid': !$v.dashboardFinance.netResult.$invalid, 'invalid': $v.dashboardFinance.netResult.$invalid }" v-model.number="$v.dashboardFinance.netResult.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.workforce')" for="dashboard-finance-workforce">Workforce</label>
                        <input type="number" class="form-control" name="workforce" id="dashboard-finance-workforce"
                            :class="{'valid': !$v.dashboardFinance.workforce.$invalid, 'invalid': $v.dashboardFinance.workforce.$invalid }" v-model.number="$v.dashboardFinance.workforce.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.year')" for="dashboard-finance-year">Year</label>
                        <input type="number" class="form-control" name="year" id="dashboard-finance-year"
                            :class="{'valid': !$v.dashboardFinance.year.$invalid, 'invalid': $v.dashboardFinance.year.$invalid }" v-model.number="$v.dashboardFinance.year.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.grossSalesPerConsultant')" for="dashboard-finance-grossSalesPerConsultant">Gross Sales Per Consultant</label>
                        <input type="number" class="form-control" name="grossSalesPerConsultant" id="dashboard-finance-grossSalesPerConsultant"
                            :class="{'valid': !$v.dashboardFinance.grossSalesPerConsultant.$invalid, 'invalid': $v.dashboardFinance.grossSalesPerConsultant.$invalid }" v-model.number="$v.dashboardFinance.grossSalesPerConsultant.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.averagePay')" for="dashboard-finance-averagePay">Average Pay</label>
                        <input type="number" class="form-control" name="averagePay" id="dashboard-finance-averagePay"
                            :class="{'valid': !$v.dashboardFinance.averagePay.$invalid, 'invalid': $v.dashboardFinance.averagePay.$invalid }" v-model.number="$v.dashboardFinance.averagePay.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dashboardFinance.netResultPercent')" for="dashboard-finance-netResultPercent">Net Result Percent</label>
                        <input type="number" class="form-control" name="netResultPercent" id="dashboard-finance-netResultPercent"
                            :class="{'valid': !$v.dashboardFinance.netResultPercent.$invalid, 'invalid': $v.dashboardFinance.netResultPercent.$invalid }" v-model.number="$v.dashboardFinance.netResultPercent.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.dashboardFinance.competitor')" for="dashboard-finance-competitor">Competitor</label>
                        <select class="form-control" id="dashboard-finance-competitor" name="competitor" v-model="dashboardFinance.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.dashboardFinance.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./dashboard-finance-update.component.ts">
</script>
