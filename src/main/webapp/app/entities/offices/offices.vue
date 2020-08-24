<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.offices.home.title')" id="offices-heading">Offices</span>
            <router-link :to="{name: 'OfficesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-offices">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.offices.home.createLabel')">
                    Create a new Offices
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && offices && offices.length === 0">
            <span v-text="$t('kompetitors2App.offices.home.notFound')">No offices found</span>
        </div>
        <div class="table-responsive" v-if="offices && offices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.name')">Name</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.address')">Address</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.phone')">Phone</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.post')">Post</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.cityAsText')">City As Text</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.numberEmployees')">Number Employees</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.numberConsultants')">Number Consultants</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.numberTechnicals')">Number Technicals</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.numberHR')">Number HR</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.numberClients')">Number Clients</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.established')">Established</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.isMainOffice')">Is Main Office</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.latitude')">Latitude</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.longitude')">Longitude</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.city')">City</span></th>
                    <th><span v-text="$t('kompetitors2App.offices.competitors')">Competitors</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="offices in offices"
                    :key="offices.id">
                    <td>
                        <router-link :to="{name: 'OfficesView', params: {officesId: offices.id}}">{{offices.id}}</router-link>
                    </td>
                    <td>{{offices.name}}</td>
                    <td>{{offices.address}}</td>
                    <td>{{offices.phone}}</td>
                    <td>{{offices.post}}</td>
                    <td>{{offices.cityAsText}}</td>
                    <td>{{offices.numberEmployees}}</td>
                    <td>{{offices.numberConsultants}}</td>
                    <td>{{offices.numberTechnicals}}</td>
                    <td>{{offices.numberHR}}</td>
                    <td>{{offices.numberClients}}</td>
                    <td>{{offices.established}}</td>
                    <td>{{offices.isMainOffice}}</td>
                    <td>{{offices.latitude}}</td>
                    <td>{{offices.longitude}}</td>
<!--                    <td>{{offices.competitors.id}}</td>-->
                    <td>
                        <div v-if="offices.city">
                            <router-link :to="{name: 'ListCitiesView', params: {cityId: offices.city.id}}">{{offices.city.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="offices.competitors">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: offices.competitors.id}}">{{offices.competitors.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'OfficesView', params: {officesId: offices.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'OfficesEdit', params: {officesId: offices.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(offices)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="kompetitors2App.offices.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-offices-heading" v-bind:title="$t('kompetitors2App.offices.delete.question')">Are you sure you want to delete this Offices?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-offices" v-text="$t('entity.action.delete')" v-on:click="removeOffices()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./offices.component.ts">
</script>
