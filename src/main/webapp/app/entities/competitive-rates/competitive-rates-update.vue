<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.competitiveRates.home.createOrEditLabel" v-text="$t('kompetitors2App.competitiveRates.home.createOrEditLabel')">Create or edit a CompetitiveRates</h2>
                <div>
                    <div class="form-group" v-if="competitiveRates.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitiveRates.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitiveRates.totalRate')" for="competitive-rates-totalRate">Total Rate</label>
                        <input type="number" class="form-control" name="totalRate" id="competitive-rates-totalRate"
                            :class="{'valid': !$v.competitiveRates.totalRate.$invalid, 'invalid': $v.competitiveRates.totalRate.$invalid }" v-model.number="$v.competitiveRates.totalRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitiveRates.techRate')" for="competitive-rates-techRate">Tech Rate</label>
                        <input type="number" class="form-control" name="techRate" id="competitive-rates-techRate"
                            :class="{'valid': !$v.competitiveRates.techRate.$invalid, 'invalid': $v.competitiveRates.techRate.$invalid }" v-model.number="$v.competitiveRates.techRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitiveRates.financeRate')" for="competitive-rates-financeRate">Finance Rate</label>
                        <input type="number" class="form-control" name="financeRate" id="competitive-rates-financeRate"
                            :class="{'valid': !$v.competitiveRates.financeRate.$invalid, 'invalid': $v.competitiveRates.financeRate.$invalid }" v-model.number="$v.competitiveRates.financeRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitiveRates.clientsRate')" for="competitive-rates-clientsRate">Clients Rate</label>
                        <input type="number" class="form-control" name="clientsRate" id="competitive-rates-clientsRate"
                            :class="{'valid': !$v.competitiveRates.clientsRate.$invalid, 'invalid': $v.competitiveRates.clientsRate.$invalid }" v-model.number="$v.competitiveRates.clientsRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitiveRates.hrRate')" for="competitive-rates-hrRate">Hr Rate</label>
                        <input type="number" class="form-control" name="hrRate" id="competitive-rates-hrRate"
                            :class="{'valid': !$v.competitiveRates.hrRate.$invalid, 'invalid': $v.competitiveRates.hrRate.$invalid }" v-model.number="$v.competitiveRates.hrRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitiveRates.competitor')" for="competitive-rates-competitor">Competitor</label>
                        <select class="form-control" id="competitive-rates-competitor" name="competitor" v-model="competitiveRates.competitor">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitiveRates.competitor && competitorsOption.id === competitiveRates.competitor.id ? competitiveRates.competitor : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitiveRates.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competitive-rates-update.component.ts">
</script>
