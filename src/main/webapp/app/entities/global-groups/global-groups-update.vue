<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.globalGroups.home.createOrEditLabel" v-text="$t('kompetitors2App.globalGroups.home.createOrEditLabel')">Create or edit a GlobalGroups</h2>
                <div>
                    <div class="form-group" v-if="globalGroups.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="globalGroups.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.globalGroups.name')" for="global-groups-name">Name</label>
                        <input type="text" class="form-control" name="name" id="global-groups-name"
                            :class="{'valid': !$v.globalGroups.name.$invalid, 'invalid': $v.globalGroups.name.$invalid }" v-model="$v.globalGroups.name.$model"  required/>
                        <div v-if="$v.globalGroups.name.$anyDirty && $v.globalGroups.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.globalGroups.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.globalGroups.logo')" for="global-groups-logo">Logo</label>
                        <div>
                            <div v-if="globalGroups.logo" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(globalGroups.logoContentType, globalGroups.logo)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{globalGroups.logoContentType}}, {{byteSize(globalGroups.logo)}}</span>
                                <button type="button" v-on:click="globalGroups.logo=null;globalGroups.logoContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_logo" id="file_logo" v-on:change="setFileData($event, globalGroups, 'logo', true)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="logo" id="global-groups-logo"
                            :class="{'valid': !$v.globalGroups.logo.$invalid, 'invalid': $v.globalGroups.logo.$invalid }" v-model="$v.globalGroups.logo.$model" />
                        <input type="hidden" class="form-control" name="logoContentType" id="global-groups-logoContentType"
                            v-model="globalGroups.logoContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.globalGroups.webSite')" for="global-groups-webSite">Web Site</label>
                        <input type="text" class="form-control" name="webSite" id="global-groups-webSite"
                            :class="{'valid': !$v.globalGroups.webSite.$invalid, 'invalid': $v.globalGroups.webSite.$invalid }" v-model="$v.globalGroups.webSite.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.globalGroups.reference')" for="global-groups-reference">Reference</label>
                        <input type="checkbox" class="form-check" name="reference" id="global-groups-reference"
                            :class="{'valid': !$v.globalGroups.reference.$invalid, 'invalid': $v.globalGroups.reference.$invalid }" v-model="$v.globalGroups.reference.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.globalGroups.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./global-groups-update.component.ts">
</script>
