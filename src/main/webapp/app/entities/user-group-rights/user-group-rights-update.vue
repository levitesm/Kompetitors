<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.userGroupRights.home.createOrEditLabel" v-text="$t('kompetitors2App.userGroupRights.home.createOrEditLabel')">Create or edit a UserGroupRights</h2>
                <div>
                    <div class="form-group" v-if="userGroupRights.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="userGroupRights.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.userGroupRights.userGroupName')" for="user-group-rights-userGroupName">User Group Name</label>
                        <input type="text" class="form-control" name="userGroupName" id="user-group-rights-userGroupName"
                            :class="{'valid': !$v.userGroupRights.userGroupName.$invalid, 'invalid': $v.userGroupRights.userGroupName.$invalid }" v-model="$v.userGroupRights.userGroupName.$model"  required/>
                        <div v-if="$v.userGroupRights.userGroupName.$anyDirty && $v.userGroupRights.userGroupName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userGroupRights.userGroupName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.userGroupRights.accessKey')" for="user-group-rights-accessKey">Access Key</label>
                        <select class="form-control" id="user-group-rights-accessKey" name="accessKey" v-model="userGroupRights.accessKey">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userGroupRights.accessKey && accessKeyOption.id === userGroupRights.accessKey.id ? userGroupRights.accessKey : accessKeyOption" v-for="accessKeyOption in accessKeys" :key="accessKeyOption.id">{{accessKeyOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.userGroupRights.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./user-group-rights-update.component.ts">
</script>
