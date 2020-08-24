<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.techPractices.home.title')" id="tech-practices-heading">Tech Practices</span>
            <router-link :to="{name: 'TechPracticesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tech-practices">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.techPractices.home.createLabel')">
                    Create a new Tech Practices
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
        <div class="alert alert-warning" v-if="!isFetching && techPractices && techPractices.length === 0">
            <span v-text="$t('kompetitors2App.techPractices.home.notFound')">No techPractices found</span>
        </div>
        <div class="table-responsive" v-if="techPractices && techPractices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.techPractices.value')">Value</span></th>
                    <th><span v-text="$t('kompetitors2App.techPractices.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="techPractices in techPractices"
                    :key="techPractices.id">
                    <td>
                        <router-link :to="{name: 'TechPracticesView', params: {techPracticesId: techPractices.id}}">{{techPractices.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="techPractices.valueId">
                            <router-link :to="{name: 'ListPracticesView', params: {valueId: techPractices.valueId}}">{{techPractices.valueId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="techPractices.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techPractices.competitorId}}">{{techPractices.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TechPracticesView', params: {techPracticesId: techPractices.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TechPracticesEdit', params: {techPracticesId: techPractices.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(techPractices)"
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
            <span slot="modal-title"><span id="kompetitors2App.techPractices.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-techPractices-heading" v-bind:title="$t('kompetitors2App.techPractices.delete.question')">Are you sure you want to delete this Tech Practices?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-techPractices" v-text="$t('entity.action.delete')" v-on:click="removeTechPractices()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./tech-practices.component.ts">
</script>
