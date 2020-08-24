<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.dialogs.home.createOrEditLabel" v-text="$t('kompetitors2App.dialogs.home.createOrEditLabel')">Create or edit a Dialogs</h2>
                <div>
                    <div class="form-group" v-if="dialogs.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="dialogs.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dialogs.section')" for="dialogs-section">Section</label>
                        <input type="text" class="form-control" name="section" id="dialogs-section"
                            :class="{'valid': !$v.dialogs.section.$invalid, 'invalid': $v.dialogs.section.$invalid }" v-model="$v.dialogs.section.$model"  required/>
                        <div v-if="$v.dialogs.section.$anyDirty && $v.dialogs.section.$invalid">
                            <small class="form-text text-danger" v-if="!$v.dialogs.section.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dialogs.topic')" for="dialogs-topic">Topic</label>
                        <input type="text" class="form-control" name="topic" id="dialogs-topic"
                            :class="{'valid': !$v.dialogs.topic.$invalid, 'invalid': $v.dialogs.topic.$invalid }" v-model="$v.dialogs.topic.$model"  required/>
                        <div v-if="$v.dialogs.topic.$anyDirty && $v.dialogs.topic.$invalid">
                            <small class="form-text text-danger" v-if="!$v.dialogs.topic.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dialogs.message')" for="dialogs-message">Message</label>
                        <input type="text" class="form-control" name="message" id="dialogs-message"
                            :class="{'valid': !$v.dialogs.message.$invalid, 'invalid': $v.dialogs.message.$invalid }" v-model="$v.dialogs.message.$model"  required/>
                        <div v-if="$v.dialogs.message.$anyDirty && $v.dialogs.message.$invalid">
                            <small class="form-text text-danger" v-if="!$v.dialogs.message.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dialogs.author')" for="dialogs-author">Author</label>
                        <input type="text" class="form-control" name="author" id="dialogs-author"
                            :class="{'valid': !$v.dialogs.author.$invalid, 'invalid': $v.dialogs.author.$invalid }" v-model="$v.dialogs.author.$model"  required/>
                        <div v-if="$v.dialogs.author.$anyDirty && $v.dialogs.author.$invalid">
                            <small class="form-text text-danger" v-if="!$v.dialogs.author.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.dialogs.date')" for="dialogs-date">Date</label>
                        <div class="input-group">
                            <input id="dialogs-date" type="date" class="form-control" name="date"  :class="{'valid': !$v.dialogs.date.$invalid, 'invalid': $v.dialogs.date.$invalid }"
                            v-model="$v.dialogs.date.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.dialogs.competitors')" for="dialogs-competitors">Competitors</label>
                        <select class="form-control" id="dialogs-competitors" name="competitors" v-model="dialogs.competitors">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="dialogs.competitors && competitorsOption.id === dialogs.competitors.id ? dialogs.competitors : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.dialogs.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./dialogs-update.component.ts">
</script>
