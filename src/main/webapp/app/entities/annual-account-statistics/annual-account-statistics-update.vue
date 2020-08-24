<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.annualAccountStatistics.home.createOrEditLabel" v-text="$t('kompetitors2App.annualAccountStatistics.home.createOrEditLabel')">Create or edit a AnnualAccountStatistics</h2>
                <div>
                    <div class="form-group" v-if="annualAccountStatistics.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="annualAccountStatistics.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccountStatistics.siren')" for="annual-account-statistics-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="annual-account-statistics-siren"
                            :class="{'valid': !$v.annualAccountStatistics.siren.$invalid, 'invalid': $v.annualAccountStatistics.siren.$invalid }" v-model="$v.annualAccountStatistics.siren.$model"  required/>
                        <div v-if="$v.annualAccountStatistics.siren.$anyDirty && $v.annualAccountStatistics.siren.$invalid">
                            <small class="form-text text-danger" v-if="!$v.annualAccountStatistics.siren.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccountStatistics.year')" for="annual-account-statistics-year">Year</label>
                        <input type="number" class="form-control" name="year" id="annual-account-statistics-year"
                            :class="{'valid': !$v.annualAccountStatistics.year.$invalid, 'invalid': $v.annualAccountStatistics.year.$invalid }" v-model.number="$v.annualAccountStatistics.year.$model"  required/>
                        <div v-if="$v.annualAccountStatistics.year.$anyDirty && $v.annualAccountStatistics.year.$invalid">
                            <small class="form-text text-danger" v-if="!$v.annualAccountStatistics.year.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.annualAccountStatistics.year.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccountStatistics.code')" for="annual-account-statistics-code">Code</label>
                        <input type="number" class="form-control" name="code" id="annual-account-statistics-code"
                            :class="{'valid': !$v.annualAccountStatistics.code.$invalid, 'invalid': $v.annualAccountStatistics.code.$invalid }" v-model.number="$v.annualAccountStatistics.code.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccountStatistics.message')" for="annual-account-statistics-message">Message</label>
                        <input type="text" class="form-control" name="message" id="annual-account-statistics-message"
                            :class="{'valid': !$v.annualAccountStatistics.message.$invalid, 'invalid': $v.annualAccountStatistics.message.$invalid }" v-model="$v.annualAccountStatistics.message.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.annualAccountStatistics.modified')" for="annual-account-statistics-modified">Modified</label>
                        <div class="d-flex">
                            <input id="annual-account-statistics-modified" type="datetime-local" class="form-control" name="modified" :class="{'valid': !$v.annualAccountStatistics.modified.$invalid, 'invalid': $v.annualAccountStatistics.modified.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.annualAccountStatistics.modified.$model)"
                            @change="updateInstantField('modified', $event)"/>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.annualAccountStatistics.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./annual-account-statistics-update.component.ts">
</script>
