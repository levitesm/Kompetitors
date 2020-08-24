<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.offices.home.createOrEditLabel" v-text="$t('kompetitors2App.offices.home.createOrEditLabel')">Create or edit a Offices</h2>
                <div>
                    <div class="form-group" v-if="offices.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="offices.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.name')" for="offices-name">Name</label>
                        <input type="text" class="form-control" name="name" id="offices-name"
                            :class="{'valid': !$v.offices.name.$invalid, 'invalid': $v.offices.name.$invalid }" v-model="$v.offices.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.address')" for="offices-address">Address</label>
                        <input type="text" class="form-control" name="address" id="offices-address"
                            :class="{'valid': !$v.offices.address.$invalid, 'invalid': $v.offices.address.$invalid }" v-model="$v.offices.address.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.phone')" for="offices-phone">Phone</label>
                        <input type="text" class="form-control" name="phone" id="offices-phone"
                            :class="{'valid': !$v.offices.phone.$invalid, 'invalid': $v.offices.phone.$invalid }" v-model="$v.offices.phone.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.post')" for="offices-post">Post</label>
                        <input type="text" class="form-control" name="post" id="offices-post"
                            :class="{'valid': !$v.offices.post.$invalid, 'invalid': $v.offices.post.$invalid }" v-model="$v.offices.post.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.cityAsText')" for="offices-cityAsText">City As Text</label>
                        <input type="text" class="form-control" name="cityAsText" id="offices-cityAsText"
                            :class="{'valid': !$v.offices.cityAsText.$invalid, 'invalid': $v.offices.cityAsText.$invalid }" v-model="$v.offices.cityAsText.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.numberEmployees')" for="offices-numberEmployees">Number Employees</label>
                        <input type="number" class="form-control" name="numberEmployees" id="offices-numberEmployees"
                            :class="{'valid': !$v.offices.numberEmployees.$invalid, 'invalid': $v.offices.numberEmployees.$invalid }" v-model.number="$v.offices.numberEmployees.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.numberConsultants')" for="offices-numberConsultants">Number Consultants</label>
                        <input type="number" class="form-control" name="numberConsultants" id="offices-numberConsultants"
                            :class="{'valid': !$v.offices.numberConsultants.$invalid, 'invalid': $v.offices.numberConsultants.$invalid }" v-model.number="$v.offices.numberConsultants.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.numberTechnicals')" for="offices-numberTechnicals">Number Technicals</label>
                        <input type="number" class="form-control" name="numberTechnicals" id="offices-numberTechnicals"
                            :class="{'valid': !$v.offices.numberTechnicals.$invalid, 'invalid': $v.offices.numberTechnicals.$invalid }" v-model.number="$v.offices.numberTechnicals.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.numberHR')" for="offices-numberHR">Number HR</label>
                        <input type="number" class="form-control" name="numberHR" id="offices-numberHR"
                            :class="{'valid': !$v.offices.numberHR.$invalid, 'invalid': $v.offices.numberHR.$invalid }" v-model.number="$v.offices.numberHR.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.numberClients')" for="offices-numberClients">Number Clients</label>
                        <input type="number" class="form-control" name="numberClients" id="offices-numberClients"
                            :class="{'valid': !$v.offices.numberClients.$invalid, 'invalid': $v.offices.numberClients.$invalid }" v-model.number="$v.offices.numberClients.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.established')" for="offices-established">Established</label>
                        <div class="input-group">
                            <input id="offices-established" type="date" class="form-control" name="established"  :class="{'valid': !$v.offices.established.$invalid, 'invalid': $v.offices.established.$invalid }"
                            v-model="$v.offices.established.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.isMainOffice')" for="offices-isMainOffice">Is Main Office</label>
                        <input type="checkbox" class="form-check" name="isMainOffice" id="offices-isMainOffice"
                            :class="{'valid': !$v.offices.isMainOffice.$invalid, 'invalid': $v.offices.isMainOffice.$invalid }" v-model="$v.offices.isMainOffice.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.latitude')" for="offices-latitude">Latitude</label>
                        <input type="number" class="form-control" name="latitude" id="offices-latitude"
                            :class="{'valid': !$v.offices.latitude.$invalid, 'invalid': $v.offices.latitude.$invalid }" v-model.number="$v.offices.latitude.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.offices.longitude')" for="offices-longitude">Longitude</label>
                        <input type="number" class="form-control" name="longitude" id="offices-longitude"
                            :class="{'valid': !$v.offices.longitude.$invalid, 'invalid': $v.offices.longitude.$invalid }" v-model.number="$v.offices.longitude.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.offices.city')" for="offices-city">City</label>
                        <select class="form-control" id="offices-city" name="city" v-model="offices.city">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="offices.city && listCitiesOption.id === offices.city.id ? offices.city : listCitiesOption" v-for="listCitiesOption in listCities" :key="listCitiesOption.id">{{listCitiesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.offices.competitors')" for="offices-competitors">Competitors</label>
                        <select class="form-control" id="offices-competitors" name="competitors" v-model="offices.competitors">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="offices.competitors && competitorsOption.id === offices.competitors.id ? offices.competitors : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.offices.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./offices-update.component.ts">
</script>
