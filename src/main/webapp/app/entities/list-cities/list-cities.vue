<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listCities.home.title')" id="list-cities-heading">List Cities</span>
            <router-link :to="{name: 'ListCitiesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-cities">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listCities.home.createLabel')">
                    Create a new List Cities
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
        <div class="alert alert-warning" v-if="!isFetching && listCities && listCities.length === 0">
            <span v-text="$t('kompetitors2App.listCities.home.notFound')">No listCities found</span>
        </div>
        <div class="table-responsive" v-if="listCities && listCities.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listCities.value')">Value</span></th>
                    <th><span v-text="$t('kompetitors2App.listCities.country')">Country</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listCities in listCities"
                    :key="listCities.id">
                    <td>
                        <router-link :to="{name: 'ListCitiesView', params: {listCitiesId: listCities.id}}">{{listCities.id}}</router-link>
                    </td>
                    <td>{{listCities.value}}</td>
                    <td>
                        <div v-if="listCities.country">
                            <router-link :to="{name: 'ListCityCountriesView', params: {countryId: listCities.country.id}}">{{listCities.country.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListCitiesView', params: {listCitiesId: listCities.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListCitiesEdit', params: {listCitiesId: listCities.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listCities)"
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
            <span slot="modal-title"><span id="kompetitors2App.listCities.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listCities-heading" v-bind:title="$t('kompetitors2App.listCities.delete.question')">Are you sure you want to delete this List Cities?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listCities" v-text="$t('entity.action.delete')" v-on:click="removeListCities()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-cities.component.ts">
</script>
