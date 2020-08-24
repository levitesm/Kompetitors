<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.legal.home.title')" id="legal-heading">Legals</span>
            <router-link :to="{name: 'LegalCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-legal">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.legal.home.createLabel')">
                    Create a new Legal
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
        <div class="alert alert-warning" v-if="!isFetching && legals && legals.length === 0">
            <span v-text="$t('kompetitors2App.legal.home.notFound')">No legals found</span>
        </div>
        <div class="table-responsive" v-if="legals && legals.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.legalAddress')">Legal Address</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.siren')">Siren</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.greffe')">Greffe</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.founded')">Founded</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.updateDate')">Update Date</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.legalForm')">Legal Form</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.competitor')">Competitor</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.ownership')">Ownership</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.activity')">Activity</span></th>
                    <th><span v-text="$t('kompetitors2App.legal.pricing')">Pricing</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="legal in legals"
                    :key="legal.id">
                    <td>
                        <router-link :to="{name: 'LegalView', params: {legalId: legal.id}}">{{legal.id}}</router-link>
                    </td>
                    <td>{{legal.legalAddress}}</td>
                    <td>{{legal.siren}}</td>
                    <td>{{legal.greffe}}</td>
                    <td>{{legal.founded}}</td>
                    <td>{{legal.updateDate}}</td>
                    <td>{{legal.legalForm}}</td>
                    <td>
                        <div v-if="legal.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: legal.competitor.id}}">{{legal.competitor.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="legal.ownership">
                            <router-link :to="{name: 'ListOwnershipsView', params: {ownershipId: legal.ownership.id}}">{{legal.ownership.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="legal.activity">
                            <router-link :to="{name: 'ListActivitiesView', params: {activityId: legal.activity.id}}">{{legal.activity.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="legal.pricing">
                            <router-link :to="{name: 'ListPricingsView', params: {pricingId: legal.pricing.id}}">{{legal.pricing.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'LegalView', params: {legalId: legal.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'LegalEdit', params: {legalId: legal.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(legal)"
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
            <span slot="modal-title"><span id="kompetitors2App.legal.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-legal-heading" v-bind:title="$t('kompetitors2App.legal.delete.question')">Are you sure you want to delete this Legal?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-legal" v-text="$t('entity.action.delete')" v-on:click="removeLegal()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./legal.component.ts">
</script>
