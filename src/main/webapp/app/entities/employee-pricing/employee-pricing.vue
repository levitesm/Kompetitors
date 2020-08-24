<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.employeePricing.home.title')" id="employee-pricing-heading">Employee Pricings</span>
            <router-link :to="{name: 'EmployeePricingCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-employee-pricing">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.employeePricing.home.createLabel')">
                    Create a new Employee Pricing
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
        <div class="alert alert-warning" v-if="!isFetching && employeePricings && employeePricings.length === 0">
            <span v-text="$t('kompetitors2App.employeePricing.home.notFound')">No employeePricings found</span>
        </div>
        <div class="table-responsive" v-if="employeePricings && employeePricings.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.level')">Level</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.price')">Price</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.currency')">Currency</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.paymentType')">Payment Type</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.modified')">Modified</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.employeeRole')">Employee Role</span></th>
                    <th><span v-text="$t('kompetitors2App.employeePricing.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="employeePricing in employeePricings"
                    :key="employeePricing.id">
                    <td>
                        <router-link :to="{name: 'EmployeePricingView', params: {employeePricingId: employeePricing.id}}">{{employeePricing.id}}</router-link>
                    </td>
                    <td v-text="$t('kompetitors2App.EmployeeLevel.' + employeePricing.level)">{{employeePricing.level}}</td>
                    <td>{{employeePricing.price}}</td>
                    <td v-text="$t('kompetitors2App.Currency.' + employeePricing.currency)">{{employeePricing.currency}}</td>
                    <td v-text="$t('kompetitors2App.PaymentType.' + employeePricing.paymentType)">{{employeePricing.paymentType}}</td>
                    <td>{{employeePricing.modified | formatDate}}</td>
                    <td>
                        <div v-if="employeePricing.employeeRoleId">
                            <router-link :to="{name: 'EmployeeRoleView', params: {employeeRoleId: employeePricing.employeeRoleId}}">{{employeePricing.employeeRoleId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="employeePricing.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: employeePricing.competitorId}}">{{employeePricing.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'EmployeePricingView', params: {employeePricingId: employeePricing.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'EmployeePricingEdit', params: {employeePricingId: employeePricing.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(employeePricing)"
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
            <span slot="modal-title"><span id="kompetitors2App.employeePricing.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-employeePricing-heading" v-bind:title="$t('kompetitors2App.employeePricing.delete.question')">Are you sure you want to delete this Employee Pricing?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-employeePricing" v-text="$t('entity.action.delete')" v-on:click="removeEmployeePricing()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./employee-pricing.component.ts">
</script>
