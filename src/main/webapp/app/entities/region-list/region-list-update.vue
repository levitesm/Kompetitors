<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.regionList.home.createOrEditLabel" v-text="$t('kompetitors2App.regionList.home.createOrEditLabel')">Create or edit a RegionList</h2>
                <div>
                    <div class="form-group" v-if="regionList.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="regionList.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.regionList.region')" for="region-list-region">Region</label>
                        <input type="text" class="form-control" name="region" id="region-list-region"
                            :class="{'valid': !$v.regionList.region.$invalid, 'invalid': $v.regionList.region.$invalid }" v-model="$v.regionList.region.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.regionList.country')" for="region-list-country">Country</label>
                        <input type="text" class="form-control" name="country" id="region-list-country"
                            :class="{'valid': !$v.regionList.country.$invalid, 'invalid': $v.regionList.country.$invalid }" v-model="$v.regionList.country.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.regionList.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./region-list-update.component.ts">
</script>
