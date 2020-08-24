<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.employeesTypology.home.createOrEditLabel" v-text="$t('kompetitors2App.employeesTypology.home.createOrEditLabel')">Create or edit a EmployeesTypology</h2>
                <div>
                    <div class="form-group" v-if="employeesTypology.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="employeesTypology.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeesTypology.value')" for="employees-typology-value">Value</label>
                        <input type="number" class="form-control" name="value" id="employees-typology-value"
                            :class="{'valid': !$v.employeesTypology.value.$invalid, 'invalid': $v.employeesTypology.value.$invalid }" v-model.number="$v.employeesTypology.value.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeesTypology.year')" for="employees-typology-year">Year</label>
                        <input type="number" class="form-control" name="year" id="employees-typology-year"
                            :class="{'valid': !$v.employeesTypology.year.$invalid, 'invalid': $v.employeesTypology.year.$invalid }" v-model.number="$v.employeesTypology.year.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.employeesTypology.employeeType')" for="employees-typology-employeeType">Employee Type</label>
                        <select class="form-control" id="employees-typology-employeeType" name="employeeType" v-model="employeesTypology.employeeTypeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="employeeTypeOption.id" v-for="employeeTypeOption in employeeTypes" :key="employeeTypeOption.id">{{employeeTypeOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.employeesTypology.competitor')" for="employees-typology-competitor">Competitor</label>
                        <select class="form-control" id="employees-typology-competitor" name="competitor" v-model="employeesTypology.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.employeesTypology.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./employees-typology-update.component.ts">
</script>
