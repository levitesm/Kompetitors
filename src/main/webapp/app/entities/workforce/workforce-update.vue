<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.workforce.home.createOrEditLabel" v-text="$t('kompetitors2App.workforce.home.createOrEditLabel')">Create or edit a Workforce</h2>
                <div>
                    <div class="form-group" v-if="workforce.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="workforce.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.workforce.employeeNumber')" for="workforce-employeeNumber">Employee Number</label>
                        <input type="number" class="form-control" name="employeeNumber" id="workforce-employeeNumber"
                            :class="{'valid': !$v.workforce.employeeNumber.$invalid, 'invalid': $v.workforce.employeeNumber.$invalid }" v-model.number="$v.workforce.employeeNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.workforce.year')" for="workforce-year">Year</label>
                        <div class="input-group">
                            <input id="workforce-year" type="date" class="form-control" name="year"  :class="{'valid': !$v.workforce.year.$invalid, 'invalid': $v.workforce.year.$invalid }"
                            v-model="$v.workforce.year.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.workforce.competitor')" for="workforce-competitor">Competitor</label>
                        <select class="form-control" id="workforce-competitor" name="competitor" v-model="workforce.competitor" required>
                            <option v-if="!workforce.competitor" v-bind:value="null" selected></option>
                            <option v-bind:value="workforce.competitor && competitorsOption.id === workforce.competitor.id ? workforce.competitor : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.workforce.competitor.$anyDirty && $v.workforce.competitor.$invalid">
                        <small class="form-text text-danger" v-if="!$v.workforce.competitor.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.workforce.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./workforce-update.component.ts">
</script>
