<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.employeeSalaries.home.title')" id="employee-salaries-heading">Employee Salaries</span>
            <router-link :to="{name: 'EmployeeSalariesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-employee-salaries">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.employeeSalaries.home.createLabel')">
                    Create a new Employee Salaries
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
        <div class="alert alert-warning" v-if="!isFetching && employeeSalaries && employeeSalaries.length === 0">
            <span v-text="$t('kompetitors2App.employeeSalaries.home.notFound')">No employeeSalaries found</span>
        </div>
        <div class="table-responsive" v-if="employeeSalaries && employeeSalaries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.employeeSalaries.seniority')">Seniority</span></th>
                    <th><span v-text="$t('kompetitors2App.employeeSalaries.salary')">Salary</span></th>
                    <th><span v-text="$t('kompetitors2App.employeeSalaries.updateDate')">Update Date</span></th>
                    <th><span v-text="$t('kompetitors2App.employeeSalaries.updatedBy')">Updated By</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="employeeSalaries in employeeSalaries"
                    :key="employeeSalaries.id">
                    <td>
                        <router-link :to="{name: 'EmployeeSalariesView', params: {employeeSalariesId: employeeSalaries.id}}">{{employeeSalaries.id}}</router-link>
                    </td>
                    <td>{{employeeSalaries.seniority}}</td>
                    <td>{{employeeSalaries.salary}}</td>
                    <td>{{employeeSalaries.updateDate}}</td>
                    <td>{{employeeSalaries.updatedBy}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'EmployeeSalariesView', params: {employeeSalariesId: employeeSalaries.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'EmployeeSalariesEdit', params: {employeeSalariesId: employeeSalaries.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(employeeSalaries)"
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
            <span slot="modal-title"><span id="kompetitors2App.employeeSalaries.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-employeeSalaries-heading" v-bind:title="$t('kompetitors2App.employeeSalaries.delete.question')">Are you sure you want to delete this Employee Salaries?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-employeeSalaries" v-text="$t('entity.action.delete')" v-on:click="removeEmployeeSalaries()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./employee-salaries.component.ts">
</script>
