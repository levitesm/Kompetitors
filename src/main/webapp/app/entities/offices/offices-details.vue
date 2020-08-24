<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="offices">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.offices.detail.title')">Offices</span> {{offices.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{offices.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.address')">Address</span>
                    </dt>
                    <dd>
                        <span>{{offices.address}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.phone')">Phone</span>
                    </dt>
                    <dd>
                        <span>{{offices.phone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.post')">Post</span>
                    </dt>
                    <dd>
                        <span>{{offices.post}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.cityAsText')">City As Text</span>
                    </dt>
                    <dd>
                        <span>{{offices.cityAsText}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.numberEmployees')">Number Employees</span>
                    </dt>
                    <dd>
                        <span>{{offices.numberEmployees}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.numberConsultants')">Number Consultants</span>
                    </dt>
                    <dd>
                        <span>{{offices.numberConsultants}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.numberTechnicals')">Number Technicals</span>
                    </dt>
                    <dd>
                        <span>{{offices.numberTechnicals}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.numberHR')">Number HR</span>
                    </dt>
                    <dd>
                        <span>{{offices.numberHR}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.numberClients')">Number Clients</span>
                    </dt>
                    <dd>
                        <span>{{offices.numberClients}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.established')">Established</span>
                    </dt>
                    <dd>
                        <span>{{offices.established}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.isMainOffice')">Is Main Office</span>
                    </dt>
                    <dd>
                        <span>{{offices.isMainOffice}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.latitude')">Latitude</span>
                    </dt>
                    <dd>
                        <span>{{offices.latitude}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.longitude')">Longitude</span>
                    </dt>
                    <dd>
                        <span>{{offices.longitude}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.city')">City</span>
                    </dt>
                    <dd>
                        <div v-if="offices.city">
                            <router-link :to="{name: 'ListCitiesView', params: {cityId: offices.city.id}}">{{offices.city.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.offices.competitors')">Competitors</span>
                    </dt>
                    <dd>
                        <div v-if="offices.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: offices.competitors.id}}">{{offices.competitors.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="offices.id" :to="{name: 'OfficesEdit', params: {officesId: offices.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./offices-details.component.ts">
</script>
