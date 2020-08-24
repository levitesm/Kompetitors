<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.techCompetancies.home.createOrEditLabel" v-text="$t('kompetitors2App.techCompetancies.home.createOrEditLabel')">Create or edit a TechCompetancies</h2>
                <div>
                    <div class="form-group" v-if="techCompetancies.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="techCompetancies.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.techCompetancies.value')" for="tech-competancies-value">Value</label>
                        <select class="form-control" id="tech-competancies-value" name="value" v-model="techCompetancies.valueId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="listCompetanciesOption.id" v-for="listCompetanciesOption in listCompetancies" :key="listCompetanciesOption.id">{{listCompetanciesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.techCompetancies.competitor')" for="tech-competancies-competitor">Competitor</label>
                        <select class="form-control" id="tech-competancies-competitor" name="competitor" v-model="techCompetancies.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.techCompetancies.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tech-competancies-update.component.ts">
</script>
