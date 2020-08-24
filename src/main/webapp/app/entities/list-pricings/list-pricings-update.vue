<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.listPricings.home.createOrEditLabel" v-text="$t('kompetitors2App.listPricings.home.createOrEditLabel')">Create or edit a ListPricings</h2>
                <div>
                    <div class="form-group" v-if="listPricings.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="listPricings.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.listPricings.value')" for="list-pricings-value">Value</label>
                        <input type="text" class="form-control" name="value" id="list-pricings-value"
                            :class="{'valid': !$v.listPricings.value.$invalid, 'invalid': $v.listPricings.value.$invalid }" v-model="$v.listPricings.value.$model"  required/>
                        <div v-if="$v.listPricings.value.$anyDirty && $v.listPricings.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.listPricings.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.listPricings.value.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 3 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.listPricings.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./list-pricings-update.component.ts">
</script>
