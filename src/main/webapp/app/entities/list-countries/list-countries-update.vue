<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.listCountries.home.createOrEditLabel" v-text="$t('kompetitors2App.listCountries.home.createOrEditLabel')">Create or edit a ListCountries</h2>
                <div>
                    <div class="form-group" v-if="listCountries.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="listCountries.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.listCountries.value')" for="list-countries-value">Value</label>
                        <input type="text" class="form-control" name="value" id="list-countries-value"
                            :class="{'valid': !$v.listCountries.value.$invalid, 'invalid': $v.listCountries.value.$invalid }" v-model="$v.listCountries.value.$model"  required/>
                        <div v-if="$v.listCountries.value.$anyDirty && $v.listCountries.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.listCountries.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.listCountries.value.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 3 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.listCountries.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./list-countries-update.component.ts">
</script>
