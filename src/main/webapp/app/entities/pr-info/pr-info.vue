<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.prInfo.home.title')" id="pr-info-heading">Pr Infos</span>
            <router-link :to="{name: 'PrInfoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-pr-info">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.prInfo.home.createLabel')">
                    Create a new Pr Info
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
        <div class="alert alert-warning" v-if="!isFetching && prInfos && prInfos.length === 0">
            <span v-text="$t('kompetitors2App.prInfo.home.notFound')">No prInfos found</span>
        </div>
        <div class="table-responsive" v-if="prInfos && prInfos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.date')">Date</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.marketingWorkforce')">Marketing Workforce</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.marketingBudget')">Marketing Budget</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.experienceFeedback')">Experience Feedback</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.linkedInSubscribers')">Linked In Subscribers</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.linkedInEngageRate')">Linked In Engage Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.linkedInPostWeek')">Linked In Post Week</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.linkedInPostDay')">Linked In Post Day</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.twitterFollowers')">Twitter Followers</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.twitterPostWeek')">Twitter Post Week</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.twitterPostDay')">Twitter Post Day</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.instagramFollowers')">Instagram Followers</span></th>
                    <th><span v-text="$t('kompetitors2App.prInfo.competitors')">Competitors</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="prInfo in prInfos"
                    :key="prInfo.id">
                    <td>
                        <router-link :to="{name: 'PrInfoView', params: {prInfoId: prInfo.id}}">{{prInfo.id}}</router-link>
                    </td>
                    <td>{{prInfo.date}}</td>
                    <td>{{prInfo.marketingWorkforce}}</td>
                    <td>{{prInfo.marketingBudget}}</td>
                    <td>{{prInfo.experienceFeedback}}</td>
                    <td>{{prInfo.linkedInSubscribers}}</td>
                    <td>{{prInfo.linkedInEngageRate}}</td>
                    <td>{{prInfo.linkedInPostWeek}}</td>
                    <td>{{prInfo.linkedInPostDay}}</td>
                    <td>{{prInfo.twitterFollowers}}</td>
                    <td>{{prInfo.twitterPostWeek}}</td>
                    <td>{{prInfo.twitterPostDay}}</td>
                    <td>{{prInfo.instagramFollowers}}</td>
                    <td>
                        <div v-if="prInfo.competitorsId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorsId: prInfo.competitorsId}}">{{prInfo.competitorsId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PrInfoView', params: {prInfoId: prInfo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PrInfoEdit', params: {prInfoId: prInfo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(prInfo)"
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
            <span slot="modal-title"><span id="kompetitors2App.prInfo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-prInfo-heading" v-bind:title="$t('kompetitors2App.prInfo.delete.question')">Are you sure you want to delete this Pr Info?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-prInfo" v-text="$t('entity.action.delete')" v-on:click="removePrInfo()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./pr-info.component.ts">
</script>
