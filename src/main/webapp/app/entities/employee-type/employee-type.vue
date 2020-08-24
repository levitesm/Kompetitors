<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.employeeType.home.title')" id="employee-type-heading">Employee Types</span>
            <router-link :to="{name: 'EmployeeTypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-employee-type">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.employeeType.home.createLabel')">
                    Create a new Employee Type
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
        <div class="alert alert-warning" v-if="!isFetching && employeeTypes && employeeTypes.length === 0">
            <span v-text="$t('kompetitors2App.employeeType.home.notFound')">No employeeTypes found</span>
        </div>
        <div class="table-responsive" v-if="employeeTypes && employeeTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.employeeType.name')">Name</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="employeeType in employeeTypes"
                    :key="employeeType.id">
                    <td>
                        <router-link :to="{name: 'EmployeeTypeView', params: {employeeTypeId: employeeType.id}}">{{employeeType.id}}</router-link>
                    </td>
                    <td>{{employeeType.name}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'EmployeeTypeView', params: {employeeTypeId: employeeType.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'EmployeeTypeEdit', params: {employeeTypeId: employeeType.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(employeeType)"
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
            <span slot="modal-title"><span id="kompetitors2App.employeeType.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-employeeType-heading" v-bind:title="$t('kompetitors2App.employeeType.delete.question')">Are you sure you want to delete this Employee Type?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-employeeType" v-text="$t('entity.action.delete')" v-on:click="removeEmployeeType()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./employee-type.component.ts">
</script>
