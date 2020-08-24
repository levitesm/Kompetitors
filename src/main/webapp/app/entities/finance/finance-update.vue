<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.finance.home.createOrEditLabel" v-text="$t('kompetitors2App.finance.home.createOrEditLabel')">Create or edit a Finance</h2>
                <div>
                    <div class="form-group" v-if="finance.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="finance.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.finance.margin')" for="finance-margin">Margin</label>
                        <input type="number" class="form-control" name="margin" id="finance-margin"
                            :class="{'valid': !$v.finance.margin.$invalid, 'invalid': $v.finance.margin.$invalid }" v-model.number="$v.finance.margin.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.finance.ebitda')" for="finance-ebitda">Ebitda</label>
                        <input type="number" class="form-control" name="ebitda" id="finance-ebitda"
                            :class="{'valid': !$v.finance.ebitda.$invalid, 'invalid': $v.finance.ebitda.$invalid }" v-model.number="$v.finance.ebitda.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.finance.occupationRate')" for="finance-occupationRate">Occupation Rate</label>
                        <input type="number" class="form-control" name="occupationRate" id="finance-occupationRate"
                            :class="{'valid': !$v.finance.occupationRate.$invalid, 'invalid': $v.finance.occupationRate.$invalid }" v-model.number="$v.finance.occupationRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.finance.revenue')" for="finance-revenue">Revenue</label>
                        <input type="number" class="form-control" name="revenue" id="finance-revenue"
                            :class="{'valid': !$v.finance.revenue.$invalid, 'invalid': $v.finance.revenue.$invalid }" v-model.number="$v.finance.revenue.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.finance.year')" for="finance-year">Year</label>
                        <input type="number" class="form-control" name="year" id="finance-year"
                            :class="{'valid': !$v.finance.year.$invalid, 'invalid': $v.finance.year.$invalid }" v-model.number="$v.finance.year.$model"  required/>
                        <div v-if="$v.finance.year.$anyDirty && $v.finance.year.$invalid">
                            <small class="form-text text-danger" v-if="!$v.finance.year.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.finance.year.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.finance.competitors')" for="finance-competitors">Competitors</label>
                        <select class="form-control" id="finance-competitors" name="competitors" v-model="finance.competitors">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="finance.competitors && competitorsOption.id === finance.competitors.id ? finance.competitors : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.finance.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./finance-update.component.ts">
</script>
