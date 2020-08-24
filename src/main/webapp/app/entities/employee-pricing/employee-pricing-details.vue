<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="employeePricing">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.employeePricing.detail.title')">EmployeePricing</span> {{employeePricing.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.level')">Level</span>
                    </dt>
                    <dd>
                        <span v-text="$t('kompetitors2App.EmployeeLevel.' + employeePricing.level)">{{employeePricing.level}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.price')">Price</span>
                    </dt>
                    <dd>
                        <span>{{employeePricing.price}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.currency')">Currency</span>
                    </dt>
                    <dd>
                        <span v-text="$t('kompetitors2App.Currency.' + employeePricing.currency)">{{employeePricing.currency}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.paymentType')">Payment Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('kompetitors2App.PaymentType.' + employeePricing.paymentType)">{{employeePricing.paymentType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.modified')">Modified</span>
                    </dt>
                    <dd>
                        <span>{{employeePricing.modified | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.employeeRole')">Employee Role</span>
                    </dt>
                    <dd>
                        <div v-if="employeePricing.employeeRoleId">
                            <router-link :to="{name: 'EmployeeRoleView', params: {employeeRoleId: employeePricing.employeeRoleId}}">{{employeePricing.employeeRoleId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeePricing.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="employeePricing.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: employeePricing.competitorId}}">{{employeePricing.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="employeePricing.id" :to="{name: 'EmployeePricingEdit', params: {employeePricingId: employeePricing.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./employee-pricing-details.component.ts">
</script>
