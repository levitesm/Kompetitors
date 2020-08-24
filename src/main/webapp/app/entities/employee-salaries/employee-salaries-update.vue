<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.employeeSalaries.home.createOrEditLabel" v-text="$t('kompetitors2App.employeeSalaries.home.createOrEditLabel')">Create or edit a EmployeeSalaries</h2>
                <div>
                    <div class="form-group" v-if="employeeSalaries.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="employeeSalaries.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeeSalaries.seniority')" for="employee-salaries-seniority">Seniority</label>
                        <input type="text" class="form-control" name="seniority" id="employee-salaries-seniority"
                            :class="{'valid': !$v.employeeSalaries.seniority.$invalid, 'invalid': $v.employeeSalaries.seniority.$invalid }" v-model="$v.employeeSalaries.seniority.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeeSalaries.salary')" for="employee-salaries-salary">Salary</label>
                        <input type="number" class="form-control" name="salary" id="employee-salaries-salary"
                            :class="{'valid': !$v.employeeSalaries.salary.$invalid, 'invalid': $v.employeeSalaries.salary.$invalid }" v-model.number="$v.employeeSalaries.salary.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeeSalaries.updateDate')" for="employee-salaries-updateDate">Update Date</label>
                        <div class="input-group">
                            <input id="employee-salaries-updateDate" type="date" class="form-control" name="updateDate"  :class="{'valid': !$v.employeeSalaries.updateDate.$invalid, 'invalid': $v.employeeSalaries.updateDate.$invalid }"
                            v-model="$v.employeeSalaries.updateDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeeSalaries.updatedBy')" for="employee-salaries-updatedBy">Updated By</label>
                        <input type="text" class="form-control" name="updatedBy" id="employee-salaries-updatedBy"
                            :class="{'valid': !$v.employeeSalaries.updatedBy.$invalid, 'invalid': $v.employeeSalaries.updatedBy.$invalid }" v-model="$v.employeeSalaries.updatedBy.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.employeeSalaries.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./employee-salaries-update.component.ts">
</script>
