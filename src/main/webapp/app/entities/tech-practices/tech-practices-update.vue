<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.techPractices.home.createOrEditLabel" v-text="$t('kompetitors2App.techPractices.home.createOrEditLabel')">Create or edit a TechPractices</h2>
                <div>
                    <div class="form-group" v-if="techPractices.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="techPractices.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.techPractices.value')" for="tech-practices-value">Value</label>
                        <select class="form-control" id="tech-practices-value" name="value" v-model="techPractices.valueId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="listPracticesOption.id" v-for="listPracticesOption in listPractices" :key="listPracticesOption.id">{{listPracticesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.techPractices.competitor')" for="tech-practices-competitor">Competitor</label>
                        <select class="form-control" id="tech-practices-competitor" name="competitor" v-model="techPractices.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.techPractices.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tech-practices-update.component.ts">
</script>
