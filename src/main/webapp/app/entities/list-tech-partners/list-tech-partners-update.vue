<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.listTechPartners.home.createOrEditLabel" v-text="$t('kompetitors2App.listTechPartners.home.createOrEditLabel')">Create or edit a ListTechPartners</h2>
                <div>
                    <div class="form-group" v-if="listTechPartners.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="listTechPartners.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.listTechPartners.value')" for="list-tech-partners-value">Value</label>
                        <input type="text" class="form-control" name="value" id="list-tech-partners-value"
                            :class="{'valid': !$v.listTechPartners.value.$invalid, 'invalid': $v.listTechPartners.value.$invalid }" v-model="$v.listTechPartners.value.$model"  required/>
                        <div v-if="$v.listTechPartners.value.$anyDirty && $v.listTechPartners.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.listTechPartners.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.listTechPartners.value.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 3 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.listTechPartners.image')" for="list-tech-partners-image">Image</label>
                        <div>
                            <img v-bind:src="'data:' + listTechPartners.imageContentType + ';base64,' + listTechPartners.image" style="max-height: 100px;" v-if="listTechPartners.image" alt="listTechPartners image"/>
                            <div v-if="listTechPartners.image" class="form-text text-danger clearfix">
                                <span class="pull-left">{{listTechPartners.imageContentType}}, {{byteSize(listTechPartners.image)}}</span>
                                <button type="button" v-on:click="clearInputImage('image', 'imageContentType', 'file_image')" class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_image" id="file_image" v-on:change="setFileData($event, listTechPartners, 'image', true)" accept="image/*" v-text="$t('entity.action.addimage')"/>
                        </div>
                        <input type="hidden" class="form-control" name="image" id="list-tech-partners-image"
                            :class="{'valid': !$v.listTechPartners.image.$invalid, 'invalid': $v.listTechPartners.image.$invalid }" v-model="$v.listTechPartners.image.$model" />
                        <input type="hidden" class="form-control" name="imageContentType" id="list-tech-partners-imageContentType"
                            v-model="listTechPartners.imageContentType" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.listTechPartners.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./list-tech-partners-update.component.ts">
</script>
