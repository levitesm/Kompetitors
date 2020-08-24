<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.employeeRole.home.createOrEditLabel" v-text="$t('kompetitors2App.employeeRole.home.createOrEditLabel')">Create or edit a EmployeeRole</h2>
                <div>
                    <div class="form-group" v-if="employeeRole.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="employeeRole.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.employeeRole.name')" for="employee-role-name">Name</label>
                        <input type="text" class="form-control" name="name" id="employee-role-name"
                            :class="{'valid': !$v.employeeRole.name.$invalid, 'invalid': $v.employeeRole.name.$invalid }" v-model="$v.employeeRole.name.$model"  required/>
                        <div v-if="$v.employeeRole.name.$anyDirty && $v.employeeRole.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.employeeRole.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.employeeRole.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./employee-role-update.component.ts">
</script>
