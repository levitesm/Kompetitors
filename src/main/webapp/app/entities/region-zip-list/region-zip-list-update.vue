<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.regionZipList.home.createOrEditLabel" v-text="$t('kompetitors2App.regionZipList.home.createOrEditLabel')">Create or edit a RegionZipList</h2>
                <div>
                    <div class="form-group" v-if="regionZipList.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="regionZipList.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.regionZipList.region')" for="region-zip-list-region">Region</label>
                        <input type="text" class="form-control" name="region" id="region-zip-list-region"
                            :class="{'valid': !$v.regionZipList.region.$invalid, 'invalid': $v.regionZipList.region.$invalid }" v-model="$v.regionZipList.region.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.regionZipList.zip')" for="region-zip-list-zip">Zip</label>
                        <input type="text" class="form-control" name="zip" id="region-zip-list-zip"
                            :class="{'valid': !$v.regionZipList.zip.$invalid, 'invalid': $v.regionZipList.zip.$invalid }" v-model="$v.regionZipList.zip.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.regionZipList.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./region-zip-list-update.component.ts">
</script>
