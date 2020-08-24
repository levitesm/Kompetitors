<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.competitors.home.createOrEditLabel" v-text="$t('kompetitors2App.competitors.home.createOrEditLabel')">Create or edit a Competitors</h2>
                <div>
                    <div class="form-group" v-if="competitors.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitors.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitors.name')" for="competitors-name">Name</label>
                        <input type="text" class="form-control" name="name" id="competitors-name"
                            :class="{'valid': !$v.competitors.name.$invalid, 'invalid': $v.competitors.name.$invalid }" v-model="$v.competitors.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitors.webSite')" for="competitors-webSite">Web Site</label>
                        <input type="text" class="form-control" name="webSite" id="competitors-webSite"
                            :class="{'valid': !$v.competitors.webSite.$invalid, 'invalid': $v.competitors.webSite.$invalid }" v-model="$v.competitors.webSite.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.competitors.countryPhone')" for="competitors-countryPhone">Country Phone</label>
                        <input type="text" class="form-control" name="countryPhone" id="competitors-countryPhone"
                            :class="{'valid': !$v.competitors.countryPhone.$invalid, 'invalid': $v.competitors.countryPhone.$invalid }" v-model="$v.competitors.countryPhone.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.competitors.country')" for="competitors-country">Country</label>
                        <select class="form-control" id="competitors-country" name="country" v-model="competitors.country">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitors.country && listCountriesOption.id === competitors.country.id ? competitors.country : listCountriesOption" v-for="listCountriesOption in listCountries" :key="listCountriesOption.id">{{listCountriesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.competitors.globalGroups')" for="competitors-globalGroups">Global Groups</label>
                        <select class="form-control" id="competitors-globalGroups" name="globalGroups" v-model="competitors.globalGroups">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitors.globalGroups && globalGroupsOption.id === competitors.globalGroups.id ? competitors.globalGroups : globalGroupsOption" v-for="globalGroupsOption in globalGroups" :key="globalGroupsOption.id">{{globalGroupsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitors.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competitors-update.component.ts">
</script>
