<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.employeesTypology.home.title')" id="employees-typology-heading">Employees Typologies</span>
            <router-link :to="{name: 'EmployeesTypologyCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-employees-typology">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.employeesTypology.home.createLabel')">
                    Create a new Employees Typology
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
        <div class="alert alert-warning" v-if="!isFetching && employeesTypologies && employeesTypologies.length === 0">
            <span v-text="$t('kompetitors2App.employeesTypology.home.notFound')">No employeesTypologies found</span>
        </div>
        <div class="table-responsive" v-if="employeesTypologies && employeesTypologies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('kompetitors2App.employeesTypology.value')">Value</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('year')"><span v-text="$t('kompetitors2App.employeesTypology.year')">Year</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('employeeType')"><span v-text="$t('kompetitors2App.employeesTypology.employeeType')">Employee Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competitorId')"><span v-text="$t('kompetitors2App.employeesTypology.competitor')">Competitor</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="employeesTypology in employeesTypologies"
                    :key="employeesTypology.id">
                    <td>
                        <router-link :to="{name: 'EmployeesTypologyView', params: {employeesTypologyId: employeesTypology.id}}">{{employeesTypology.id}}</router-link>
                    </td>
                    <td>{{employeesTypology.value}}</td>
                    <td>{{employeesTypology.year}}</td>
                    <td>
                        <div v-if="employeesTypology.employeeTypeId">
                            <router-link :to="{name: 'EmployeeTypeView', params: {employeeTypeId: employeesTypology.employeeTypeId}}">{{employeesTypology.employeeTypeId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="employeesTypology.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: employeesTypology.competitorId}}">{{employeesTypology.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'EmployeesTypologyView', params: {employeesTypologyId: employeesTypology.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'EmployeesTypologyEdit', params: {employeesTypologyId: employeesTypology.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(employeesTypology)"
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
            <span slot="modal-title"><span id="kompetitors2App.employeesTypology.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-employeesTypology-heading" v-bind:title="$t('kompetitors2App.employeesTypology.delete.question')">Are you sure you want to delete this Employees Typology?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-employeesTypology" v-text="$t('entity.action.delete')" v-on:click="removeEmployeesTypology()">Delete</button>
            </div>
        </b-modal>
        <div v-show="employeesTypologies && employeesTypologies.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./employees-typology.component.ts">
</script>
