<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.competitorIndustry.home.createOrEditLabel" v-text="$t('kompetitors2App.competitorIndustry.home.createOrEditLabel')">Create or edit a CompetitorIndustry</h2>
                <div>
                    <div class="form-group" v-if="competitorIndustry.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitorIndustry.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.competitorIndustry.competitor')" for="competitor-industry-competitor">Competitor</label>
                        <select class="form-control" id="competitor-industry-competitor" name="competitor" v-model="$v.competitorIndustry.competitorId.$model" required>
                            <option v-if="!competitorIndustry.competitorId" v-bind:value="null" selected></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.competitorIndustry.competitorId.$anyDirty && $v.competitorIndustry.competitorId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.competitorIndustry.competitorId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.competitorIndustry.industry')" for="competitor-industry-industry">Industry</label>
                        <select class="form-control" id="competitor-industry-industry" name="industry" v-model="$v.competitorIndustry.industryId.$model" required>
                            <option v-if="!competitorIndustry.industryId" v-bind:value="null" selected></option>
                            <option v-bind:value="listIndustriesOption.id" v-for="listIndustriesOption in listIndustries" :key="listIndustriesOption.id">{{listIndustriesOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.competitorIndustry.industryId.$anyDirty && $v.competitorIndustry.industryId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.competitorIndustry.industryId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitorIndustry.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competitor-industry-update.component.ts">
</script>
