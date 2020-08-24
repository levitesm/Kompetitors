<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.annualAccount.home.createOrEditLabel" v-text="$t('kompetitors2App.annualAccount.home.createOrEditLabel')">Create or edit a AnnualAccount</h2>
                <div>
                    <div class="form-group" v-if="annualAccount.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="annualAccount.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccount.siren')" for="annual-account-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="annual-account-siren"
                            :class="{'valid': !$v.annualAccount.siren.$invalid, 'invalid': $v.annualAccount.siren.$invalid }" v-model="$v.annualAccount.siren.$model"  required/>
                        <div v-if="$v.annualAccount.siren.$anyDirty && $v.annualAccount.siren.$invalid">
                            <small class="form-text text-danger" v-if="!$v.annualAccount.siren.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccount.year')" for="annual-account-year">Year</label>
                        <input type="number" class="form-control" name="year" id="annual-account-year"
                            :class="{'valid': !$v.annualAccount.year.$invalid, 'invalid': $v.annualAccount.year.$invalid }" v-model.number="$v.annualAccount.year.$model"  required/>
                        <div v-if="$v.annualAccount.year.$anyDirty && $v.annualAccount.year.$invalid">
                            <small class="form-text text-danger" v-if="!$v.annualAccount.year.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.annualAccount.year.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccount.code')" for="annual-account-code">Code</label>
                        <input type="text" class="form-control" name="code" id="annual-account-code"
                            :class="{'valid': !$v.annualAccount.code.$invalid, 'invalid': $v.annualAccount.code.$invalid }" v-model="$v.annualAccount.code.$model"  required/>
                        <div v-if="$v.annualAccount.code.$anyDirty && $v.annualAccount.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.annualAccount.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccount.value')" for="annual-account-value">Value</label>
                        <input type="number" class="form-control" name="value" id="annual-account-value"
                            :class="{'valid': !$v.annualAccount.value.$invalid, 'invalid': $v.annualAccount.value.$invalid }" v-model.number="$v.annualAccount.value.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.annualAccount.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./annual-account-update.component.ts">
</script>
