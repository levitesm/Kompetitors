<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.workforce.home.title')" id="workforce-heading">Workforces</span>
            <router-link :to="{name: 'WorkforceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-workforce">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.workforce.home.createLabel')">
                    Create a new Workforce
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
        <div class="alert alert-warning" v-if="!isFetching && workforces && workforces.length === 0">
            <span v-text="$t('kompetitors2App.workforce.home.notFound')">No workforces found</span>
        </div>
        <div class="table-responsive" v-if="workforces && workforces.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.workforce.employeeNumber')">Employee Number</span></th>
                    <th><span v-text="$t('kompetitors2App.workforce.year')">Year</span></th>
                    <th><span v-text="$t('kompetitors2App.workforce.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="workforce in workforces"
                    :key="workforce.id">
                    <td>
                        <router-link :to="{name: 'WorkforceView', params: {workforceId: workforce.id}}">{{workforce.id}}</router-link>
                    </td>
                    <td>{{workforce.employeeNumber}}</td>
                    <td>{{workforce.year}}</td>
                    <td>
                        <div v-if="workforce.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: workforce.competitor.id}}">{{workforce.competitor.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'WorkforceView', params: {workforceId: workforce.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'WorkforceEdit', params: {workforceId: workforce.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(workforce)"
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
            <span slot="modal-title"><span id="kompetitors2App.workforce.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-workforce-heading" v-bind:title="$t('kompetitors2App.workforce.delete.question')">Are you sure you want to delete this Workforce?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-workforce" v-text="$t('entity.action.delete')" v-on:click="removeWorkforce()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./workforce.component.ts">
</script>
