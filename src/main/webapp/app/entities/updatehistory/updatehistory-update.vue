<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.updatehistory.home.createOrEditLabel" v-text="$t('kompetitors2App.updatehistory.home.createOrEditLabel')">Create or edit a Updatehistory</h2>
                <div>
                    <div class="form-group" v-if="updatehistory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="updatehistory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.updatehistory.type')" for="updatehistory-type">Type</label>
                        <input type="text" class="form-control" name="type" id="updatehistory-type"
                            :class="{'valid': !$v.updatehistory.type.$invalid, 'invalid': $v.updatehistory.type.$invalid }" v-model="$v.updatehistory.type.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.updatehistory.siren')" for="updatehistory-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="updatehistory-siren"
                            :class="{'valid': !$v.updatehistory.siren.$invalid, 'invalid': $v.updatehistory.siren.$invalid }" v-model="$v.updatehistory.siren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.updatehistory.date')" for="updatehistory-date">Date</label>
                        <div class="input-group">
                            <input id="updatehistory-date" type="date" class="form-control" name="date"  :class="{'valid': !$v.updatehistory.date.$invalid, 'invalid': $v.updatehistory.date.$invalid }"
                            v-model="$v.updatehistory.date.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.updatehistory.status')" for="updatehistory-status">Status</label>
                        <input type="text" class="form-control" name="status" id="updatehistory-status"
                            :class="{'valid': !$v.updatehistory.status.$invalid, 'invalid': $v.updatehistory.status.$invalid }" v-model="$v.updatehistory.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.updatehistory.responce')" for="updatehistory-responce">Responce</label>
                        <input type="text" class="form-control" name="responce" id="updatehistory-responce"
                            :class="{'valid': !$v.updatehistory.responce.$invalid, 'invalid': $v.updatehistory.responce.$invalid }" v-model="$v.updatehistory.responce.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.updatehistory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./updatehistory-update.component.ts">
</script>
