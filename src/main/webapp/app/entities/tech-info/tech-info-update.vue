<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.techInfo.home.createOrEditLabel" v-text="$t('kompetitors2App.techInfo.home.createOrEditLabel')">Create or edit a TechInfo</h2>
                <div>
                    <div class="form-group" v-if="techInfo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="techInfo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.techInfo.techSpecialistsNumber')" for="tech-info-techSpecialistsNumber">Tech Specialists Number</label>
                        <input type="number" class="form-control" name="techSpecialistsNumber" id="tech-info-techSpecialistsNumber"
                            :class="{'valid': !$v.techInfo.techSpecialistsNumber.$invalid, 'invalid': $v.techInfo.techSpecialistsNumber.$invalid }" v-model.number="$v.techInfo.techSpecialistsNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.techInfo.competitor')" for="tech-info-competitor">Competitor</label>
                        <select class="form-control" id="tech-info-competitor" name="competitor" v-model="techInfo.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.techInfo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tech-info-update.component.ts">
</script>
