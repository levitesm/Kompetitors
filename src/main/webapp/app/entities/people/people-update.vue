<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.people.home.createOrEditLabel" v-text="$t('kompetitors2App.people.home.createOrEditLabel')">Create or edit a People</h2>
                <div>
                    <div class="form-group" v-if="people.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="people.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.people.fName')" for="people-fName">F Name</label>
                        <input type="text" class="form-control" name="fName" id="people-fName"
                            :class="{'valid': !$v.people.fName.$invalid, 'invalid': $v.people.fName.$invalid }" v-model="$v.people.fName.$model"  required/>
                        <div v-if="$v.people.fName.$anyDirty && $v.people.fName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.people.fName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.people.lName')" for="people-lName">L Name</label>
                        <input type="text" class="form-control" name="lName" id="people-lName"
                            :class="{'valid': !$v.people.lName.$invalid, 'invalid': $v.people.lName.$invalid }" v-model="$v.people.lName.$model"  required/>
                        <div v-if="$v.people.lName.$anyDirty && $v.people.lName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.people.lName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.people.title')" for="people-title">Title</label>
                        <input type="text" class="form-control" name="title" id="people-title"
                            :class="{'valid': !$v.people.title.$invalid, 'invalid': $v.people.title.$invalid }" v-model="$v.people.title.$model"  required/>
                        <div v-if="$v.people.title.$anyDirty && $v.people.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.people.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.people.linkedPage')" for="people-linkedPage">Linked Page</label>
                        <input type="text" class="form-control" name="linkedPage" id="people-linkedPage"
                            :class="{'valid': !$v.people.linkedPage.$invalid, 'invalid': $v.people.linkedPage.$invalid }" v-model="$v.people.linkedPage.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.people.isKey')" for="people-isKey">Is Key</label>
                        <input type="checkbox" class="form-check" name="isKey" id="people-isKey"
                            :class="{'valid': !$v.people.isKey.$invalid, 'invalid': $v.people.isKey.$invalid }" v-model="$v.people.isKey.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.people.competitors')" for="people-competitors">Competitors</label>
                        <select class="form-control" id="people-competitors" name="competitors" v-model="people.competitors">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="people.competitors && competitorsOption.id === people.competitors.id ? people.competitors : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.people.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./people-update.component.ts">
</script>
