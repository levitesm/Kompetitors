<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.listPractices.home.createOrEditLabel" v-text="$t('kompetitors2App.listPractices.home.createOrEditLabel')">Create or edit a ListPractices</h2>
                <div>
                    <div class="form-group" v-if="listPractices.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="listPractices.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.listPractices.value')" for="list-practices-value">Value</label>
                        <input type="text" class="form-control" name="value" id="list-practices-value"
                            :class="{'valid': !$v.listPractices.value.$invalid, 'invalid': $v.listPractices.value.$invalid }" v-model="$v.listPractices.value.$model"  required/>
                        <div v-if="$v.listPractices.value.$anyDirty && $v.listPractices.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.listPractices.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.listPractices.value.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 3 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.listPractices.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./list-practices-update.component.ts">
</script>
