<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.listCities.home.createOrEditLabel" v-text="$t('kompetitors2App.listCities.home.createOrEditLabel')">Create or edit a ListCities</h2>
                <div>
                    <div class="form-group" v-if="listCities.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="listCities.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.listCities.value')" for="list-cities-value">Value</label>
                        <input type="text" class="form-control" name="value" id="list-cities-value"
                            :class="{'valid': !$v.listCities.value.$invalid, 'invalid': $v.listCities.value.$invalid }" v-model="$v.listCities.value.$model"  required/>
                        <div v-if="$v.listCities.value.$anyDirty && $v.listCities.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.listCities.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.listCities.value.minLength" v-bind:value="$t('entity.validation.minlength')">
                                This field is required to be at least 3 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.listCities.country')" for="list-cities-country">Country</label>
                        <select class="form-control" id="list-cities-country" name="country" v-model="listCities.country">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="listCities.country && listCityCountriesOption.id === listCities.country.id ? listCities.country : listCityCountriesOption" v-for="listCityCountriesOption in listCityCountries" :key="listCityCountriesOption.id">{{listCityCountriesOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.listCities.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./list-cities-update.component.ts">
</script>
