<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.clients.home.createOrEditLabel" v-text="$t('kompetitors2App.clients.home.createOrEditLabel')">Create or edit a Clients</h2>
                <div>
                    <div class="form-group" v-if="clients.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="clients.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.clients.officeName')" for="clients-officeName">Office Name</label>
                        <input type="text" class="form-control" name="officeName" id="clients-officeName"
                            :class="{'valid': !$v.clients.officeName.$invalid, 'invalid': $v.clients.officeName.$invalid }" v-model="$v.clients.officeName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.clients.name')" for="clients-name">Name</label>
                        <input type="text" class="form-control" name="name" id="clients-name"
                            :class="{'valid': !$v.clients.name.$invalid, 'invalid': $v.clients.name.$invalid }" v-model="$v.clients.name.$model"  required/>
                        <div v-if="$v.clients.name.$anyDirty && $v.clients.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.clients.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.clients.since')" for="clients-since">Since</label>
                        <input type="text" class="form-control" name="since" id="clients-since"
                            :class="{'valid': !$v.clients.since.$invalid, 'invalid': $v.clients.since.$invalid }" v-model="$v.clients.since.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.clients.isIppon')" for="clients-isIppon">Is Ippon</label>
                        <input type="checkbox" class="form-check" name="isIppon" id="clients-isIppon"
                            :class="{'valid': !$v.clients.isIppon.$invalid, 'invalid': $v.clients.isIppon.$invalid }" v-model="$v.clients.isIppon.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.clients.updateDate')" for="clients-updateDate">Update Date</label>
                        <div class="input-group">
                            <input id="clients-updateDate" type="date" class="form-control" name="updateDate"  :class="{'valid': !$v.clients.updateDate.$invalid, 'invalid': $v.clients.updateDate.$invalid }"
                            v-model="$v.clients.updateDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.clients.industry')" for="clients-industry">Industry</label>
                        <select class="form-control" id="clients-industry" name="industry" v-model="clients.industry">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="clients.industry && listIndustriesOption.id === clients.industry.id ? clients.industry : listIndustriesOption" v-for="listIndustriesOption in listIndustries" :key="listIndustriesOption.id">{{listIndustriesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.clients.offices')" for="clients-offices">Offices</label>
                        <select class="form-control" id="clients-offices" name="offices" v-model="clients.offices">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="clients.offices && officesOption.id === clients.offices.id ? clients.offices : officesOption" v-for="officesOption in offices" :key="officesOption.id">{{officesOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.clients.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./clients-update.component.ts">
</script>
