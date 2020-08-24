<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.employeePricing.home.createOrEditLabel" v-text="$t('kompetitors2App.employeePricing.home.createOrEditLabel')">Create or edit a EmployeePricing</h2>
                <div>
                    <div class="form-group" v-if="employeePricing.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="employeePricing.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeePricing.level')" for="employee-pricing-level">Level</label>
                        <select class="form-control" name="level" :class="{'valid': !$v.employeePricing.level.$invalid, 'invalid': $v.employeePricing.level.$invalid }" v-model="$v.employeePricing.level.$model" id="employee-pricing-level" >
                            <option value="JUNIOR" v-bind:label="$t('kompetitors2App.EmployeeLevel.JUNIOR')">JUNIOR</option>
                            <option value="MIDDLE" v-bind:label="$t('kompetitors2App.EmployeeLevel.MIDDLE')">MIDDLE</option>
                            <option value="SENIOR" v-bind:label="$t('kompetitors2App.EmployeeLevel.SENIOR')">SENIOR</option>
                            <option value="TRAINEE" v-bind:label="$t('kompetitors2App.EmployeeLevel.TRAINEE')">TRAINEE</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeePricing.price')" for="employee-pricing-price">Price</label>
                        <input type="number" class="form-control" name="price" id="employee-pricing-price"
                            :class="{'valid': !$v.employeePricing.price.$invalid, 'invalid': $v.employeePricing.price.$invalid }" v-model.number="$v.employeePricing.price.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeePricing.currency')" for="employee-pricing-currency">Currency</label>
                        <select class="form-control" name="currency" :class="{'valid': !$v.employeePricing.currency.$invalid, 'invalid': $v.employeePricing.currency.$invalid }" v-model="$v.employeePricing.currency.$model" id="employee-pricing-currency" >
                            <option value="EUR" v-bind:label="$t('kompetitors2App.Currency.EUR')">EUR</option>
                            <option value="USD" v-bind:label="$t('kompetitors2App.Currency.USD')">USD</option>
                            <option value="RUB" v-bind:label="$t('kompetitors2App.Currency.RUB')">RUB</option>
                            <option value="CNY" v-bind:label="$t('kompetitors2App.Currency.CNY')">CNY</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeePricing.paymentType')" for="employee-pricing-paymentType">Payment Type</label>
                        <select class="form-control" name="paymentType" :class="{'valid': !$v.employeePricing.paymentType.$invalid, 'invalid': $v.employeePricing.paymentType.$invalid }" v-model="$v.employeePricing.paymentType.$model" id="employee-pricing-paymentType" >
                            <option value="PER_HOUR" v-bind:label="$t('kompetitors2App.PaymentType.PER_HOUR')">PER_HOUR</option>
                            <option value="PER_DAY" v-bind:label="$t('kompetitors2App.PaymentType.PER_DAY')">PER_DAY</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeePricing.modified')" for="employee-pricing-modified">Modified</label>
                        <div class="d-flex">
                            <input id="employee-pricing-modified" type="datetime-local" class="form-control" name="modified" :class="{'valid': !$v.employeePricing.modified.$invalid, 'invalid': $v.employeePricing.modified.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.employeePricing.modified.$model)"
                            @change="updateInstantField('modified', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.employeePricing.employeeRole')" for="employee-pricing-employeeRole">Employee Role</label>
                        <select class="form-control" id="employee-pricing-employeeRole" name="employeeRole" v-model="employeePricing.employeeRoleId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="employeeRoleOption.id" v-for="employeeRoleOption in employeeRoles" :key="employeeRoleOption.id">{{employeeRoleOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.employeePricing.competitor')" for="employee-pricing-competitor">Competitor</label>
                        <select class="form-control" id="employee-pricing-competitor" name="competitor" v-model="employeePricing.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.employeePricing.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./employee-pricing-update.component.ts">
</script>
