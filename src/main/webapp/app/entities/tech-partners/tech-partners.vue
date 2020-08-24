<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.techPartners.home.title')" id="tech-partners-heading">Tech Partners</span>
            <router-link :to="{name: 'TechPartnersCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tech-partners">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.techPartners.home.createLabel')">
                    Create a new Tech Partners
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
        <div class="alert alert-warning" v-if="!isFetching && techPartners && techPartners.length === 0">
            <span v-text="$t('kompetitors2App.techPartners.home.notFound')">No techPartners found</span>
        </div>
        <div class="table-responsive" v-if="techPartners && techPartners.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.techPartners.value')">Value</span></th>
                    <th><span v-text="$t('kompetitors2App.techPartners.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="techPartners in techPartners"
                    :key="techPartners.id">
                    <td>
                        <router-link :to="{name: 'TechPartnersView', params: {techPartnersId: techPartners.id}}">{{techPartners.id}}</router-link>
                    </td>
                    <td>
                        <div v-if="techPartners.valueId">
                            <router-link :to="{name: 'ListTechPartnersView', params: {valueId: techPartners.valueId}}">{{techPartners.valueId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="techPartners.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techPartners.competitorId}}">{{techPartners.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TechPartnersView', params: {techPartnersId: techPartners.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TechPartnersEdit', params: {techPartnersId: techPartners.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(techPartners)"
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
            <span slot="modal-title"><span id="kompetitors2App.techPartners.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-techPartners-heading" v-bind:title="$t('kompetitors2App.techPartners.delete.question')">Are you sure you want to delete this Tech Partners?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-techPartners" v-text="$t('entity.action.delete')" v-on:click="removeTechPartners()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./tech-partners.component.ts">
</script>
