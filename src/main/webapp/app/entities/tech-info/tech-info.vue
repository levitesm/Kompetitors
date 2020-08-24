<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.techInfo.home.title')" id="tech-info-heading">Tech Infos</span>
            <router-link :to="{name: 'TechInfoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tech-info">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.techInfo.home.createLabel')">
                    Create a new Tech Info
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
        <div class="alert alert-warning" v-if="!isFetching && techInfos && techInfos.length === 0">
            <span v-text="$t('kompetitors2App.techInfo.home.notFound')">No techInfos found</span>
        </div>
        <div class="table-responsive" v-if="techInfos && techInfos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.techInfo.techSpecialistsNumber')">Tech Specialists Number</span></th>
                    <th><span v-text="$t('kompetitors2App.techInfo.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="techInfo in techInfos"
                    :key="techInfo.id">
                    <td>
                        <router-link :to="{name: 'TechInfoView', params: {techInfoId: techInfo.id}}">{{techInfo.id}}</router-link>
                    </td>
                    <td>{{techInfo.techSpecialistsNumber}}</td>
                    <td>
                        <div v-if="techInfo.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: techInfo.competitorId}}">{{techInfo.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TechInfoView', params: {techInfoId: techInfo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TechInfoEdit', params: {techInfoId: techInfo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(techInfo)"
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
            <span slot="modal-title"><span id="kompetitors2App.techInfo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-techInfo-heading" v-bind:title="$t('kompetitors2App.techInfo.delete.question')">Are you sure you want to delete this Tech Info?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-techInfo" v-text="$t('entity.action.delete')" v-on:click="removeTechInfo()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./tech-info.component.ts">
</script>
